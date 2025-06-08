package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface    VeiculoRepository extends JpaRepository<Veiculo, Long> {
    // Add inside VeiculoRepository interface
    List<Veiculo> findByMotoristaId(Long motoristaId);
    List<Veiculo> findByTransportadoraId(Long transportadoraId);
}

