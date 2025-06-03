package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.domain.Veiculo;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
import com.cadastroMot.CadastroMotorista.service.VeiculoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/motorista")
public class MotoristaController {

    private final MotoristaService motoristaService;
    private final CargaService cargaService;
    private final VeiculoService veiculoService;

    @Autowired
    public MotoristaController(MotoristaService motoristaService, CargaService cargaService, VeiculoService veiculoService){
        this.motoristaService = motoristaService;
        this.cargaService = cargaService;
        this.veiculoService = veiculoService;
    }

    @GetMapping("/novo")
    public String formulario (Model model) {
        model.addAttribute("motorista", new Motorista());
        return "/motoristas/formulario-motorista";
    }

    @PostMapping("/salvar")
    public String salvar(Motorista motorista,
                         @RequestParam("arquivoFoto") MultipartFile arquivoFoto,
                         @RequestParam("email") String email,
                         @RequestParam("senha") String senha,
                         RedirectAttributes redirectAttributes) throws IOException {

        try {
            // Processar a foto se enviada
            if (!arquivoFoto.isEmpty()) {
                motorista.setFoto(arquivoFoto.getBytes());
                motorista.setTipoFoto(arquivoFoto.getContentType());
            }

            // Usar o método do service que cuida da transação
            Motorista motoristaSalvo = motoristaService.salvarComUsuario(motorista, email, senha);

            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Motorista cadastrado com sucesso! Faça login para acessar sua área.");

            return "redirect:/login";

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/motorista/novo";
        }
    }

    @GetMapping("/cargas")
    public String listarCargas (Model model){
        return "redirect:/cargas/listar";
    }

    @GetMapping("/motoristas/foto/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> exibirFoto(@PathVariable Long id) {
        Motorista motorista = motoristaService.buscarPorId(id);
        if (motorista.getFoto() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(motorista.getTipoFoto()));
            return new ResponseEntity<>(motorista.getFoto(), headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/dashboard")
    public String dashboardDeMotorista(HttpSession session, Model model){
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.MOTORISTA) {
            return "redirect:/login";
        }

        Motorista motorista = usuarioLogado.getMotorista();
        model.addAttribute("motorista", motorista);

        List<Carga> cargas = cargaService.buscarCargaPorMotorista(usuarioLogado.getId());
        model.addAttribute("cargas", cargas);

        List<Veiculo> veiculos = veiculoService.buscarPorMotoristaId(motorista.getId());
        model.addAttribute("veiculos", veiculos);

        return "/motoristas/dashboard-motorista";
    }
}
