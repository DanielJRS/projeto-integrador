package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.*;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import com.cadastroMot.CadastroMotorista.service.EmpresaService;
import com.cadastroMot.CadastroMotorista.service.FreteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/empresa")

public class EmpresaController {

    @Autowired
    private final CargaService cargaService;

    private final EmpresaService empresaService;

    private final FreteService freteService;

    public EmpresaController(CargaService cargaService, EmpresaService empresaService, FreteService freteService) {
        this.cargaService = cargaService;
        this.empresaService = empresaService;
        this.freteService = freteService;
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
    public String dashboardDeEmpresa (HttpSession session,
                                      Model model){
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.EMPRESA){
            return "redirect/login";
        }

        Empresa empresa = usuarioLogado.getEmpresa();
        model.addAttribute("empresa", empresa);

        List <Frete> fretes = freteService.buscarFretesPorEmpresa(empresa);
        model.addAttribute("fretes", fretes);

        List <Carga> cargas = cargaService.buscarCargasPorEmpresa(empresa);
        model.addAttribute("cargas", cargas);

        Long numeroFretesAtivosEmpresa = freteService.buscarFretesPorEmpresa(empresa, TipoEstadoFrete.ATIVO);
        model.addAttribute("totalFretes", numeroFretesAtivosEmpresa);

        Long numeroFretesFinalizadosEmpresa = freteService.buscarFretesPorEmpresa(empresa, TipoEstadoFrete.FINALIZADO);
        model.addAttribute("fretesFinalizados", numeroFretesFinalizadosEmpresa);

        Long numeroCargasEmpresa = cargaService.contarCargasPorEmpresa(empresa.getId());
        model.addAttribute("totalCargas", numeroCargasEmpresa);

        return "/empresas/dashboard-empresa";
    }

    @GetMapping("/detalhar/{id}")
    public String detalhar(@PathVariable Long id, Model model, HttpSession session) {
        Empresa empresa = empresaService.buscarPorId(id);
        model.addAttribute("empresa", empresa);
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        model.addAttribute("tipoUsuario", tipoUsuario);
        return "/empresas/detalhar-empresa";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.buscarPorId(id);
        model.addAttribute("empresa", empresa);
        model.addAttribute("edicao", true);
        return "/empresas/formulario-empresa";
    }

    @PostMapping("/excluir/{id}")
    public String excluirEmpresa(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario.toString())) {
            redirectAttributes.addFlashAttribute("falha", "Apenas administradores podem excluir empresas.");
            return "redirect:/dashboard/empresas-listartodos";
        }
        try {
            empresaService.excluirPorId(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Empresa excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("falha", "Não foi possível excluir a empresa. Verifique se há vínculos ativos.");
        }
        return "redirect:/dashboard/empresas-listartodos";
    }
}
