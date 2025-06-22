package com.cadastroMot.CadastroMotorista.controller.api;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.CargaFiltro;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import Dto.CargasDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargas")
@Tag(name = "Cargas", description = "API para gerenciamento de cargas")
public class CargaApiController {

    @Autowired
    private CargaService cargaService;

    @Operation(summary = "Detalhar carga", description = "Retorna os detalhes de uma carga pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<CargasDTO> detalharCarga(@PathVariable Long id) {
        Carga c = cargaService.buscarPorId(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        CargasDTO dto = new CargasDTO(
            c.getId(), c.getOrigemCidade(), c.getOrigemEstado(), c.getDataColeta(),
            c.getDestinoCidade(), c.getDestinoEstado(), c.getDataEntrega(),
            c.getProduto(), c.getEspecie(), c.getVeiculo(), c.getPreco(), c.getPrecoFrete(),
            c.getTipoCarga() != null ? c.getTipoCarga().name() : null,
            c.getTipoEstadoCarga() != null ? c.getTipoEstadoCarga().name() : null,
            c.getPossuiLona(), c.getPesoTotal(), c.getLimiteAltura(), c.getVolume(),
            c.getVeiculosLeves(), c.getVeiculosMedios(), c.getVeiculosPesados(),
            c.getFretesFechados(), c.getFretesAbertos(), c.getFretesEspeciais()
        );
        return ResponseEntity.ok(dto);
    }


    @Operation(summary = "Atualizar carga", description = "Atualiza uma carga existente pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<CargasDTO> atualizarCarga(@PathVariable Long id, @RequestBody CargasDTO dto) {
        Carga existente = cargaService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setOrigemCidade(dto.getOrigemCidade());
        existente.setOrigemEstado(dto.getOrigemEstado());
        existente.setDataColeta(dto.getDataColeta());
        existente.setDestinoCidade(dto.getDestinoCidade());
        existente.setDestinoEstado(dto.getDestinoEstado());
        existente.setDataEntrega(dto.getDataEntrega());
        existente.setProduto(dto.getProduto());
        existente.setEspecie(dto.getEspecie());
        existente.setVeiculo(dto.getVeiculo());
        existente.setPreco(dto.getPreco());
        existente.setPrecoFrete(dto.getPrecoFrete());
        if (dto.getTipoCarga() != null)
            existente.setTipoCarga(Enum.valueOf(com.cadastroMot.CadastroMotorista.domain.TipoCarga.class, dto.getTipoCarga()));
        if (dto.getTipoEstadoCarga() != null)
            existente.setTipoEstadoCarga(Enum.valueOf(com.cadastroMot.CadastroMotorista.domain.TipoEstadoCarga.class, dto.getTipoEstadoCarga()));
        existente.setPossuiLona(dto.getPossuiLona());
        existente.setPesoTotal(dto.getPesoTotal());
        existente.setLimiteAltura(dto.getLimiteAltura());
        existente.setVolume(dto.getVolume());
        existente.setVeiculosLeves(dto.getVeiculosLeves());
        existente.setVeiculosMedios(dto.getVeiculosMedios());
        existente.setVeiculosPesados(dto.getVeiculosPesados());
        existente.setFretesFechados(dto.getFretesFechados());
        existente.setFretesAbertos(dto.getFretesAbertos());
        existente.setFretesEspeciais(dto.getFretesEspeciais());
        Carga atualizada = cargaService.salvar(existente);
        CargasDTO resp = new CargasDTO(
            atualizada.getId(), atualizada.getOrigemCidade(), atualizada.getOrigemEstado(), atualizada.getDataColeta(),
            atualizada.getDestinoCidade(), atualizada.getDestinoEstado(), atualizada.getDataEntrega(),
            atualizada.getProduto(), atualizada.getEspecie(), atualizada.getVeiculo(), atualizada.getPreco(), atualizada.getPrecoFrete(),
            atualizada.getTipoCarga() != null ? atualizada.getTipoCarga().name() : null,
            atualizada.getTipoEstadoCarga() != null ? atualizada.getTipoEstadoCarga().name() : null,
            atualizada.getPossuiLona(), atualizada.getPesoTotal(), atualizada.getLimiteAltura(), atualizada.getVolume(),
            atualizada.getVeiculosLeves(), atualizada.getVeiculosMedios(), atualizada.getVeiculosPesados(),
            atualizada.getFretesFechados(), atualizada.getFretesAbertos(), atualizada.getFretesEspeciais()
        );
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Deletar carga", description = "Remove uma carga pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCarga(@PathVariable Long id) {
        Carga existente = cargaService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        cargaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
