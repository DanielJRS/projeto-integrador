package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.TipoCarga;
import com.cadastroMot.CadastroMotorista.repository.CargaRepository;
import com.cadastroMot.CadastroMotorista.repository.CargaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        return cargaRepository.save(carga);
    }

    public void deletar(Long id) {
        cargaRepository.deleteById(id);
    }

    public Carga atualizar(Carga cargaAtualizada) {
        if (cargaAtualizada != null && cargaAtualizada.getId() != null && cargaRepository.existsById(cargaAtualizada.getId())) {
            Optional<Carga> cargaExistenteOpt = cargaRepository.findById(cargaAtualizada.getId());
            if (cargaExistenteOpt.isPresent()) {
                Carga cargaExistente = cargaExistenteOpt.get();

                cargaExistente.setDataColeta(cargaAtualizada.getDataColeta());
                cargaExistente.setDataEntrega(cargaAtualizada.getDataEntrega());
                cargaExistente.setOrigemEstado(cargaAtualizada.getOrigemEstado());
                cargaExistente.setOrigemCidade(cargaAtualizada.getOrigemCidade());
                cargaExistente.setDestinoEstado(cargaAtualizada.getDestinoEstado());
                cargaExistente.setDestinoCidade(cargaAtualizada.getDestinoCidade());
                cargaExistente.setTipoCarga(cargaAtualizada.getTipoCarga());
                cargaExistente.setPossuiLona(cargaAtualizada.getPossuiLona());
                cargaExistente.setProduto(cargaAtualizada.getProduto());
                cargaExistente.setEspecie(cargaAtualizada.getEspecie());
                cargaExistente.setPesoTotal(cargaAtualizada.getPesoTotal());
                cargaExistente.setPreco(cargaAtualizada.getPreco());
                cargaExistente.setLimiteAltura(cargaAtualizada.getLimiteAltura());

                cargaExistente.setVeiculosLeves(cargaAtualizada.getVeiculosLeves());
                cargaExistente.setVeiculosMedios(cargaAtualizada.getVeiculosMedios());
                cargaExistente.setVeiculosPesados(cargaAtualizada.getVeiculosPesados());
                cargaExistente.setFretesFechados(cargaAtualizada.getFretesFechados());
                cargaExistente.setFretesAbertos(cargaAtualizada.getFretesAbertos());
                cargaExistente.setFretesEspeciais(cargaAtualizada.getFretesEspeciais());

                return cargaRepository.save(cargaExistente);
            }
        }
        return null;
    }

    public List<Carga> buscarPorFiltro(String origemCidade, String origemEstado,
                                       String destinoCidade, String destinoEstado,
                                       String produto, String especie) {

        List<Carga> todasCargas = cargaRepository.findAll();

        return todasCargas.stream()
                .filter(c -> origemCidade == null || origemCidade.isEmpty() ||
                        c.getOrigemCidade().equals(origemCidade))
                .filter(c -> origemEstado == null || origemEstado.isEmpty() ||
                        c.getOrigemEstado().equals(origemEstado))
                .filter(c -> destinoCidade == null || destinoCidade.isEmpty() ||
                        c.getDestinoCidade().equals(destinoCidade))
                .filter(c -> destinoEstado == null || destinoEstado.isEmpty() ||
                        c.getDestinoEstado().equals(destinoEstado))
                .filter(c -> produto == null || produto.isEmpty() ||
                        c.getProduto().equals(produto))
                .filter(c -> especie == null || especie.isEmpty() ||
                        c.getEspecie().equals(especie))
                .collect(Collectors.toList());
    }


    public List<Carga> buscarPorFiltro(String origem, String destino, String produto, String especie) {
        return buscarPorFiltro(origem, null, destino, null, produto, especie);
    }
    @Transactional
    public Carga processarFormularioCarga(
            Carga carga,
            String tipoCarga,
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


        if (tipoCarga != null && !tipoCarga.isEmpty()) {
            carga.setTipoCarga(TipoCarga.fromDescricao(tipoCarga));
        }


        if (possuiLona != null) {
            carga.setPossuiLona("Sim".equalsIgnoreCase(possuiLona));
        }


        carga.setPesoTotal(pesoTotal);
        carga.setLimiteAltura(limiteAltura);
        carga.setVolume(volume);


        if (veiculosLeves != null) {
            carga.setVeiculosLeves(Arrays.asList(veiculosLeves));
        }


        if (veiculosMedios != null) {
            carga.setVeiculosMedios(Arrays.asList(veiculosMedios));
        }


        if (veiculosPesados != null) {
            carga.setVeiculosPesados(Arrays.asList(veiculosPesados));
        }


        if (freteFechado != null) {
            carga.setFretesFechados(Arrays.asList(freteFechado));
        }


        if (freteAberto != null) {
            carga.setFretesAbertos(Arrays.asList(freteAberto));
        }


        if (freteEspecial != null) {
            carga.setFretesEspeciais(Arrays.asList(freteEspecial));
        }

        return carga;
    }


}


