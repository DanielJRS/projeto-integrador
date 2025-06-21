package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findByRazaoSocialContainingIgnoreCaseAndNomeFantasiaContainingIgnoreCaseAndCnpjContainingIgnoreCase(
        String razaoSocial,
        String nomeFantasia,
        String cnpj
    );

}
