package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.Veiculo;
import com.cadastroMot.CadastroMotorista.repository.VeiculoRepository;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.service.TransportadoraService;
import com.cadastroMot.CadastroMotorista.service.UsuarioService;
import com.cadastroMot.CadastroMotorista.service.VeiculoService;
import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import java.security.Principal;

import java.util.ArrayList;
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

    @Autowired
    private TransportadoraService transportadoraService;

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
        Object tipoUsuario = session.getAttribute("tipoUsuario");

        if ("ADMIN".equals(String.valueOf(tipoUsuario))) {
            model.addAttribute("veiculo", new Veiculo());
            model.addAttribute("isMotorista", false);
            model.addAttribute("motoristas", motoristaService.listarTodos());
            model.addAttribute("edicao", false);
            return "veiculos/formulario-veiculo";
        }

        if (usuarioLogado == null ||
            (usuarioLogado.getTipo() != TipoUsuario.MOTORISTA &&
             usuarioLogado.getTipo() != TipoUsuario.TRANSPORTADORA)) {
            return "redirect:/login";
        }

        model.addAttribute("veiculo", new Veiculo());

        if (usuarioLogado.getTipo() == TipoUsuario.MOTORISTA) {
            model.addAttribute("isMotorista", true);
        } else if (usuarioLogado.getTipo() == TipoUsuario.TRANSPORTADORA) {
            if (usuarioLogado.getTransportadora() == null) {
                return "redirect:/erro-transportadora-nao-associada";
            }
            model.addAttribute("isMotorista", false);
            model.addAttribute("motoristas", motoristaService.listarPorTransportadora(usuarioLogado.getTransportadora()));
        }
        model.addAttribute("edicao", false);
        return "veiculos/formulario-veiculo";
    }

    @PostMapping
    public String salvarVeiculo(
            @ModelAttribute Veiculo veiculo,
            @RequestParam(required = false) String[] tipos,
            @RequestParam(required = false) String[] freteFechado,
            @RequestParam(required = false) String[] freteAberto,
            @RequestParam(required = false) String[] freteEspecial,
            @RequestParam(required = false) Long motorista,
            HttpSession session
    ) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        Object tipoUsuario = session.getAttribute("tipoUsuario");

        // Permite ADMIN fixo (sem usuarioLogado)
        if ("ADMIN".equals(String.valueOf(tipoUsuario))) {
            veiculo.setTipos(tipos != null ? Arrays.asList(tipos) : List.of());
            veiculo.setFretesFechados(freteFechado != null ? Arrays.asList(freteFechado) : List.of());
            veiculo.setFretesAbertos(freteAberto != null ? Arrays.asList(freteAberto) : List.of());
            veiculo.setFretesEspeciais(freteEspecial != null ? Arrays.asList(freteEspecial) : List.of());
            if (motorista != null) {
                Motorista motoristaObj = motoristaService.buscarPorId(motorista);
                veiculo.setMotorista(motoristaObj);
            }
            veiculoService.salvar(veiculo);
            return "redirect:/dashboard/";
        }

        if (usuarioLogado == null ||
            (usuarioLogado.getTipo() != TipoUsuario.MOTORISTA &&
             usuarioLogado.getTipo() != TipoUsuario.TRANSPORTADORA)) {
            return "redirect:/login";
        }

        veiculo.setTipos(tipos != null ? Arrays.asList(tipos) : List.of());
        veiculo.setFretesFechados(freteFechado != null ? Arrays.asList(freteFechado) : List.of());
        veiculo.setFretesAbertos(freteAberto != null ? Arrays.asList(freteAberto) : List.of());
        veiculo.setFretesEspeciais(freteEspecial != null ? Arrays.asList(freteEspecial) : List.of());

        if (usuarioLogado.getTipo() == TipoUsuario.MOTORISTA) {
            Motorista motoristaObj = motoristaService.findByUsuario(usuarioLogado);
            veiculo.setMotorista(motoristaObj);
            veiculoService.salvar(veiculo);
            return "redirect:/motorista/dashboard";
        } else if (usuarioLogado.getTipo() == TipoUsuario.TRANSPORTADORA) {
            veiculo.setTransportadora(usuarioLogado.getTransportadora());
            if (motorista != null) {
                Motorista motoristaObj = motoristaService.buscarPorId(motorista);
                veiculo.setMotorista(motoristaObj);
            }
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

    @GetMapping("/editar/{id}")
    public String editarVeiculoForm(@PathVariable Long id, Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        Object tipoUsuario = session.getAttribute("tipoUsuario");

        if ("ADMIN".equals(String.valueOf(tipoUsuario))) {
            Veiculo veiculo = veiculoService.buscarPorId(id);
            if (veiculo == null) {
                return "redirect:/veiculos";
            }
            model.addAttribute("veiculo", veiculo);
            model.addAttribute("isMotorista", false);
            model.addAttribute("motoristas", motoristaService.listarTodos());
            model.addAttribute("edicao", true);
            return "veiculos/formulario-veiculo";
        }

        if (usuarioLogado == null ||
            (usuarioLogado.getTipo() != TipoUsuario.MOTORISTA &&
             usuarioLogado.getTipo() != TipoUsuario.TRANSPORTADORA)) {
            return "redirect:/login";
        }

        Veiculo veiculo = veiculoService.buscarPorId(id);
        if (veiculo == null) {
            return "redirect:/veiculos";
        }

        model.addAttribute("veiculo", veiculo);

        if (usuarioLogado.getTipo() == TipoUsuario.MOTORISTA) {
            model.addAttribute("isMotorista", true);
        } else if (usuarioLogado.getTipo() == TipoUsuario.TRANSPORTADORA) {
            model.addAttribute("isMotorista", false);
            model.addAttribute("motoristas", motoristaService.listarPorTransportadora(usuarioLogado.getTransportadora()));
        }
        model.addAttribute("edicao", true);
        return "veiculos/formulario-veiculo";
    }

    @PostMapping("/editar/{id}")
    public String atualizarVeiculo(
            @PathVariable Long id,
            @ModelAttribute Veiculo veiculo,
            @RequestParam(required = false) String[] tipos,
            @RequestParam(required = false) String[] freteFechado,
            @RequestParam(required = false) String[] freteAberto,
            @RequestParam(required = false) String[] freteEspecial,
            @RequestParam(required = false) Long motorista,
            HttpSession session
    ) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        Object tipoUsuario = session.getAttribute("tipoUsuario");

        if ("ADMIN".equals(String.valueOf(tipoUsuario))) {
            Veiculo veiculoExistente = veiculoService.buscarPorId(id);
            if (veiculoExistente == null) {
                return "redirect:/veiculos";
            }
            veiculoExistente.setPlaca(veiculo.getPlaca());
            veiculoExistente.setModelo(veiculo.getModelo());
            veiculoExistente.setMarca(veiculo.getMarca());
            veiculoExistente.setCapacidadeCarga(veiculo.getCapacidadeCarga());
            veiculoExistente.setRenavam(veiculo.getRenavam());
            veiculoExistente.setChassi(veiculo.getChassi());
            veiculoExistente.setTipos(tipos != null ? new ArrayList<>(Arrays.asList(tipos)) : new ArrayList<>());
            veiculoExistente.setFretesFechados(freteFechado != null ? new ArrayList<>(Arrays.asList(freteFechado)) : new ArrayList<>());
            veiculoExistente.setFretesAbertos(freteAberto != null ? new ArrayList<>(Arrays.asList(freteAberto)) : new ArrayList<>());
            veiculoExistente.setFretesEspeciais(freteEspecial != null ? new ArrayList<>(Arrays.asList(freteEspecial)) : new ArrayList<>());
            if (motorista != null) {
                Motorista motoristaObj = motoristaService.buscarPorId(motorista);
                veiculoExistente.setMotorista(motoristaObj);
            } else {
                veiculoExistente.setMotorista(null);
            }
            veiculoService.salvar(veiculoExistente);
            return "redirect:/dashboard/";
        }

        if (usuarioLogado == null ||
            (usuarioLogado.getTipo() != TipoUsuario.MOTORISTA &&
             usuarioLogado.getTipo() != TipoUsuario.TRANSPORTADORA)) {
            return "redirect:/login";
        }

        Veiculo veiculoExistente = veiculoService.buscarPorId(id);
        if (veiculoExistente == null) {
            return "redirect:/veiculos";
        }

        veiculoExistente.setPlaca(veiculo.getPlaca());
        veiculoExistente.setModelo(veiculo.getModelo());
        veiculoExistente.setMarca(veiculo.getMarca());
        veiculoExistente.setCapacidadeCarga(veiculo.getCapacidadeCarga());
        veiculoExistente.setRenavam(veiculo.getRenavam());
        veiculoExistente.setChassi(veiculo.getChassi());
        veiculoExistente.setTipos(tipos != null ? new ArrayList<>(Arrays.asList(tipos)) : new ArrayList<>());
        veiculoExistente.setFretesFechados(freteFechado != null ? new ArrayList<>(Arrays.asList(freteFechado)) : new ArrayList<>());
        veiculoExistente.setFretesAbertos(freteAberto != null ? new ArrayList<>(Arrays.asList(freteAberto)) : new ArrayList<>());
        veiculoExistente.setFretesEspeciais(freteEspecial != null ? new ArrayList<>(Arrays.asList(freteEspecial)) : new ArrayList<>());

        if (usuarioLogado.getTipo() == TipoUsuario.MOTORISTA) {
            Motorista motoristaObj = motoristaService.findByUsuario(usuarioLogado);
            veiculoExistente.setMotorista(motoristaObj);
        } else if (usuarioLogado.getTipo() == TipoUsuario.TRANSPORTADORA) {
            veiculoExistente.setTransportadora(usuarioLogado.getTransportadora());
            if (motorista != null) {
                Motorista motoristaObj = motoristaService.buscarPorId(motorista);
                veiculoExistente.setMotorista(motoristaObj);
            } else {
                veiculoExistente.setMotorista(null);
            }
        }

        veiculoService.salvar(veiculoExistente);

        if (usuarioLogado.getTipo() == TipoUsuario.MOTORISTA) {
            return "redirect:/motorista/dashboard";
        } else if (usuarioLogado.getTipo() == TipoUsuario.TRANSPORTADORA) {
            return "redirect:/transportadora/dashboard";
        } else {
            return "redirect:/login";
        }
    }
}