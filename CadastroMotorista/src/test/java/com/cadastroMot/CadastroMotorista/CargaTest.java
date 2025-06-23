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

    @Test
    void testFalhaCarga() {
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

        // Assertivas incorretas, vão falhar
        assertEquals(2L, carga.getId()); // falha aqui
        assertEquals("Porto Alegreeee", carga.getOrigemCidade());
        assertEquals("SP", carga.getOrigemEstado());
        assertEquals(LocalDate.of(2025, 7, 1), carga.getDataColeta());
        assertEquals("Rio de Janeiro", carga.getDestinoCidade());
        assertEquals("RJ", carga.getDestinoEstado());
        assertEquals(LocalDate.of(2025, 7, 5), carga.getDataEntrega());
        assertEquals("Café", carga.getProduto());
        assertEquals("B", carga.getEspecie());
        assertEquals("Caminhão", carga.getVeiculo());
        assertEquals(6000.0, carga.getPreco());
        assertEquals(TipoCarga.COMPLETA, carga.getTipoCarga());
        assertEquals(TipoEstadoCarga.ANDAMENTO, carga.getTipoEstadoCarga());
        assertFalse(carga.getPossuiLona());
        assertEquals(15.0, carga.getPesoTotal());
        assertEquals(3.0, carga.getLimiteAltura());
        assertEquals(35.0, carga.getVolume());

        assertEquals(Arrays.asList("Caminhão"), carga.getVeiculosLeves());
        assertEquals(Arrays.asList("Bitrem"), carga.getVeiculosMedios());
        assertEquals(Arrays.asList("Toco"), carga.getVeiculosPesados());
        assertEquals(Arrays.asList("FA1"), carga.getFretesFechados());
        assertEquals(Arrays.asList("FF1"), carga.getFretesAbertos());
        assertEquals(Arrays.asList("FE2"), carga.getFretesEspeciais());
    }

}