package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.domain.Veiculo;
import com.cadastroMot.CadastroMotorista.service.TransportadoraService;
import com.cadastroMot.CadastroMotorista.service.VeiculoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping ("/transportadora")
public class TransportadoraController {
    private final TransportadoraService transportadoraService;
    private final VeiculoService veiculoService;

    public TransportadoraController (TransportadoraService transportadoraService, VeiculoService veiculoService) {
        this.transportadoraService = transportadoraService;
        this.veiculoService = veiculoService;
    }

    @GetMapping ("/novo")
    public String novaEmpresa (Model model){
        model.addAttribute("transportadora", new Transportadora());
        return "/transportadoras/formulario-transportadora";
    }

    @PostMapping("/salvar")
    public String salvar (Transportadora transportadora,
                          @RequestParam("email") String email,
                          @RequestParam("senha") String senha,
                          RedirectAttributes redirectAttributes) throws IOException {

        try {
            Transportadora transportadoraSalva = transportadoraService.salvarComUsuario(transportadora, email, senha);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Transportadora cadastrada com sucesso! Faça login para acessar sua área.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("falha", e.getMessage());
            return "redirect:/empresa/novo";
        }
    }

    @GetMapping("/dashboard")
    public String dashboardT(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.TRANSPORTADORA)
            return "redirect:/login";

        Transportadora transportadora = usuarioLogado.getTransportadora();
        model.addAttribute("transportadora", transportadora);

        List<Veiculo> veiculos = transportadora != null
            ? veiculoService.buscarPorTransportadoraId(transportadora.getId())
            : List.of();
        model.addAttribute("veiculos", veiculos);

        return "/transportadoras/dashboard-transportadora";
    }


}
