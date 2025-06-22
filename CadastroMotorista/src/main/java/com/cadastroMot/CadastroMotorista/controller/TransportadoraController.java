package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.domain.Veiculo;
import com.cadastroMot.CadastroMotorista.service.TransportadoraService;
import com.cadastroMot.CadastroMotorista.service.VeiculoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
    public String novaEmpresa (Model model, HttpSession session){
        Object tipoUsuario = session.getAttribute("tipoUsuario");

        model.addAttribute("tipoUsuario", tipoUsuario);
        model.addAttribute("transportadora", new Transportadora());
        model.addAttribute("edicao", false);
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

    @GetMapping("/detalhar/{id}")
    public String detalharTransportadora(@PathVariable Long id, Model model, HttpSession session) {
        Transportadora transportadora = transportadoraService.buscarPorId(id);
        model.addAttribute("transportadora", transportadora);
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        model.addAttribute("tipoUsuario", tipoUsuario);
        String dataFundacaoFormatada = null;
        if (transportadora.getDataFundacao() != null) {
            dataFundacaoFormatada = transportadora.getDataFundacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        model.addAttribute("dataFundacaoFormatada", dataFundacaoFormatada);
        String dataVencimentoLicencaFormatada = null;
        if (transportadora.getDataVencimentoLicenca() != null) {
            dataVencimentoLicencaFormatada = transportadora.getDataVencimentoLicenca().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        model.addAttribute("dataVencimentoLicencaFormatada", dataVencimentoLicencaFormatada);


        return "/transportadoras/detalhar-transportadora";
    }

    @GetMapping("/editar/{id}")
    public String editarTransportadora(@PathVariable Long id, Model model) {
        Transportadora transportadora = transportadoraService.buscarPorId(id);
        model.addAttribute("transportadora", transportadora);
        model.addAttribute("edicao", true);
        return "/transportadoras/formulario-transportadora";
    }

    @PostMapping("/excluir/{id}")
    public String excluirTransportadora(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario.toString())) {
            redirectAttributes.addFlashAttribute("falha", "Apenas administradores podem excluir transportadoras.");
            return "redirect:/dashboard/transportadoras-listartodos";
        }
        try {
            transportadoraService.excluirPorId(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Transportadora excluída com sucesso!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("falha", "Não é possível excluir: existem veículos associados a esta transportadora.");
        }
        return "redirect:/dashboard/transportadoras-listartodos";
    }
}
