package Dto;
import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.CargaFiltro;
import com.cadastroMot.CadastroMotorista.domain.TipoCarga;
import com.cadastroMot.CadastroMotorista.domain.TipoEstadoCarga;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CargaSpecification {

    public static Specification<Carga> filtrar(CargaFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // FILTRO POR EMPRESA - MAIS IMPORTANTE PARA SUA NECESSIDADE
            if (filtro.getEmpresa() != null) {
                predicates.add(criteriaBuilder.equal(root.get("empresaCarga"), filtro.getEmpresa()));
            }

            // FILTRO POR ORIGEM - CIDADE
            if (filtro.getOrigemCidade() != null && !filtro.getOrigemCidade().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("origemCidade")),
                        "%" + filtro.getOrigemCidade().toLowerCase().trim() + "%"
                ));
            }

            // FILTRO POR ORIGEM - ESTADO
            if (filtro.getOrigemEstado() != null && !filtro.getOrigemEstado().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("origemEstado")),
                        "%" + filtro.getOrigemEstado().toLowerCase().trim() + "%"
                ));
            }

            // FILTRO POR DESTINO - CIDADE
            if (filtro.getDestinoCidade() != null && !filtro.getDestinoCidade().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("destinoCidade")),
                        "%" + filtro.getDestinoCidade().toLowerCase().trim() + "%"
                ));
            }

            // FILTRO POR DESTINO - ESTADO
            if (filtro.getDestinoEstado() != null && !filtro.getDestinoEstado().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("destinoEstado")),
                        "%" + filtro.getDestinoEstado().toLowerCase().trim() + "%"
                ));
            }

            // FILTRO POR PRODUTO
            if (filtro.getProduto() != null && !filtro.getProduto().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("produto")),
                        "%" + filtro.getProduto().toLowerCase().trim() + "%"
                ));
            }

            // FILTRO POR ESPÉCIE
            if (filtro.getEspecie() != null && !filtro.getEspecie().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("especie")),
                        "%" + filtro.getEspecie().toLowerCase().trim() + "%"
                ));
            }

            // FILTRO POR TIPO DE CARGA
            if (filtro.getTipoCarga() != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoCarga"), filtro.getTipoCarga()));
            }

            // FILTRO POR ESTADO DA CARGA
            if (filtro.getTipoEstadoCarga() != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoEstadoCarga"), filtro.getTipoEstadoCarga()));
            }

            // FILTRO POR DATA DE COLETA - DE
            if (filtro.getDataColetaDe() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataColeta"), filtro.getDataColetaDe()));
            }

            // FILTRO POR DATA DE COLETA - ATÉ
            if (filtro.getDataColetaAte() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataColeta"), filtro.getDataColetaAte()));
            }

            // FILTRO POR DATA DE ENTREGA - DE
            if (filtro.getDataEntregaDe() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataEntrega"), filtro.getDataEntregaDe()));
            }

            // FILTRO POR DATA DE ENTREGA - ATÉ
            if (filtro.getDataEntregaAte() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataEntrega"), filtro.getDataEntregaAte()));
            }

            // FILTRO POR PREÇO MÍNIMO
            if (filtro.getPrecoMinimo() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), filtro.getPrecoMinimo()));
            }

            // FILTRO POR PREÇO MÁXIMO
            if (filtro.getPrecoMaximo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("preco"), filtro.getPrecoMaximo()));
            }

            // FILTRO POR PESO MÍNIMO
            if (filtro.getPesoMinimo() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pesoTotal"), filtro.getPesoMinimo()));
            }

            // FILTRO POR PESO MÁXIMO
            if (filtro.getPesoMaximo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pesoTotal"), filtro.getPesoMaximo()));
            }

            // FILTRO POR POSSUI LONA
            if (filtro.getPossuiLona() != null) {
                predicates.add(criteriaBuilder.equal(root.get("possuiLona"), filtro.getPossuiLona()));
            }

            // Ordenação padrão por data de criação (mais recentes primeiro)
            if (query != null) {
                query.orderBy(criteriaBuilder.desc(root.get("id")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}