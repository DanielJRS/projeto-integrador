package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.Veiculo;
import com.cadastroMot.CadastroMotorista.repository.VeiculoRepository;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.service.UsuarioService;
import com.cadastroMot.CadastroMotorista.service.VeiculoService;
import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import java.security.Principal;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/veiculos")
public class VeiculosController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private MotoristaService motoristaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public List<Veiculo> listar() {
        return veiculoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Veiculo> buscar(@PathVariable Long id) {
        return veiculoRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Veiculo atualizar(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        veiculo.setId(id);
        return veiculoRepository.save(veiculo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        veiculoRepository.deleteById(id);
    }

    @GetMapping("/novo")
    public String novoVeiculoForm(Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    
        if (usuarioLogado == null || 
            (usuarioLogado.getTipo() != TipoUsuario.MOTORISTA && usuarioLogado.getTipo() != TipoUsuario.TRANSPORTADORA)) {
            return "redirect:/login";
        }
    
        model.addAttribute("veiculo", new Veiculo());
    
        if (usuarioLogado.getTipo() == TipoUsuario.MOTORISTA) {
            model.addAttribute("isMotorista", true);
        } else if (usuarioLogado.getTipo() == TipoUsuario.TRANSPORTADORA) {
            model.addAttribute("isMotorista", false);
            model.addAttribute("motoristas", motoristaService.listarTodos());
        }
        return "veiculos/formulario-veiculo";
    }

    @PostMapping
    public String salvarVeiculo(
            @ModelAttribute Veiculo veiculo,
            @RequestParam(required = false) String[] tipos,
            @RequestParam(required = false) String[] freteFechado,
            @RequestParam(required = false) String[] freteAberto,
            @RequestParam(required = false) String[] freteEspecial,
            HttpSession session
    ) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null || 
            (usuarioLogado.getTipo() != TipoUsuario.MOTORISTA && usuarioLogado.getTipo() != TipoUsuario.TRANSPORTADORA)) {
            return "redirect:/login";
        }

        veiculo.setTipos(tipos != null ? Arrays.asList(tipos) : List.of());
        veiculo.setFretesFechados(freteFechado != null ? Arrays.asList(freteFechado) : List.of());
        veiculo.setFretesAbertos(freteAberto != null ? Arrays.asList(freteAberto) : List.of());
        veiculo.setFretesEspeciais(freteEspecial != null ? Arrays.asList(freteEspecial) : List.of());

        if (usuarioLogado.getTipo() == TipoUsuario.MOTORISTA) {
            Motorista motorista = motoristaService.findByUsuario(usuarioLogado);
            veiculo.setMotorista(motorista);
            veiculoService.salvar(veiculo);
            return "redirect:/motorista/dashboard";
        } else if (usuarioLogado.getTipo() == TipoUsuario.TRANSPORTADORA) {
            veiculoService.salvar(veiculo);
            return "redirect:/transportadora/dashboard";
        }

        return "redirect:/login";
    }

    @GetMapping("/show/{id}")
    public String showVeiculo(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculoService.buscarPorId(id);
        if (veiculo == null) {
            return "redirect:/veiculos";
        }
        model.addAttribute("veiculo", veiculo);
        return "veiculos/show";
    }
}