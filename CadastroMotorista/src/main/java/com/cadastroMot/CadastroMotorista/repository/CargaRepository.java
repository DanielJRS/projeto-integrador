package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CargaRepository extends JpaRepository<Carga, Long> {

    List<Carga> findByOrigemCidadeAndDestinoCidadeAndProduto(String origemCidade, String destinoCidade, String produto);

    List<Carga> findByOrigemCidade(String origemCidade);

    List<Carga> findByDestinoCidade(String destinoCidade);

    List<Carga> findByProduto(String produto);

    List<Carga> findByProdutoLike(String produto);

    List<Carga> findByOrigemCidadeAndDestinoCidadeAndProdutoAndEspecie(String origemCidade, String destinoCidade, String produto, String especie);

    List<Carga> findByOrigemCidadeAndDestinoCidade(String origemCidade, String destinoCidade);

    // Consulta limpa, sem join fetch, para evitar erro com @ElementCollection
    @Query("SELECT c FROM Carga c WHERE c.id = :id")
    Optional<Carga> findByIdWithCollections(@Param("id") Long id);

    // Também é possível usar apenas o método padrão
    // Optional<Carga> findById(Long id);

    List<Carga> findByMotoristaId(Long motoristaId);
}
