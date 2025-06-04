package com.cadastroMot.CadastroMotorista;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.TipoCarga;
import com.cadastroMot.CadastroMotorista.domain.TipoEstadoCarga;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CargaTest {

    @Test
    void testGettersAndSetters() {
        Carga carga = new Carga();
        carga.setId(1L);
        carga.setOrigemCidade("Porto Alegre");
        carga.setOrigemEstado("RS");
        carga.setDataColeta(LocalDate.of(2025, 6, 1));
        carga.setDestinoCidade("São Paulo");
        carga.setDestinoEstado("SP");
        carga.setDataEntrega(LocalDate.of(2025, 6, 5));
        carga.setProduto("Grãos");
        carga.setEspecie("A");
        carga.setVeiculo("Truck");
        carga.setPreco(5000.0);
        carga.setTipoCarga(TipoCarga.COMPLETA);
        carga.setTipoEstadoCarga(TipoEstadoCarga.DISPONIVEL);
        carga.setPossuiLona(true);
        carga.setPesoTotal(12.5);
        carga.setLimiteAltura(2.8);
        carga.setVolume(30.0);

        carga.setVeiculosLeves(Arrays.asList("VAN", "3/4"));
        carga.setVeiculosMedios(Arrays.asList("Toco"));
        carga.setVeiculosPesados(Arrays.asList("Bitrem"));
        carga.setFretesFechados(Arrays.asList("FF1"));
        carga.setFretesAbertos(Arrays.asList("FA1"));
        carga.setFretesEspeciais(Arrays.asList("FE1"));

        assertEquals(1L, carga.getId());
        assertEquals("Porto Alegre", carga.getOrigemCidade());
        assertEquals("RS", carga.getOrigemEstado());
        assertEquals(LocalDate.of(2025, 6, 1), carga.getDataColeta());
        assertEquals("São Paulo", carga.getDestinoCidade());
        assertEquals("SP", carga.getDestinoEstado());
        assertEquals(LocalDate.of(2025, 6, 5), carga.getDataEntrega());
        assertEquals("Grãos", carga.getProduto());
        assertEquals("A", carga.getEspecie());
        assertEquals("Truck", carga.getVeiculo());
        assertEquals(5000.0, carga.getPreco());
        assertEquals(TipoCarga.COMPLETA, carga.getTipoCarga());
        assertEquals(TipoEstadoCarga.DISPONIVEL, carga.getTipoEstadoCarga());
        assertTrue(carga.getPossuiLona());
        assertEquals(12.5, carga.getPesoTotal());
        assertEquals(2.8, carga.getLimiteAltura());
        assertEquals(30.0, carga.getVolume());

        assertEquals(Arrays.asList("VAN", "3/4"), carga.getVeiculosLeves());
        assertEquals(Arrays.asList("Toco"), carga.getVeiculosMedios());
        assertEquals(Arrays.asList("Bitrem"), carga.getVeiculosPesados());
        assertEquals(Arrays.asList("FF1"), carga.getFretesFechados());
        assertEquals(Arrays.asList("FA1"), carga.getFretesAbertos());
        assertEquals(Arrays.asList("FE1"), carga.getFretesEspeciais());
    }
}