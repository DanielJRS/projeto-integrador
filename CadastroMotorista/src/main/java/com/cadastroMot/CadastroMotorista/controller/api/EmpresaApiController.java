package com.cadastroMot.CadastroMotorista.controller.api;

import com.cadastroMot.CadastroMotorista.domain.Empresa;
import com.cadastroMot.CadastroMotorista.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaApiController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<Empresa> criarEmpresa(@RequestBody Empresa empresa) {
        Empresa novaEmpresa = empresaService.salvar(empresa);
        return new ResponseEntity<>(novaEmpresa, HttpStatus.CREATED);
    }
}