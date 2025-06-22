package com.cadastroMot.CadastroMotorista;

import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.repository.TransportadoraRepository;
import com.cadastroMot.CadastroMotorista.repository.UsuarioRepository;
import com.cadastroMot.CadastroMotorista.service.TransportadoraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransportadoraTest {

    @Mock
    private TransportadoraRepository transportadoraRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TransportadoraService transportadoraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarTransportadoraComUsuario_quandoEmailNaoExiste() {
        Transportadora transportadora = new Transportadora();
        String email = "transp@empresa.com";
        String senha = "senha123";

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(senha)).thenReturn("senha123_criptografada");
        when(transportadoraRepository.save(any(Transportadora.class))).thenAnswer(i -> i.getArgument(0));

        Transportadora resultado = transportadoraService.salvarComUsuario(transportadora, email, senha);

        assertNotNull(resultado.getUsuario());
        assertEquals(email, resultado.getUsuario().getEmail());
        assertEquals("senha123_criptografada", resultado.getUsuario().getSenha());
        assertEquals(TipoUsuario.TRANSPORTADORA, resultado.getUsuario().getTipo());

        verify(usuarioRepository).save(any(Usuario.class));
        verify(transportadoraRepository).save(any(Transportadora.class));
    }

    @Test
    void deveLancarExcecao_quandoEmailJaExiste() {
        Transportadora transportadora = new Transportadora();
        String email = "existe@empresa.com";
        String senha = "senha123";

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(new Usuario()));

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            transportadoraService.salvarComUsuario(transportadora, email, senha);
        });

        assertEquals("Já existe uma transportadora cadastrada com este e-mail", excecao.getMessage());
        verify(usuarioRepository, never()).save(any());
        verify(transportadoraRepository, never()).save(any());
    }

}
