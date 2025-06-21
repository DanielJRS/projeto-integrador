package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {

        public List<Frete> findByMotoristaFrete(Motorista motorista);

        public Long countByMotoristaFreteAndStatus(Motorista motorista, TipoEstadoFrete status);

    Long countByMotoristaFrete(Motorista motorista);

    Long countByEmpresaFreteAndStatus(Empresa empresa, TipoEstadoFrete status);

    public List<Frete> findByEmpresaFrete (Empresa empresa);
}
