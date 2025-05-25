package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CargaRepository extends JpaRepository<Carga, Long> {

    List<Carga> findByOrigemCidadeAndDestinoCidadeAndProduto(String origemCidade, String destinoCidade, String produto);
    List<Carga> findByOrigemCidade(String origemCidade);
    List<Carga> findByDestinoCidade(String destinoCidade);
    List<Carga> findByProduto(String produto);
    List<Carga> findByProdutoLike(String produto);
    List<Carga> findByOrigemCidadeAndDestinoCidadeAndProdutoAndEspecie(String origemCidade, String destinoCidade, String produto, String especie);
    List<Carga> findByOrigemCidadeAndDestinoCidade(String origemCidade, String destinoCidade);
}
