package com.cadastroMot.CadastroMotorista.controller.api;

import com.cadastroMot.CadastroMotorista.domain.Veiculo;
import com.cadastroMot.CadastroMotorista.service.VeiculoService;

import Dto.VeiculoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@Tag(name = "Veículos", description = "API para gerenciamento de veículos")
public class VeiculoApiController {

    @Autowired
    private VeiculoService veiculoService;

    @Operation(summary = "Listar veículos", description = "Retorna uma lista de veículos")
    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> listarVeiculos() {
        List<Veiculo> veiculos = veiculoService.listarTodos();
        List<VeiculoDTO> dtos = veiculos.stream()
            .map(v -> new VeiculoDTO(
                v.getId(), v.getPlaca(), v.getModelo(), v.getMarca(),
                v.getCapacidadeCarga(), v.getRenavam(), v.getChassi()
            ))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Detalhar veículo", description = "Retorna os detalhes de um veículo pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> detalharVeiculo(@PathVariable Long id) {
        Veiculo veiculo = veiculoService.buscarPorId(id);
        if (veiculo == null) {
            return ResponseEntity.notFound().build();
        }
        VeiculoDTO dto = new VeiculoDTO(
            veiculo.getId(), veiculo.getPlaca(), veiculo.getModelo(), veiculo.getMarca(),
            veiculo.getCapacidadeCarga(), veiculo.getRenavam(), veiculo.getChassi()
        );
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Criar veículo", description = "Cria um novo veículo")
    @PostMapping
    public ResponseEntity<VeiculoDTO> criarVeiculo(@RequestBody VeiculoDTO veiculoDTO) {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoDTO.getPlaca());
        veiculo.setModelo(veiculoDTO.getModelo());
        veiculo.setMarca(veiculoDTO.getMarca());
        veiculo.setCapacidadeCarga(veiculoDTO.getCapacidadeCarga());
        veiculo.setRenavam(veiculoDTO.getRenavam());
        veiculo.setChassi(veiculoDTO.getChassi());
        Veiculo novoVeiculo = veiculoService.salvar(veiculo);
        VeiculoDTO dto = new VeiculoDTO(
            novoVeiculo.getId(), novoVeiculo.getPlaca(), novoVeiculo.getModelo(), novoVeiculo.getMarca(),
            novoVeiculo.getCapacidadeCarga(), novoVeiculo.getRenavam(), novoVeiculo.getChassi()
        );
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Atualizar veículo", description = "Atualiza um veículo existente pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO> atualizarVeiculo(@PathVariable Long id, @RequestBody VeiculoDTO veiculoDTO) {
        Veiculo existente = veiculoService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setPlaca(veiculoDTO.getPlaca());
        existente.setModelo(veiculoDTO.getModelo());
        existente.setMarca(veiculoDTO.getMarca());
        existente.setCapacidadeCarga(veiculoDTO.getCapacidadeCarga());
        existente.setRenavam(veiculoDTO.getRenavam());
        existente.setChassi(veiculoDTO.getChassi());
        Veiculo atualizado = veiculoService.salvar(existente);
        VeiculoDTO dto = new VeiculoDTO(
            atualizado.getId(), atualizado.getPlaca(), atualizado.getModelo(), atualizado.getMarca(),
            atualizado.getCapacidadeCarga(), atualizado.getRenavam(), atualizado.getChassi()
        );
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Deletar veículo", description = "Remove um veículo pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id) {
        Veiculo existente = veiculoService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        veiculoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
