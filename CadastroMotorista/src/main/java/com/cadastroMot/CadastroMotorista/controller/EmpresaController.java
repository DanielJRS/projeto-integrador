package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.Empresa;
import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.service.EmpresaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/empresa")

public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {

        this.empresaService = empresaService;
    }

    @GetMapping("/novo")
    public String novaEmpresa(Model model, HttpSession session) {
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        model.addAttribute("tipoUsuario", tipoUsuario);
        model.addAttribute("empresa", new Empresa());
        return "/empresas/formulario-empresa";
    }

    @PostMapping("/salvar")
    public String salvar (Empresa empresa,
                          @RequestParam("email") String email,
                          @RequestParam("senha") String senha,
                            RedirectAttributes redirectAttributes) throws IOException {

        try{
            Empresa empresaSalva = empresaService.salvarComUsuario(empresa, email, senha);

            redirectAttributes.addFlashAttribute("mensagemSucesso", "Empresa cadastrada com sucesso! Faça login para acessar sua área.");
            return "redirect:/login";

        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/empresa/novo";
        }
    }

    @GetMapping("/dashboard")
    public String dashboardDeEmpresa (HttpSession session, Model model){
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.EMPRESA){
            return "redirect/login";
        }

        Empresa empresa = usuarioLogado.getEmpresa();
        model.addAttribute("empresa", empresa);

        return "/empresas/dashboard-empresa";
    }

    @GetMapping("/detalhar/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.buscarPorId(id);
        model.addAttribute("empresa", empresa);
        return "/empresas/detalhar-empresa";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.buscarPorId(id);
        model.addAttribute("empresa", empresa);
        model.addAttribute("edicao", true);
        return "/empresas/formulario-empresa";
    }
}
