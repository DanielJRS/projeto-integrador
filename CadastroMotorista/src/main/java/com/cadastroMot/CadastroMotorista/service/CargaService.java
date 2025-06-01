package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.CargaFiltro;
import Dto.CargaSpecification;
import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.TipoCarga;
import com.cadastroMot.CadastroMotorista.repository.CargaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Carga cargaExistente = buscarPorId(cargaAtualizada.getId());
        if (cargaExistente == null) {
            throw new RuntimeException("Carga não encontrada");
        }

        cargaExistente.setTipoCarga(cargaAtualizada.getTipoCarga());
        cargaExistente.setTipoEstadoCarga(cargaAtualizada.getTipoEstadoCarga());
        cargaExistente.setPossuiLona(cargaAtualizada.getPossuiLona());
        cargaExistente.setVeiculosLeves(cargaAtualizada.getVeiculosLeves());
        cargaExistente.setVeiculosMedios(cargaAtualizada.getVeiculosMedios());
        cargaExistente.setVeiculosPesados(cargaAtualizada.getVeiculosPesados());
        cargaExistente.setFretesFechados(cargaAtualizada.getFretesFechados());
        cargaExistente.setFretesAbertos(cargaAtualizada.getFretesAbertos());
        cargaExistente.setFretesEspeciais(cargaAtualizada.getFretesEspeciais());
        cargaExistente.setPesoTotal(cargaAtualizada.getPesoTotal());
        cargaExistente.setLimiteAltura(cargaAtualizada.getLimiteAltura());
        cargaExistente.setVolume(cargaAtualizada.getVolume());

        return salvar(cargaExistente);
    }

    public List<Carga> buscarComFiltro(CargaFiltro filtro) {
        return cargaRepository.findAll(CargaSpecification.filtrar(filtro));
    }

    @Transactional
    public Carga processarFormularioCarga(
            Carga carga,
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


        if (tipoCarga != null && !tipoCarga.isEmpty()) {
            carga.setTipoCarga(TipoCarga.fromDescricao(tipoCarga));
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




    public List<Carga> buscarCargaPorMotorista(Long motoristaId){
        return cargaRepository.findByMotoristaId(motoristaId);
    }



}


