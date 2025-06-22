package com.cadastroMot.CadastroMotorista.controller.api;

import com.cadastroMot.CadastroMotorista.domain.Empresa;
import com.cadastroMot.CadastroMotorista.service.EmpresaService;
import Dto.EmpresaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "API para gerenciamento de empresas")
public class EmpresaApiController {

    @Autowired
    private EmpresaService empresaService;

    @Operation(summary = "Listar empresas", description = "Retorna uma lista de empresas")
    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> listarEmpresas() {
        List<Empresa> empresas = empresaService.listarTodos();
        List<EmpresaDTO> dtos = empresas.stream()
            .map(e -> new EmpresaDTO(
                e.getId(), e.getRazaoSocial(), e.getNomeFantasia(), e.getCnpj(), e.getInscricaoEstadual(),
                e.getEndereco(), e.getCidade(), e.getEstado(), e.getCep(), e.getTelefone(), e.getEmail(), e.getDataFundacao()
            ))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Detalhar empresa", description = "Retorna os detalhes de uma empresa pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> detalharEmpresa(@PathVariable Long id) {
        Empresa e = empresaService.buscarPorId(id);
        if (e == null) {
            return ResponseEntity.notFound().build();
        }
        EmpresaDTO dto = new EmpresaDTO(
            e.getId(), e.getRazaoSocial(), e.getNomeFantasia(), e.getCnpj(), e.getInscricaoEstadual(),
            e.getEndereco(), e.getCidade(), e.getEstado(), e.getCep(), e.getTelefone(), e.getEmail(), e.getDataFundacao()
        );
        return ResponseEntity.ok(dto);
    }


    @Operation(summary = "Deletar empresa", description = "Remove uma empresa pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        Empresa existente = empresaService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        empresaService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }
}
