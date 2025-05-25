package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.repository.CargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargaService {

    private final CargaRepository cargaRepository;

    @Autowired
    public CargaService(CargaRepository cargaRepository) {
        this.cargaRepository = cargaRepository;
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

    public Carga atualizar(Carga carga) {
        if (carga != null && carga.getId() != null && cargaRepository.existsById(carga.getId())) {
            return cargaRepository.save(carga);
        }
        return null;
    }

    public List<Carga> buscarPorFiltro(String origem, String destino, String produto, String especie) {
        if (origem != null && !origem.isEmpty()) {
            if (destino != null && !destino.isEmpty()) {
                if (produto != null && !produto.isEmpty()) {
                    if (especie != null && !especie.isEmpty()) {
                        return cargaRepository.findByOrigemAndDestinoAndProdutoAndEspecie(origem, destino, produto, especie);
                    }
                    return cargaRepository.findByOrigemAndDestinoAndProduto(origem, destino, produto);
                }
                return cargaRepository.findByOrigemAndDestino(origem, destino);
            }
            return cargaRepository.findByOrigem(origem);
        }
        return cargaRepository.findAll();
    }

    public List<Carga> buscarCargaPorMotorista(Long motoristaId){
        return cargaRepository.findByMotoristaId(motoristaId);
    }
}
