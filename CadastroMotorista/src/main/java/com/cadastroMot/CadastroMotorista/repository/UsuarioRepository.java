package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    // Consulta para buscar o usuário com todos os relacionamentos carregados
    @Query("SELECT u FROM Usuario u " +
            "LEFT JOIN FETCH u.motorista " +
            "LEFT JOIN FETCH u.empresa " +
            "LEFT JOIN FETCH u.transportadora " +
            "WHERE u.email = :email")
    Optional<Usuario> findByEmailWithRelationships(@Param("email") String email);
}
