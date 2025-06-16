package com.cadastroMot.CadastroMotorista.controller.api;

import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import com.cadastroMot.CadastroMotorista.service.TransportadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transportadoras")
public class TransportadoraApiController {

    private final TransportadoraService transportadoraService;

    @Autowired
    public TransportadoraApiController(TransportadoraService transportadoraService) {
        this.transportadoraService = transportadoraService;
    }

    @PostMapping
    public ResponseEntity<Transportadora> criarTransportadora(@RequestBody Transportadora transportadora) {
        Transportadora transportadoraSalva = transportadoraService.salvar(transportadora);
        return ResponseEntity.status(HttpStatus.CREATED).body(transportadoraSalva);
    }
}