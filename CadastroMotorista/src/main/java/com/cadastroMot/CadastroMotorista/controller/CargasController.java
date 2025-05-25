package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.TipoCarga;
import com.cadastroMot.CadastroMotorista.repository.CargaRepository;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/cargas")
public class CargasController {

    @Autowired
    private CargaService cargaService;



    @GetMapping("/nova")
    public String formularioCarga(Model model) {
        model.addAttribute("carga", new Carga());
        model.addAttribute("cidades", cargaService.listarCidades());
        model.addAttribute("estados", cargaService.listarEstados());
        return "cargas/nova";
    }

    @PostMapping("/entrega")
    public String processarFormularioCarga(
            @ModelAttribute Carga carga,
            @RequestParam(required = false) String tipoCarga,
            @RequestParam(required = false) String possuiLona,
            @RequestParam(required = false) String[] veiculosLeves,
            @RequestParam(required = false) String[] veiculosMedios,
            @RequestParam(required = false) String[] veiculosPesados,
            @RequestParam(required = false) String[] freteFechado,
            @RequestParam(required = false) String[] freteAberto,
            @RequestParam(required = false) String[] freteEspecial,
            @RequestParam(required = false) Double pesoTotal,
            @RequestParam(required = false) Double limiteAltura,
            @RequestParam(required = false) Double volume,
            Model model) {

        carga = cargaService.processarFormularioCarga(
                carga, tipoCarga, possuiLona,
                veiculosLeves, veiculosMedios, veiculosPesados,
                freteFechado, freteAberto, freteEspecial,
                pesoTotal, limiteAltura, volume);

        cargaService.salvar(carga);

        return "redirect:/cargas/listar";
    }

    @GetMapping("/listar")
    public String listarCargas(
            @RequestParam(required = false) String origemCidade,
            @RequestParam(required = false) String origemEstado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataColeta,
            @RequestParam(required = false) String destinoCidade,
            @RequestParam(required = false) String destinoEstado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEntrega,
            @RequestParam(required = false) String produto,
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String veiculo,
            @RequestParam(required = false) Double preco, // CORRETO
            @RequestParam(required = false) TipoCarga tipoCarga,
            @RequestParam(required = false) Boolean possuiLona,
            @RequestParam(required = false) Double pesoTotal,
            @RequestParam(required = false) Double limiteAltura,
            @RequestParam(required = false) Double volume,

            Model model) {

        List<Carga> cargasFiltradas = cargaService.buscarPorFiltro(
                origemCidade, destinoCidade, produto, especie);

        model.addAttribute("cargas", cargasFiltradas);

        return "cargas/listar";
    }



    @GetMapping("/editar/{id}")
    public String editarCarga(@PathVariable Long id, Model model) {
        Carga carga = cargaService.buscarPorId(id);

        model.addAttribute("carga", carga);
        List<String> estados = cargaService.listarEstados();
        model.addAttribute("estados", estados);

        List<String> cidades = cargaService.listarCidades(); // Pode ser filtrado por estado, se quiser.
        model.addAttribute("cidades", cidades);
        return "cargas/editar";
    }

    @PostMapping("/editar/{id}")
    public String salvarEdicao(@PathVariable Long id, @ModelAttribute("carga") Carga cargaAtualizada) {
        cargaAtualizada.setId(id);
        cargaService.atualizar(cargaAtualizada);
        return "redirect:/cargas";
    }

    @GetMapping("/deletar/{id}")
    public String deletarCarga(@PathVariable Long id) {
        cargaService.deletar(id);
        return "redirect:/cargas";
    }
}

