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


            if (filtro.getEmpresa() != null) {
                predicates.add(criteriaBuilder.equal(root.get("empresaCarga"), filtro.getEmpresa()));
            }


            if (filtro.getNomeEmpresa() != null && !filtro.getNomeEmpresa().trim().isEmpty()) {
                String nomeEmpresaLower = filtro.getNomeEmpresa().toLowerCase().trim();

                Predicate razaoSocialPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("empresaCarga").get("razaoSocial")),
                        "%" + nomeEmpresaLower + "%"
                );

                Predicate nomeFantasiaPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("empresaCarga").get("nomeFantasia")),
                        "%" + nomeEmpresaLower + "%"
                );

                // Busca em qualquer um dos dois campos (OR)
                predicates.add(criteriaBuilder.or(razaoSocialPredicate, nomeFantasiaPredicate));
            }

            if (filtro.getOrigemCidade() != null && !filtro.getOrigemCidade().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("origemCidade")),
                        "%" + filtro.getOrigemCidade().toLowerCase().trim() + "%"
                ));
            }

            if (filtro.getOrigemEstado() != null && !filtro.getOrigemEstado().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("origemEstado")),
                        "%" + filtro.getOrigemEstado().toLowerCase().trim() + "%"
                ));
            }

            if (filtro.getDestinoCidade() != null && !filtro.getDestinoCidade().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("destinoCidade")),
                        "%" + filtro.getDestinoCidade().toLowerCase().trim() + "%"
                ));
            }

            if (filtro.getDestinoEstado() != null && !filtro.getDestinoEstado().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("destinoEstado")),
                        "%" + filtro.getDestinoEstado().toLowerCase().trim() + "%"
                ));
            }

            if (filtro.getProduto() != null && !filtro.getProduto().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("produto")),
                        "%" + filtro.getProduto().toLowerCase().trim() + "%"
                ));
            }

            if (filtro.getEspecie() != null && !filtro.getEspecie().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("especie")),
                        "%" + filtro.getEspecie().toLowerCase().trim() + "%"
                ));
            }

            if (filtro.getTipoCarga() != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoCarga"), filtro.getTipoCarga()));
            }

            if (filtro.getTipoEstadoCarga() != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoEstadoCarga"), filtro.getTipoEstadoCarga()));
            }

            if (filtro.getDataColetaDe() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataColeta"), filtro.getDataColetaDe()));
            }

            if (filtro.getDataColetaAte() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataColeta"), filtro.getDataColetaAte()));
            }

            if (filtro.getDataEntregaDe() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataEntrega"), filtro.getDataEntregaDe()));
            }

            if (filtro.getDataEntregaAte() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataEntrega"), filtro.getDataEntregaAte()));
            }

            if (filtro.getPrecoMinimo() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), filtro.getPrecoMinimo()));
            }

            if (filtro.getPrecoMaximo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("preco"), filtro.getPrecoMaximo()));
            }

            if (filtro.getPesoMinimo() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pesoTotal"), filtro.getPesoMinimo()));
            }

            if (filtro.getPesoMaximo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pesoTotal"), filtro.getPesoMaximo()));
            }

            if (filtro.getPossuiLona() != null) {
                predicates.add(criteriaBuilder.equal(root.get("possuiLona"), filtro.getPossuiLona()));
            }

            if (query != null) {
                query.orderBy(criteriaBuilder.desc(root.get("id")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}