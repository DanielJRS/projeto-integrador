package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.Empresa;
import com.cadastroMot.CadastroMotorista.service.EmpresaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
    @RequestMapping("/empresa")

    public class EmpresaController {

        private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/novo")
    public String novaEmpresa(Model model){
        model.addAttribute("empresa", new Empresa());
        return "/empresas/formulario-empresa";
    }
}
