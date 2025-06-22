package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
    Motorista findByUsuario(Usuario usuario);
    List<Motorista> findByTransportadoraMotorista(Transportadora transportadora);
    List<Motorista> findByNomeContainingIgnoreCase(String nome);
    List<Motorista> findByTransportadora_NomeFantasiaContainingIgnoreCase(String nomeFantasia);
    List<Motorista> findByNomeContainingIgnoreCaseAndTransportadora_NomeFantasiaContainingIgnoreCase(String nome, String nomeFantasia);

}
