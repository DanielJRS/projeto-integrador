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

public interface CargaRepository extends JpaRepository<Carga, Long>, JpaSpecificationExecutor<Carga>
       {  // <- Adicionada interface customizada

    // Todos os seus métodos existentes permanecem os mesmos
    List<Carga> findByOrigemCidadeAndDestinoCidadeAndProduto(String origemCidade, String destinoCidade, String produto);

    List<Carga> findByOrigemCidade(String origemCidade);

    List<Carga> findByDestinoCidade(String destinoCidade);

    List<Carga> findByProduto(String produto);

    List<Carga> findByProdutoLike(String produto);

    List<Carga> findByOrigemCidadeAndDestinoCidadeAndProdutoAndEspecie(String origemCidade, String destinoCidade, String produto, String especie);

    List<Carga> findByOrigemCidadeAndDestinoCidade(String origemCidade, String destinoCidade);

    @Query("SELECT c FROM Carga c WHERE c.id = :id")
    Optional<Carga> findByIdWithCollections(@Param("id") Long id);

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

    // Métodos para popular selects dos filtros - melhorados
    @Query("SELECT DISTINCT c.origemCidade FROM Carga c WHERE c.origemCidade IS NOT NULL ORDER BY c.origemCidade")
    List<String> findDistinctOrigemCidade();

    @Query("SELECT DISTINCT c.origemEstado FROM Carga c WHERE c.origemEstado IS NOT NULL ORDER BY c.origemEstado")
    List<String> findDistinctOrigemEstado();

    @Query("SELECT DISTINCT c.destinoCidade FROM Carga c")
    List<String> findDistinctDestinoCidade();;

    @Query("SELECT DISTINCT c.destinoEstado FROM Carga c WHERE c.destinoEstado IS NOT NULL ORDER BY c.destinoEstado")
    List<String> findDistinctDestinoEstado();

    @Query("SELECT DISTINCT c.especie FROM Carga c WHERE c.especie IS NOT NULL ORDER BY c.especie")
    List<String> findDistinctEspecies();

    @Query("SELECT DISTINCT c.produto FROM Carga c WHERE c.produto IS NOT NULL ORDER BY c.produto")
    List<String> findDistinctProdutos();

    // Método para buscar todas as cidades (origem e destino) - útil para autocomplete
    @Query("SELECT DISTINCT cidade FROM " +
            "(SELECT c.origemCidade as cidade FROM Carga c WHERE c.origemCidade IS NOT NULL " +
            "UNION " +
            "SELECT c.destinoCidade as cidade FROM Carga c WHERE c.destinoCidade IS NOT NULL) " +
            "ORDER BY cidade")
    List<String> findAllDistinctCidades();

    // Método para buscar todos os estados (origem e destino)
    @Query("SELECT DISTINCT estado FROM " +
            "(SELECT c.origemEstado as estado FROM Carga c WHERE c.origemEstado IS NOT NULL " +
            "UNION " +
            "SELECT c.destinoEstado as estado FROM Carga c WHERE c.destinoEstado IS NOT NULL) " +
            "ORDER BY estado")
    List<String> findAllDistinctEstados();

       }