package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Carga;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CargaRepository extends JpaRepository<Carga, Long> {

    List<Carga> findByOrigemAndDestinoAndProduto(String origem, String destino, String produto);
    List<Carga> findByOrigem(String origem);
    List<Carga> findByDestino(String destino);
    List<Carga> findByProduto(String produto);
    List<Carga> findByProdutoLike(String produto);

    List<Carga> findByOrigemAndDestinoAndProdutoAndEspecie(String origem, String destino, String produto, String especie);

    List<Carga> findByOrigemAndDestino(String origem, String destino);
}
