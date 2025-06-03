package com.cadastroMot.CadastroMotorista;

//package com.cadastroMot.CadastroMotorista.domain;

import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import com.cadastroMot.CadastroMotorista.domain.Veiculo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class VeiculoTest {

    @Test
    void testGettersAndSetters() {
        Motorista motorista = new Motorista();
        Transportadora transportadora = new Transportadora();

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("ABC1D23");
        veiculo.setModelo("FH");
        veiculo.setMarca("Volvo");
        veiculo.setCapacidadeCarga(25.0);
        veiculo.setRenavam("12345678901");
        veiculo.setChassi("9BWZZZ377VT004251");
        veiculo.setMotorista(motorista);
        veiculo.setTransportadora(transportadora);
        veiculo.setTipos(Arrays.asList("Truck", "Bitrem"));
        veiculo.setFretesFechados(Arrays.asList("FF1", "FF2"));
        veiculo.setFretesAbertos(Arrays.asList("FA1"));
        veiculo.setFretesEspeciais(Arrays.asList("FE1", "FE2"));

        assertEquals(1L, veiculo.getId());
        assertEquals("ABC1D23", veiculo.getPlaca());
        assertEquals("FH", veiculo.getModelo());
        assertEquals("Volvo", veiculo.getMarca());
        assertEquals(25.0, veiculo.getCapacidadeCarga());
        assertEquals("12345678901", veiculo.getRenavam());
        assertEquals("9BWZZZ377VT004251", veiculo.getChassi());
        assertEquals(motorista, veiculo.getMotorista());
        assertEquals(transportadora, veiculo.getTransportadora());
        assertEquals(Arrays.asList("Truck", "Bitrem"), veiculo.getTipos());
        assertEquals(Arrays.asList("FF1", "FF2"), veiculo.getFretesFechados());
        assertEquals(Arrays.asList("FA1"), veiculo.getFretesAbertos());
        assertEquals(Arrays.asList("FE1", "FE2"), veiculo.getFretesEspeciais());
    }
}
