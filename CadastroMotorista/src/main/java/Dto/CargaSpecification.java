package Dto;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.CargaFiltro;
import org.springframework.data.jpa.domain.Specification;

public class CargaSpecification {

    public static Specification<Carga> filtrar(CargaFiltro filtro) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (filtro.getOrigemCidade() != null)
                predicates.getExpressions().add(cb.equal(root.get("origemCidade"), filtro.getOrigemCidade()));

            if (filtro.getOrigemEstado() != null)
                predicates.getExpressions().add(cb.equal(root.get("origemEstado"), filtro.getOrigemEstado()));

            if (filtro.getDataColeta() != null)
                predicates.getExpressions().add(cb.equal(root.get("dataColeta"), filtro.getDataColeta()));

            if (filtro.getDestinoCidade() != null)
                predicates.getExpressions().add(cb.equal(root.get("destinoCidade"), filtro.getDestinoCidade()));

            if (filtro.getDestinoEstado() != null)
                predicates.getExpressions().add(cb.equal(root.get("destinoEstado"), filtro.getDestinoEstado()));

            if (filtro.getDataEntrega() != null)
                predicates.getExpressions().add(cb.equal(root.get("dataEntrega"), filtro.getDataEntrega()));

            if (filtro.getProduto() != null)
                predicates.getExpressions().add(cb.equal(root.get("produto"), filtro.getProduto()));

            if (filtro.getEspecie() != null)
                predicates.getExpressions().add(cb.equal(root.get("especie"), filtro.getEspecie()));

            if (filtro.getVeiculo() != null)
                predicates.getExpressions().add(cb.equal(root.get("veiculo"), filtro.getVeiculo()));

            if (filtro.getPreco() != null)
                predicates.getExpressions().add(cb.equal(root.get("preco"), filtro.getPreco()));

            if (filtro.getTipoCarga() != null)
                predicates.getExpressions().add(cb.equal(root.get("tipoCarga"), filtro.getTipoCarga()));

            if (filtro.getTipoEstadoCarga() != null)
                predicates.getExpressions().add(cb.equal(root.get("tipoEstadoCarga"), filtro.getTipoEstadoCarga()));

            if (filtro.getPossuiLona() != null)
                predicates.getExpressions().add(cb.equal(root.get("possuiLona"), filtro.getPossuiLona()));

            if (filtro.getPesoTotal() != null)
                predicates.getExpressions().add(cb.equal(root.get("pesoTotal"), filtro.getPesoTotal()));

            if (filtro.getLimiteAltura() != null)
                predicates.getExpressions().add(cb.equal(root.get("limiteAltura"), filtro.getLimiteAltura()));

            if (filtro.getVolume() != null)
                predicates.getExpressions().add(cb.equal(root.get("volume"), filtro.getVolume()));

            // *** FILTRO POR EMPRESA - CORRIGIDO ***
            if (filtro.getEmpresa() != null) {
                System.out.println("=== DEBUG SPECIFICATION ===");
                System.out.println("Aplicando filtro para empresa ID: " + filtro.getEmpresa().getId());

                // CORREÇÃO: Comparar apenas o ID da empresa
                var empresaPredicate = cb.equal(root.get("empresaCarga").get("id"), filtro.getEmpresa().getId());

                predicates.getExpressions().add(empresaPredicate);
                System.out.println("Filtro por empresa ID aplicado na query");
                System.out.println("=== FIM DEBUG SPECIFICATION ===");
            }

            return predicates;
        };
    }
}