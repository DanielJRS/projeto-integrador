package com.cadastroMot.CadastroMotorista.controller;


import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cargas")
public class CargasController {

    @Autowired
    private CargaService cargaService;


    @GetMapping("/listar")
    public String listarCargas(Model model) {
        model.addAttribute("cargas", cargaService.listarTodos());
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
