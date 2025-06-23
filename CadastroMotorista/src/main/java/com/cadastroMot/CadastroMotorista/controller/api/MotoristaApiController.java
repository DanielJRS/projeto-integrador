package com.cadastroMot.CadastroMotorista.controller.api;

import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
import Dto.MotoristaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motoristas")
@Tag(name = "Motoristas", description = "API para gerenciamento de motoristas")
public class MotoristaApiController {

    @Autowired
    private MotoristaService motoristaService;

    @Operation(summary = "Listar motoristas", description = "Retorna uma lista de motoristas")
    @GetMapping
    public ResponseEntity<List<MotoristaDTO>> listarMotoristas() {
        List<Motorista> motoristas = motoristaService.listarTodos();
        List<MotoristaDTO> dtos = motoristas.stream()
            .map(m -> new MotoristaDTO(
                m.getId(), m.getNome(), m.getCpf(), m.getEndereco(), m.getCelular(),
                m.getCidade(), m.getEstado(), m.getPais(), m.getCnh(), m.getAntt(), m.getTipoFoto()
            ))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Detalhar motorista", description = "Retorna os detalhes de um motorista pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<MotoristaDTO> detalharMotorista(@PathVariable Long id) {
        Motorista m = motoristaService.buscarPorId(id);
        if (m == null) {
            return ResponseEntity.notFound().build();
        }
        MotoristaDTO dto = new MotoristaDTO(
            m.getId(), m.getNome(), m.getCpf(), m.getEndereco(), m.getCelular(),
            m.getCidade(), m.getEstado(), m.getPais(), m.getCnh(), m.getAntt(), m.getTipoFoto()
        );
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Criar motorista", description = "Cria um novo motorista")
    @PostMapping
    public ResponseEntity<MotoristaDTO> criarMotorista(@RequestBody MotoristaDTO dto) {
        Motorista m = new Motorista();
        m.setNome(dto.getNome());
        m.setCpf(dto.getCpf());
        m.setEndereco(dto.getEndereco());
        m.setCelular(dto.getCelular());
        m.setCidade(dto.getCidade());
        m.setEstado(dto.getEstado());
        m.setPais(dto.getPais());
        m.setCnh(dto.getCnh());
        m.setAntt(dto.getAntt());
        m.setTipoFoto(dto.getTipoFoto());
        Motorista novo = motoristaService.salvar(m);
        MotoristaDTO resp = new MotoristaDTO(
            novo.getId(), novo.getNome(), novo.getCpf(), novo.getEndereco(), novo.getCelular(),
            novo.getCidade(), novo.getEstado(), novo.getPais(), novo.getCnh(), novo.getAntt(), novo.getTipoFoto()
        );
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Atualizar motorista", description = "Atualiza um motorista existente pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<MotoristaDTO> atualizarMotorista(@PathVariable Long id, @RequestBody MotoristaDTO dto) {
        Motorista existente = motoristaService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNome(dto.getNome());
        existente.setCpf(dto.getCpf());
        existente.setEndereco(dto.getEndereco());
        existente.setCelular(dto.getCelular());
        existente.setCidade(dto.getCidade());
        existente.setEstado(dto.getEstado());
        existente.setPais(dto.getPais());
        existente.setCnh(dto.getCnh());
        existente.setAntt(dto.getAntt());
        existente.setTipoFoto(dto.getTipoFoto());
        Motorista atualizado = motoristaService.salvar(existente);
        MotoristaDTO resp = new MotoristaDTO(
            atualizado.getId(), atualizado.getNome(), atualizado.getCpf(), atualizado.getEndereco(), atualizado.getCelular(),
            atualizado.getCidade(), atualizado.getEstado(), atualizado.getPais(), atualizado.getCnh(), atualizado.getAntt(), atualizado.getTipoFoto()
        );
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Deletar motorista", description = "Remove um motorista pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMotorista(@PathVariable Long id) {
        Motorista existente = motoristaService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        motoristaService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }
}
