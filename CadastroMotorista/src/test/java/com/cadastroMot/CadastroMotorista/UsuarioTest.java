package com.cadastroMot.CadastroMotorista;

//package com.cadastroMot.CadastroMotorista.domain;

import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testGettersAndSetters() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("user@email.com");
        usuario.setSenha("senha123");
        usuario.setTipo(TipoUsuario.MOTORISTA);

        assertEquals(1L, usuario.getId());
        assertEquals("user@email.com", usuario.getEmail());
        assertEquals("senha123", usuario.getSenha());
        assertEquals(TipoUsuario.MOTORISTA, usuario.getTipo());
    }
}
