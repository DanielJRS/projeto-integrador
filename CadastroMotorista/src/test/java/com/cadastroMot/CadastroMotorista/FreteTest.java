package com.cadastroMot.CadastroMotorista;

import com.cadastroMot.CadastroMotorista.controller.FreteController;
import com.cadastroMot.CadastroMotorista.domain.*;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import com.cadastroMot.CadastroMotorista.service.FreteService;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FreteTest {

    private MockMvc mockMvc;

    @Mock
    private CargaService cargaService;

    @Mock
    private MotoristaService motoristaService;

    @Mock
    private FreteService freteService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private FreteController freteController;

    private Usuario usuarioLogado;
    private Carga carga;
    private Motorista motorista;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(freteController).build();

        usuarioLogado = new Usuario();
        usuarioLogado.setId(10L);
        usuarioLogado.setTipo(TipoUsuario.MOTORISTA);

        carga = new Carga();
        carga.setId(100L);
        carga.setOrigemCidade("Porto Alegre");
        carga.setOrigemEstado("RS");
        carga.setDestinoCidade("São Paulo");
        carga.setDestinoEstado("SP");

        motorista = new Motorista();
        motorista.setId(10L);
    }

    @Test
    void deveAceitarFreteERedirecionar() throws Exception {

        when(session.getAttribute("usuarioLogado")).thenReturn(usuarioLogado);


        when(cargaService.buscarPorId(100L)).thenReturn(carga);


        when(motoristaService.buscarPorId(usuarioLogado.getId())).thenReturn(motorista);


        doNothing().when(freteService).salvar(any(Frete.class));
        doNothing().when(cargaService).salvar(any(Carga.class));

        mockMvc.perform(get("/aceitar-frete")
                        .param("cargaId", "100")
                        .sessionAttr("usuarioLogado", usuarioLogado))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cargas/listar"));


        verify(cargaService).buscarPorId(100L);
        verify(motoristaService).buscarPorId(usuarioLogado.getId());
        verify(freteService).salvar(any(Frete.class));
        verify(cargaService).salvar(any(Carga.class));


        assertEquals(TipoEstadoCarga.ANDAMENTO, carga.getTipoEstadoCarga());
    }

    @Test
    void deveFalharAceitarFreteERedirecionar() throws Exception {

        when(session.getAttribute("usuarioLogado")).thenReturn(usuarioLogado);

        when(cargaService.buscarPorId(100L)).thenReturn(carga);

        when(motoristaService.buscarPorId(usuarioLogado.getId())).thenReturn(motorista);

        doNothing().when(freteService).salvar(any(Frete.class));
        doNothing().when(cargaService).salvar(any(Carga.class));

        mockMvc.perform(get("/aceitar-frete")
                        .param("cargaId", "100")
                        .sessionAttr("usuarioLogado", usuarioLogado))
                .andExpect(status().is4xxClientError()); // espera erro mas será redirecionamento

        verify(cargaService).buscarPorId(100L);
        verify(motoristaService).buscarPorId(usuarioLogado.getId());
        verify(freteService).salvar(any(Frete.class));
        verify(cargaService).salvar(any(Carga.class));


        assertEquals(TipoEstadoCarga.INDISPONIVEL, carga.getTipoEstadoCarga());
    }

}

