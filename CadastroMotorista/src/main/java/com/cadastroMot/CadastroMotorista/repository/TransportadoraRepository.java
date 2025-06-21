package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TransportadoraRepository extends JpaRepository <Transportadora, Long> {
    List<Transportadora> findByRazaoSocialContainingIgnoreCaseAndNomeFantasiaContainingIgnoreCaseAndCnpjContainingIgnoreCase(
        String razaoSocial,
        String nomeFantasia,
        String cnpj
    );

}
