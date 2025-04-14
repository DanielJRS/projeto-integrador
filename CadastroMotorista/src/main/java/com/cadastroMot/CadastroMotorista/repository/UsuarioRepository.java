package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}