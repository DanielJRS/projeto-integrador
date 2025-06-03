package com.cadastroMot.CadastroMotorista;

//package com.cadastroMot.CadastroMotorista.domain;

import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransportadoraTest {

    @Test
    void testGettersAndSetters() {
        Usuario usuario = new Usuario(3L, "transp@email.com", "senha", TipoUsuario.TRANSPORTADORA, null, null, null);
        Transportadora transp = new Transportadora(
                1L,
                "RazaoTransp",
                "FantasiaTransp",
                "12345678000155",
                "ISENTO",
                "Rua Q",
                "Cidade Q",
                "SC",
                "88300-100",
                "4733333333",
                "transp@email.com",
                "senha",
                LocalDate.of(2019,5,15),
                true,
                true,
                "ANTT0001",
                "Pesada",
                12,
                true,
                "Perecíveis",
                "30t",
                true,
                7,
                LocalDate.of(2029, 2, 2),
                "E",
                usuario
        );

        assertEquals(1L, transp.getId());
        assertEquals("RazaoTransp", transp.getRazaoSocial());
        assertEquals("FantasiaTransp", transp.getNomeFantasia());
        assertEquals("12345678000155", transp.getCnpj());
        assertEquals("ISENTO", transp.getInscricaoEstadual());
        assertEquals("Rua Q", transp.getEndereco());
        assertEquals("Cidade Q", transp.getCidade());
        assertEquals("SC", transp.getEstado());
        assertEquals("88300-100", transp.getCep());
        assertEquals("4733333333", transp.getTelefone());
        assertEquals("transp@email.com", transp.getEmail());
        assertEquals("senha", transp.getSenha());
        assertEquals(LocalDate.of(2019,5,15), transp.getDataFundacao());
        assertTrue(transp.isAtivo());
        assertTrue(transp.isSouTransportadora());
        assertEquals("ANTT0001", transp.getNumeroRegistroANTT());
        assertEquals("Pesada", transp.getTipoFrota());
        assertEquals(12, transp.getQuantidadeVeiculos());
        assertTrue(transp.getPossuiSeguroCarga());
        assertEquals("Perecíveis", transp.getTiposMercadorias());
        assertEquals("30t", transp.getCapacidadeCarga());
        assertTrue(transp.getRastreamentoVeiculos());
        assertEquals(7, transp.getPrazoPadraoEntrega());
        assertEquals(LocalDate.of(2029, 2, 2), transp.getDataVencimentoLicenca());
        assertEquals("E", transp.getCategoriasLicenca());
        assertEquals(usuario, transp.getUsuario());
    }
}
