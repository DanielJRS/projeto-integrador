package com.cadastroMot.CadastroMotorista;

import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.repository.MotoristaRepository;
import com.cadastroMot.CadastroMotorista.repository.UsuarioRepository;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
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

class MotoristaTest {

    @Mock
    private MotoristaRepository motoristaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MotoristaService motoristaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarTodosOsMotoristas() {
        Motorista motorista1 = new Motorista();
        motorista1.setId(1L);
        Motorista motorista2 = new Motorista();
        motorista2.setId(2L);

        when(motoristaRepository.findAll()).thenReturn(List.of(motorista1, motorista2));

        var resultado = motoristaService.listarTodos();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(motorista1));
        assertTrue(resultado.contains(motorista2));

        verify(motoristaRepository).findAll();
    }

    @Test
    void deveBuscarMotoristaPorId_quandoExistir() {
        Long id = 1L;
        Motorista motorista = new Motorista();
        motorista.setId(id);

        when(motoristaRepository.findById(id)).thenReturn(Optional.of(motorista));

        Motorista resultado = motoristaService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(motoristaRepository).findById(id);
    }

    @Test
    void deveRetornarNull_quandoMotoristaNaoExistir() {
        Long id = 999L;

        when(motoristaRepository.findById(id)).thenReturn(Optional.empty());

        Motorista resultado = motoristaService.buscarPorId(id);

        assertNull(resultado);
        verify(motoristaRepository).findById(id);
    }
}
