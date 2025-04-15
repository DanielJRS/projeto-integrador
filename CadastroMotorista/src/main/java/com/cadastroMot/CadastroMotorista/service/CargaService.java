package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.repository.CargaRepository;
import com.cadastroMot.CadastroMotorista.repository.CargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CargaService {

    @Autowired
    private CargaRepository cargaRepository;


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

    public Carga atualizar(Carga carga) {
        if (carga != null && carga.getId() != null && cargaRepository.existsById(carga.getId())) {
            return cargaRepository.save(carga);
        }
        return null;
    }


    public List<Carga> buscarPorFiltro(String origem, String destino, String produto) {
        return cargaRepository.findByOrigemAndDestinoAndProduto(origem, destino, produto);
    }
}
