package com.cadastroMot.CadastroMotorista.controller.api;

import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motoristas")
public class MotoristaApiController {

    private final MotoristaService motoristaService;

    @Autowired
    public MotoristaApiController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @GetMapping
    public ResponseEntity<List<Motorista>> listarMotoristas() {
        List<Motorista> motoristas = motoristaService.listarTodos();
        return ResponseEntity.ok(motoristas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorista> obterMotorista(@PathVariable Long id) {
        Motorista motorista = motoristaService.buscarPorId(id);
        return motorista != null ? ResponseEntity.ok(motorista) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Motorista> criarMotorista(@RequestBody Motorista motorista) {
        Motorista motoristaSalvo = motoristaService.salvar(motorista);
        return ResponseEntity.status(HttpStatus.CREATED).body(motoristaSalvo);
    }
}