package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoristaService {
    private final MotoristaRepository motoristaRepository;

    @Autowired
    public MotoristaService(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    public Motorista salvar (Motorista motorista){
      return motoristaRepository.save(motorista);
    }

    public List<Motorista> listarTodos(){
        return motoristaRepository.findAll();
    }

    public Motorista buscarPorId(Long id) {
        return null;
    }
}
