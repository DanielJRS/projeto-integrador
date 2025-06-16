package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.*;
import Dto.CargaSpecification;
import com.cadastroMot.CadastroMotorista.repository.CargaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CargaService {

    @Autowired
    private CargaRepository cargaRepository;

    public List<String> listarCidades(){
        List<String> cidades = new ArrayList<>();
        cidades.add("São Paulo");
        cidades.add("Rio de Janeiro");
        cidades.add("Belo Horizonte");
        cidades.add("Brasília");
        cidades.add("Salvador");
        cidades.add("Curitiba");
        cidades.add("Fortaleza");
        cidades.add("Recife");
        cidades.add("Porto Alegre");
        cidades.add("Goiânia");
        cidades.add("Manaus");
        cidades.add("Belém");
        cidades.add("Vitória");
        cidades.add("Natal");
        cidades.add("João Pessoa");
        cidades.add("Aracaju");
        cidades.add("Maceió");
        cidades.add("Campo Grande");
        cidades.add("Cuiabá");
        cidades.add("Florianópolis");
        return cidades;
    }

    public List<String> listarEstados(){
        List<String> estados = new ArrayList<>();
        estados.add("AC");
        estados.add("AL");
        estados.add("AP");
        estados.add("AM");
        estados.add("BA");
        estados.add("CE");
        estados.add("DF");
        estados.add("ES");
        estados.add("GO");
        estados.add("MA");
        estados.add("MT");
        estados.add("MS");
        estados.add("MG");
        estados.add("PA");
        estados.add("PB");
        estados.add("PR");
        estados.add("PE");
        estados.add("PI");
        estados.add("RJ");
        estados.add("RN");
        estados.add("RS");
        estados.add("RO");
        estados.add("RR");
        estados.add("SC");
        estados.add("SP");
        estados.add("SE");
        estados.add("TO");
        return estados;
    }

    public List<Carga> listarTodos() {
        return cargaRepository.findAll();
    }

    public Carga buscarPorId(Long id) {
        return cargaRepository.findById(id).orElse(null);
    }

    public Carga salvar(Carga carga) {
        // Validações antes de salvar
        validarCarga(carga);
        return cargaRepository.save(carga);
    }

    public void deletar(Long id) {
        if (buscarPorId(id) == null) {
            throw new RuntimeException("Carga não encontrada para exclusão");
        }
        cargaRepository.deleteById(id);
    }

    public Carga atualizar(Carga cargaAtualizada) {
        Carga cargaExistente = buscarPorId(cargaAtualizada.getId());
        if (cargaExistente == null) {
            throw new RuntimeException("Carga não encontrada");
        }

        atualizarCamposCarga(cargaExistente, cargaAtualizada);

        return salvar(cargaExistente);
    }

    private void atualizarCamposCarga(Carga cargaExistente, Carga cargaAtualizada) {

        cargaExistente.setOrigemCidade(cargaAtualizada.getOrigemCidade());
        cargaExistente.setOrigemEstado(cargaAtualizada.getOrigemEstado());
        cargaExistente.setDataColeta(cargaAtualizada.getDataColeta());
        cargaExistente.setDestinoCidade(cargaAtualizada.getDestinoCidade());
        cargaExistente.setDestinoEstado(cargaAtualizada.getDestinoEstado());
        cargaExistente.setDataEntrega(cargaAtualizada.getDataEntrega());

        cargaExistente.setProduto(cargaAtualizada.getProduto());
        cargaExistente.setEspecie(cargaAtualizada.getEspecie());
        cargaExistente.setVeiculo(cargaAtualizada.getVeiculo());

        cargaExistente.setPreco(cargaAtualizada.getPreco());
        cargaExistente.setPrecoFrete(cargaAtualizada.getPrecoFrete());

        cargaExistente.setTipoCarga(cargaAtualizada.getTipoCarga());
        cargaExistente.setTipoEstadoCarga(cargaAtualizada.getTipoEstadoCarga());

        cargaExistente.setPossuiLona(cargaAtualizada.getPossuiLona());
        cargaExistente.setPesoTotal(cargaAtualizada.getPesoTotal());
        cargaExistente.setLimiteAltura(cargaAtualizada.getLimiteAltura());
        cargaExistente.setVolume(cargaAtualizada.getVolume());

        if (cargaAtualizada.getVeiculosLeves() != null) {
            cargaExistente.setVeiculosLeves(new ArrayList<>(cargaAtualizada.getVeiculosLeves()));
        }
        if (cargaAtualizada.getVeiculosMedios() != null) {
            cargaExistente.setVeiculosMedios(new ArrayList<>(cargaAtualizada.getVeiculosMedios()));
        }
        if (cargaAtualizada.getVeiculosPesados() != null) {
            cargaExistente.setVeiculosPesados(new ArrayList<>(cargaAtualizada.getVeiculosPesados()));
        }

        if (cargaAtualizada.getFretesFechados() != null) {
            cargaExistente.setFretesFechados(new ArrayList<>(cargaAtualizada.getFretesFechados()));
        }
        if (cargaAtualizada.getFretesAbertos() != null) {
            cargaExistente.setFretesAbertos(new ArrayList<>(cargaAtualizada.getFretesAbertos()));
        }
        if (cargaAtualizada.getFretesEspeciais() != null) {
            cargaExistente.setFretesEspeciais(new ArrayList<>(cargaAtualizada.getFretesEspeciais()));
        }

        if (cargaAtualizada.getEmpresaCarga() != null) {
            cargaExistente.setEmpresaCarga(cargaAtualizada.getEmpresaCarga());
        }
        if (cargaAtualizada.getMotorista() != null) {
            cargaExistente.setMotorista(cargaAtualizada.getMotorista());
        }
        if (cargaAtualizada.getFrete() != null) {
            cargaExistente.setFrete(cargaAtualizada.getFrete());
        }
    }

    public List<Carga> buscarPorEmpresa(Empresa empresa) {
        return cargaRepository.findByEmpresaCarga(empresa);
    }

    public List<Carga> buscarComFiltro(CargaFiltro filtro) {
        if (filtro == null) {
            return listarTodos();
        }
        return cargaRepository.findAll(CargaSpecification.filtrar(filtro));
    }


    @Transactional
    public Carga processarFormularioCarga(
            Carga carga,
            String origemCidade,
            String origemEstado,
            LocalDate dataColeta,
            String destinoCidade,
            String destinoEstado,
            LocalDate dataEntrega,
            String produto,
            String especie,
            String veiculo,
            Double preco,
            Double precoFrete,
            String tipoCarga,
            String tipoEstadoCarga,
            String possuiLona,
            String[] veiculosLeves,
            String[] veiculosMedios,
            String[] veiculosPesados,
            String[] freteFechado,
            String[] freteAberto,
            String[] freteEspecial,
            Double pesoTotal,
            Double limiteAltura,
            Double volume) {

        // Campos básicos de localização e datas
        if (origemCidade != null && !origemCidade.isEmpty()) {
            carga.setOrigemCidade(origemCidade);
        }
        if (origemEstado != null && !origemEstado.isEmpty()) {
            carga.setOrigemEstado(origemEstado);
        }
        if (dataColeta != null) {
            carga.setDataColeta(dataColeta);
        }
        if (destinoCidade != null && !destinoCidade.isEmpty()) {
            carga.setDestinoCidade(destinoCidade);
        }
        if (destinoEstado != null && !destinoEstado.isEmpty()) {
            carga.setDestinoEstado(destinoEstado);
        }
        if (dataEntrega != null) {
            carga.setDataEntrega(dataEntrega);
        }

        // Campos de produto
        if (produto != null && !produto.isEmpty()) {
            carga.setProduto(produto);
        }
        if (especie != null && !especie.isEmpty()) {
            carga.setEspecie(especie);
        }
        if (veiculo != null && !veiculo.isEmpty()) {
            carga.setVeiculo(veiculo);
        }

        // Campos financeiros
        if (preco != null) {
            carga.setPreco(preco);
        }
        if (precoFrete != null) {
            carga.setPrecoFrete(precoFrete);
        }

        // Enums
        if (tipoCarga != null && !tipoCarga.isEmpty()) {
            try {
                carga.setTipoCarga(TipoCarga.valueOf(tipoCarga));
            } catch (IllegalArgumentException e) {
                // Tentar usar método fromDescricao se existir
                try {
                    carga.setTipoCarga(TipoCarga.fromDescricao(tipoCarga));
                } catch (Exception ex) {
                    // Log do erro ou definir valor padrão
                    System.err.println("Erro ao definir tipo de carga: " + tipoCarga);
                }
            }
        }

        if (tipoEstadoCarga != null && !tipoEstadoCarga.isEmpty()) {
            try {
                carga.setTipoEstadoCarga(TipoEstadoCarga.valueOf(tipoEstadoCarga));
            } catch (IllegalArgumentException e) {
                System.err.println("Erro ao definir estado da carga: " + tipoEstadoCarga);
            }
        }

        // Campo boolean
        carga.setPossuiLona("on".equalsIgnoreCase(possuiLona) || "true".equalsIgnoreCase(possuiLona));

        // Campos numéricos
        carga.setPesoTotal(pesoTotal);
        carga.setLimiteAltura(limiteAltura);
        carga.setVolume(volume);

        // Listas de veículos
        if (veiculosLeves != null) {
            carga.setVeiculosLeves(new ArrayList<>(Arrays.asList(veiculosLeves)));
        }
        if (veiculosMedios != null) {
            carga.setVeiculosMedios(new ArrayList<>(Arrays.asList(veiculosMedios)));
        }
        if (veiculosPesados != null) {
            carga.setVeiculosPesados(new ArrayList<>(Arrays.asList(veiculosPesados)));
        }

        // Listas de fretes
        if (freteFechado != null) {
            carga.setFretesFechados(new ArrayList<>(Arrays.asList(freteFechado)));
        }
        if (freteAberto != null) {
            carga.setFretesAbertos(new ArrayList<>(Arrays.asList(freteAberto)));
        }
        if (freteEspecial != null) {
            carga.setFretesEspeciais(new ArrayList<>(Arrays.asList(freteEspecial)));
        }

        return carga;
    }

    public List<Carga> buscarCargaPorMotorista(Long motoristaId){
        return cargaRepository.findByMotoristaId(motoristaId);
    }

    public List<Carga> buscarCargasPorEmpresa(Long empresaId) {
        return cargaRepository.findByEmpresaCargaId(empresaId);
    }

    public List<Carga> buscarCargasDisponiveis() {
        return cargaRepository.findByTipoEstadoCarga(TipoEstadoCarga.DISPONIVEL);
    }

    public List<Carga> buscarPorOrigem(String cidade, String estado) {
        return cargaRepository.findByOrigemCidadeAndOrigemEstado(cidade, estado);
    }

    public List<Carga> buscarPorDestino(String cidade, String estado) {
        return cargaRepository.findByDestinoCidadeAndDestinoEstado(cidade, estado);
    }

    public List<Carga> buscarPorRota(String origemCidade, String origemEstado,
                                     String destinoCidade, String destinoEstado) {
        return cargaRepository.findByOrigemCidadeAndOrigemEstadoAndDestinoCidadeAndDestinoEstado(
                origemCidade, origemEstado, destinoCidade, destinoEstado);
    }

    public List<Carga> buscarPorProduto(String produto) {
        return cargaRepository.findByProdutoContainingIgnoreCase(produto);
    }

    public List<Carga> buscarPorFaixaPreco(Double precoMin, Double precoMax) {
        return cargaRepository.findByPrecoBetween(precoMin, precoMax);
    }

    public List<Carga> buscarPorDataColeta(LocalDate dataInicio, LocalDate dataFim) {
        return cargaRepository.findByDataColetaBetween(dataInicio, dataFim);
    }

    public List<Carga> buscarPorDataEntrega(LocalDate dataInicio, LocalDate dataFim) {
        return cargaRepository.findByDataEntregaBetween(dataInicio, dataFim);
    }

    public long contarCargasDisponiveis() {
        return cargaRepository.countByTipoEstadoCarga(TipoEstadoCarga.DISPONIVEL);
    }

    public long contarCargasPorEmpresa(Long empresaId) {
        return cargaRepository.countByEmpresaCargaId(empresaId);
    }

    @Transactional
    public void alterarStatusCarga(Long cargaId, TipoEstadoCarga novoStatus) {
        Carga carga = buscarPorId(cargaId);
        if (carga == null) {
            throw new RuntimeException("Carga não encontrada");
        }
        carga.setTipoEstadoCarga(novoStatus);
        salvar(carga);
    }

    @Transactional
    public void atribuirMotorista(Long cargaId, Long motoristaId) {
        Carga carga = buscarPorId(cargaId);
        if (carga == null) {
            throw new RuntimeException("Carga não encontrada");
        }
        // Assumindo que você tem um método para buscar motorista
        // Motorista motorista = motoristaService.buscarPorId(motoristaId);
        // carga.setMotorista(motorista);
        carga.setTipoEstadoCarga(TipoEstadoCarga.DISPONIVEL); // ou outro status apropriado
        salvar(carga);
    }

    private void validarCarga(Carga carga) {
        List<String> erros = new ArrayList<>();

        if (carga.getOrigemCidade() == null || carga.getOrigemCidade().trim().isEmpty()) {
            erros.add("Cidade de origem é obrigatória");
        }

        if (carga.getOrigemEstado() == null || carga.getOrigemEstado().trim().isEmpty()) {
            erros.add("Estado de origem é obrigatório");
        }

        if (carga.getDestinoCidade() == null || carga.getDestinoCidade().trim().isEmpty()) {
            erros.add("Cidade de destino é obrigatória");
        }

        if (carga.getDestinoEstado() == null || carga.getDestinoEstado().trim().isEmpty()) {
            erros.add("Estado de destino é obrigatório");
        }

        if (carga.getDataColeta() == null) {
            erros.add("Data de coleta é obrigatória");
        }

        if (carga.getDataEntrega() == null) {
            erros.add("Data de entrega é obrigatória");
        }

        if (carga.getDataColeta() != null && carga.getDataEntrega() != null &&
                carga.getDataColeta().isAfter(carga.getDataEntrega())) {
            erros.add("Data de coleta não pode ser posterior à data de entrega");
        }

        if (carga.getProduto() == null || carga.getProduto().trim().isEmpty()) {
            erros.add("Produto é obrigatório");
        }

        if (carga.getPreco() != null && carga.getPreco() < 0) {
            erros.add("Preço não pode ser negativo");
        }

        if (carga.getPrecoFrete() != null && carga.getPrecoFrete() < 0) {
            erros.add("Preço do frete não pode ser negativo");
        }

        if (carga.getPesoTotal() != null && carga.getPesoTotal() <= 0) {
            erros.add("Peso total deve ser maior que zero");
        }

        if (carga.getVolume() != null && carga.getVolume() <= 0) {
            erros.add("Volume deve ser maior que zero");
        }

        if (carga.getLimiteAltura() != null && carga.getLimiteAltura() <= 0) {
            erros.add("Limite de altura deve ser maior que zero");
        }

        if (!erros.isEmpty()) {
            throw new RuntimeException("Erros de validação: " + String.join(", ", erros));
        }
    }

    public Map<String, Object> obterEstatisticasCargas() {
        Map<String, Object> estatisticas = new HashMap<>();

        estatisticas.put("totalCargas", cargaRepository.count());
        estatisticas.put("cargasDisponiveis", contarCargasDisponiveis());

        // Outras estatísticas podem ser adicionadas conforme necessário
        return estatisticas;
    }

    public List<String> obterTiposVeiculoDisponiveis() {
        List<String> tipos = new ArrayList<>();
        tipos.add("Todos");
        tipos.add("3/4");
        tipos.add("HR");
        tipos.add("Toco");
        tipos.add("VLC");
        tipos.add("Bitruck");
        tipos.add("Truck");
        tipos.add("Bi-trem");
        tipos.add("Carreta");
        tipos.add("Rodotrem");
        tipos.add("Carreta LS");
        return tipos;
    }
}