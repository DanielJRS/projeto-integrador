package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.*;
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

    public List<Frete> buscarFretesPorEmpresa(Empresa empresa){
        return freteRepository.findByEmpresaFrete(empresa);
    }

    public Long contarFretesEStatus(Motorista motorista, TipoEstadoFrete status){
        return freteRepository.countByMotoristaFreteAndStatus(motorista, status);
    }

    public Long buscarFretesPorEmpresa(Empresa empresa, TipoEstadoFrete status){
        return freteRepository.countByEmpresaFreteAndStatus(empresa, status);
    }
}
