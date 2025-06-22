package com.cadastroMot.CadastroMotorista.controller.api;

import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import com.cadastroMot.CadastroMotorista.service.TransportadoraService;
import Dto.TransportadoraDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportadoras")
@Tag(name = "Transportadoras", description = "API para gerenciamento de transportadoras")
public class TransportadoraApiController {

    @Autowired
    private TransportadoraService transportadoraService;

    @Operation(summary = "Listar transportadoras", description = "Retorna uma lista de transportadoras")
    @GetMapping
    public ResponseEntity<List<TransportadoraDTO>> listarTransportadoras() {
        List<Transportadora> transportadoras = transportadoraService.listarTodos();
        List<TransportadoraDTO> dtos = transportadoras.stream()
            .map(t -> new TransportadoraDTO(
                t.getId(),
                t.getRazaoSocial(),
                t.getNomeFantasia(),
                t.getCnpj(),
                t.getInscricaoEstadual(),
                t.getEndereco(),
                t.getCidade(),
                t.getEstado(),
                t.getCep(),
                t.getTelefone(),
                t.getEmail(),
                t.getDataFundacao(),
                t.isAtivo()
            ))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Detalhar transportadora", description = "Retorna os detalhes de uma transportadora pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<TransportadoraDTO> detalharTransportadora(@PathVariable Long id) {
        Transportadora t = transportadoraService.buscarPorId(id);
        if (t == null) {
            return ResponseEntity.notFound().build();
        }
        TransportadoraDTO dto = new TransportadoraDTO(
            t.getId(),
            t.getRazaoSocial(),
            t.getNomeFantasia(),
            t.getCnpj(),
            t.getInscricaoEstadual(),
            t.getEndereco(),
            t.getCidade(),
            t.getEstado(),
            t.getCep(),
            t.getTelefone(),
            t.getEmail(),
            t.getDataFundacao(),
            t.isAtivo()
        );
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Criar transportadora", description = "Cria uma nova transportadora")
    @PostMapping
    public ResponseEntity<TransportadoraDTO> criarTransportadora(@RequestBody TransportadoraDTO dto) {
        Transportadora t = new Transportadora();
        t.setRazaoSocial(dto.getRazaoSocial());
        t.setNomeFantasia(dto.getNomeFantasia());
        t.setCnpj(dto.getCnpj());
        t.setInscricaoEstadual(dto.getInscricaoEstadual());
        t.setEndereco(dto.getEndereco());
        t.setCidade(dto.getCidade());
        t.setEstado(dto.getEstado());
        t.setCep(dto.getCep());
        t.setTelefone(dto.getTelefone());
        t.setEmail(dto.getEmail());
        t.setDataFundacao(dto.getDataFundacao());
        t.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        Transportadora nova = transportadoraService.salvar(t);
        TransportadoraDTO resp = new TransportadoraDTO(
            nova.getId(),
            nova.getRazaoSocial(),
            nova.getNomeFantasia(),
            nova.getCnpj(),
            nova.getInscricaoEstadual(),
            nova.getEndereco(),
            nova.getCidade(),
            nova.getEstado(),
            nova.getCep(),
            nova.getTelefone(),
            nova.getEmail(),
            nova.getDataFundacao(),
            nova.isAtivo()
        );
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Atualizar transportadora", description = "Atualiza uma transportadora existente pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<TransportadoraDTO> atualizarTransportadora(@PathVariable Long id, @RequestBody TransportadoraDTO dto) {
        Transportadora existente = transportadoraService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setRazaoSocial(dto.getRazaoSocial());
        existente.setNomeFantasia(dto.getNomeFantasia());
        existente.setCnpj(dto.getCnpj());
        existente.setInscricaoEstadual(dto.getInscricaoEstadual());
        existente.setEndereco(dto.getEndereco());
        existente.setCidade(dto.getCidade());
        existente.setEstado(dto.getEstado());
        existente.setCep(dto.getCep());
        existente.setTelefone(dto.getTelefone());
        existente.setEmail(dto.getEmail());
        existente.setDataFundacao(dto.getDataFundacao());
        existente.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : existente.isAtivo());
        Transportadora atualizada = transportadoraService.salvar(existente);
        TransportadoraDTO resp = new TransportadoraDTO(
            atualizada.getId(),
            atualizada.getRazaoSocial(),
            atualizada.getNomeFantasia(),
            atualizada.getCnpj(),
            atualizada.getInscricaoEstadual(),
            atualizada.getEndereco(),
            atualizada.getCidade(),
            atualizada.getEstado(),
            atualizada.getCep(),
            atualizada.getTelefone(),
            atualizada.getEmail(),
            atualizada.getDataFundacao(),
            atualizada.isAtivo()
        );
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Deletar transportadora", description = "Remove uma transportadora pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTransportadora(@PathVariable Long id) {
        Transportadora existente = transportadoraService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        transportadoraService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }
}
