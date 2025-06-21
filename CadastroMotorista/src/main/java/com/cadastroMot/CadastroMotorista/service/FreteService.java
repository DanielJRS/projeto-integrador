package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.Frete;
import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.domain.TipoEstadoCarga;
import com.cadastroMot.CadastroMotorista.domain.TipoEstadoFrete;
import com.cadastroMot.CadastroMotorista.repository.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FreteService {

    @Autowired
    private final FreteRepository freteRepository;

    public FreteService(FreteRepository freteRepository) {
        this.freteRepository = freteRepository;
    }

    public Frete salvar (Frete frete){
        return freteRepository.save(frete);
    }

    public Optional<Frete> buscarPorId(Long id) {
        return freteRepository.findById(id);
    }

    public List<Frete> buscarFretesPorMotorista(Motorista motorista) {
        return freteRepository.findByMotoristaFrete(motorista);
    }
//
//    public Long contarFretesAtivosEStatus(Motorista motorista){
//        return freteRepository.countByMotoristaFreteAndStatus(motorista, TipoEstadoFrete.ATIVO.toString());
//    }

    public Long contarFretesEStatus(Motorista motorista, TipoEstadoFrete status){
        return freteRepository.countByMotoristaFreteAndStatus(motorista, status);
    }
}
