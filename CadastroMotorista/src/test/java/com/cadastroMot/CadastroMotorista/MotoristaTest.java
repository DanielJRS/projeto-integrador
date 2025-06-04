package com.cadastroMot.CadastroMotorista;


//package com.cadastroMot.CadastroMotorista.domain;

import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MotoristaTest {

    @Test
    void testGettersAndSetters() {
        Usuario usuario = new Usuario(2L, "motorista@email.com", "senha", TipoUsuario.MOTORISTA, null, null, null);
        Transportadora transportadora = new Transportadora();
        Motorista motorista = new Motorista(
                1L,
                "João Motorista",
                "12345678901",
                "Rua Y",
                "51999999999",
                "Porto Alegre",
                "RS",
                "Brasil",
                "CNH123456",
                "ANTT123",
                new byte[]{1,2,3},
                "image/png",
                Collections.emptyList(),
                usuario,
                transportadora
        );

        assertEquals(1L, motorista.getId());
        assertEquals("João Motorista", motorista.getNome());
        assertEquals("12345678901", motorista.getCpf());
        assertEquals("Rua Y", motorista.getEndereco());
        assertEquals("51999999999", motorista.getCelular());
        assertEquals("Porto Alegre", motorista.getCidade());
        assertEquals("RS", motorista.getEstado());
        assertEquals("Brasil", motorista.getPais());
        assertEquals("CNH123456", motorista.getCnh());
        assertEquals("ANTT123", motorista.getAntt());
        assertArrayEquals(new byte[]{1,2,3}, motorista.getFoto());
        assertEquals("image/png", motorista.getTipoFoto());
        assertEquals(usuario, motorista.getUsuario());
        assertEquals(transportadora, motorista.getTransportadora());
    }
}