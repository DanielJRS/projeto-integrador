package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.Empresa;
import com.cadastroMot.CadastroMotorista.domain.TipoEstadoCarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CargaRepository extends JpaRepository<Carga, Long>, JpaSpecificationExecutor<Carga> {

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

    List<Carga> findByOrigemCidadeAndOrigemEstado(String cidade, String estado);

    List<Carga> findByTipoEstadoCarga(TipoEstadoCarga tipoEstadoCarga);

    List<Carga> findByEmpresaCargaId(Long empresaId);

    List<Carga> findByOrigemCidadeAndOrigemEstadoAndDestinoCidadeAndDestinoEstado(String origemCidade, String origemEstado, String destinoCidade, String destinoEstado);

    List<Carga> findByProdutoContainingIgnoreCase(String produto);

    long countByEmpresaCargaId(Long empresaId);

    long countByTipoEstadoCarga(TipoEstadoCarga tipoEstadoCarga);

    List<Carga> findByDataEntregaBetween(LocalDate dataInicio, LocalDate dataFim);

    List<Carga> findByDataColetaBetween(LocalDate dataInicio, LocalDate dataFim);

    List<Carga> findByPrecoBetween(Double precoMin, Double precoMax);

    List<Carga> findByDestinoCidadeAndDestinoEstado(String cidade, String estado);

    List<Carga> findByEmpresaCarga(Empresa empresa);

    @Query("SELECT c FROM Carga c JOIN FETCH c.empresaCarga WHERE c.empresaCarga = :empresa")
    List<Carga> findByEmpresa(@Param("empresa") Empresa empresa);

    List<String> findDistinctOrigemCidade();

    List<String> findDistinctOrigemEstado();
}
