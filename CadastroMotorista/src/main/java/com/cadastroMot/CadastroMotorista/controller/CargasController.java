package com.cadastroMot.CadastroMotorista.controller;


import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cargas")
public class CargasController {

    @Autowired
    private CargaService cargaService;


    @GetMapping("/listar")
    public String listarCargas(@RequestParam(required = false) String origem,
                               @RequestParam(required = false) String destino,
                               @RequestParam(required = false) String produto,
                               @RequestParam(required = false) String especie,
                               Model model) {
        List<Carga> cargas;
        if (origem != null || destino != null || produto != null || especie != null) {
            cargas = cargaService.buscarPorFiltro(origem, destino, produto, especie);
        } else {
            cargas = cargaService.listarTodos();
        }
        model.addAttribute("cargas", cargas);
        model.addAttribute("origem", origem);
        model.addAttribute("destino", destino);
        model.addAttribute("produto", produto);
        model.addAttribute("especie", especie);
        return "/cargas/listar";
    }



    @GetMapping("/{id:\\d+}")
    public String detalharCarga(@PathVariable Long id, Model model) {
        model.addAttribute("carga", cargaService.buscarPorId(id));
        return "/cargas/detalhar";
    }


    @GetMapping("/novo")
    public String formularioNovaCarga(Model model) {
        model.addAttribute("carga", new Carga());
        return "/cargas/novo";
    }


    @PostMapping("/salvar")
    public String salvarCarga(@ModelAttribute Carga carga) {
        if (carga.getId() != null) {
            cargaService.salvar(carga);
        } else {
            cargaService.salvar(carga);
        }
        return "redirect:/cargas/listar";
    }



    @GetMapping("/editar/{id:\\d+}")
    public String formularioEditarCarga(@PathVariable Long id, Model model) {
        model.addAttribute("carga", cargaService.buscarPorId(id));
        return "/cargas/editar";
    }



    @GetMapping("/deletar/{id:\\d+}")
    public String deletarCarga(@PathVariable Long id) {
        cargaService.deletar(id);
        return "redirect:/cargas/listar";
    }
}
