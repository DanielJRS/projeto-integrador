package com.cadastroMot.CadastroMotorista;

import com.cadastroMot.CadastroMotorista.domain.Empresa;
import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.repository.EmpresaRepository;
import com.cadastroMot.CadastroMotorista.repository.UsuarioRepository;
import com.cadastroMot.CadastroMotorista.service.EmpresaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpresaTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmpresaService empresaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarEmpresaComUsuario_quandoEmailNaoExiste() {
        Empresa empresa = new Empresa();
        String email = "teste@empresa.com";
        String senha = "senha123";

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(senha)).thenReturn("senha123_criptografada");
        when(empresaRepository.save(any(Empresa.class))).thenAnswer(i -> i.getArgument(0));

        Empresa resultado = empresaService.salvarComUsuario(empresa, email, senha);

        assertNotNull(resultado.getUsuario());
        assertEquals(email, resultado.getUsuario().getEmail());
        assertEquals("senha123_criptografada", resultado.getUsuario().getSenha());
        assertEquals(TipoUsuario.EMPRESA, resultado.getUsuario().getTipo());

        verify(usuarioRepository).save(any(Usuario.class));
        verify(empresaRepository).save(any(Empresa.class));
    }

    @Test
    void deveLancarExcecao_quandoEmailJaExiste() {
        Empresa empresa = new Empresa();
        String email = "jaexiste@empresa.com";
        String senha = "senha123";

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(new Usuario()));

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            empresaService.salvarComUsuario(empresa, email, senha);
        });

        assertEquals("Já existe uma empresa cadastrada com este e-mail", excecao.getMessage());
        verify(usuarioRepository, never()).save(any());
        verify(empresaRepository, never()).save(any());
    }

    @Test
    void deveFalharSalvarEmpresaComUsuario_quandoEmailNaoExiste() {
        Empresa empresa = new Empresa();
        String email = "teste@empresa.com";
        String senha = "senha123";

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(senha)).thenReturn("senha123_criptografada");
        when(empresaRepository.save(any(Empresa.class))).thenAnswer(i -> i.getArgument(0));

        Empresa resultado = empresaService.salvarComUsuario(empresa, email, senha);

        assertNull(resultado.getUsuario()); // falha: usuário não pode ser null
        assertEquals("outroemail@empresa.com", resultado.getUsuario().getEmail()); // falha
        assertEquals("senhaErrada", resultado.getUsuario().getSenha()); // falha
        assertEquals(TipoUsuario.MOTORISTA, resultado.getUsuario().getTipo()); // falha

        verify(usuarioRepository).save(any(Usuario.class));
        verify(empresaRepository).save(any(Empresa.class));
    }

}
