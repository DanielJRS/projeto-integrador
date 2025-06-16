package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.*;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import com.cadastroMot.CadastroMotorista.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/cargas")
public class CargasController {

    @Autowired
    private CargaService cargaService;

    @GetMapping("/novo")
    public String formularioCarga(Model model, HttpSession session) {
        Carga carga = new Carga();
        carga.setTipoEstadoCarga(TipoEstadoCarga.DISPONIVEL);

        model.addAttribute("carga", carga);
        model.addAttribute("cidades", cargaService.listarCidades());
        model.addAttribute("estados", cargaService.listarEstados());

        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if ("ADMIN".equals(String.valueOf(tipoUsuario))) {
            return "cargas/novo";
        }

        if (usuarioLogado == null || tipoUsuario != TipoUsuario.EMPRESA) {
            return "redirect:/cargas/listar";
        }

        carga.setEmpresaCarga(usuarioLogado.getEmpresa());
        return "cargas/novo";
    }

    @PostMapping("/entrega")
    public String processarFormularioCarga(@ModelAttribute Carga carga,
                                           @RequestParam(required = false) String tipoCarga,
                                           @RequestParam(required = false) String tipoEstadoCarga,
                                           @RequestParam(required = false) String possuiLona,
                                           @RequestParam(required = false) String[] veiculo,
                                           @RequestParam(required = false) String[] freteFechado,
                                           @RequestParam(required = false) String[] freteAberto,
                                           @RequestParam(required = false) String[] freteEspecial,
                                           @RequestParam(required = false) Double pesoTotal,
                                           @RequestParam(required = false) Double limiteAltura,
                                           @RequestParam(required = false) Double volume,
                                           @RequestParam(required = false) Double preco,
                                           @RequestParam(required = false) Double precoFrete,
                                           @RequestParam(required = false) String produto,
                                           @RequestParam(required = false) String especie,
                                           @RequestParam(required = false) String veiculoSimples) {

        // Se estiver editando, busca o objeto original para preservar campos não preenchidos no form
        Carga cargaFinal;
        if (carga.getId() != null) {
            // Assumindo que o ID vem do formulário hidden
            Carga existente = cargaService.buscarPorId(carga.getId());
            if (existente != null) {
                cargaFinal = existente;

                // Atualiza apenas os campos que vêm do formulário
                cargaFinal.setTipoCarga(tipoCarga != null && !tipoCarga.isEmpty() ? TipoCarga.valueOf(tipoCarga) : null);
                cargaFinal.setPossuiLona("on".equalsIgnoreCase(possuiLona));
                cargaFinal.setPesoTotal(pesoTotal);
                cargaFinal.setLimiteAltura(limiteAltura);
                cargaFinal.setVolume(volume);
                cargaFinal.setPreco(preco);
                cargaFinal.setPrecoFrete(precoFrete);
                cargaFinal.setProduto(produto);
                cargaFinal.setEspecie(especie);


                List<String> selecionados = veiculo != null ? Arrays.asList(veiculo) : List.of();
                cargaFinal.setVeiculosLeves(selecionados.stream().filter(List.of("Todos", "3/4", "HR", "Toco", "VLC")::contains).toList());
                cargaFinal.setVeiculosMedios(selecionados.stream().filter(List.of("Bitruck", "Truck")::contains).toList());
                cargaFinal.setVeiculosPesados(selecionados.stream().filter(List.of("Bi-trem", "Carreta", "Rodotrem", "Carreta LS")::contains).toList());

                cargaFinal.setFretesFechados(freteFechado != null ? List.of(freteFechado) : List.of());
                cargaFinal.setFretesAbertos(freteAberto != null ? List.of(freteAberto) : List.of());
                cargaFinal.setFretesEspeciais(freteEspecial != null ? List.of(freteEspecial) : List.of());
            } else {
                // fallback: trata como novo se não encontrar pelo ID
                cargaFinal = carga;
            }
        } else {
            // novo registro
            carga.setTipoCarga(tipoCarga != null && !tipoCarga.isEmpty() ? TipoCarga.valueOf(tipoCarga) : null);
            carga.setPossuiLona("on".equalsIgnoreCase(possuiLona));
            carga.setPesoTotal(pesoTotal);
            carga.setLimiteAltura(limiteAltura);
            carga.setVolume(volume);
            carga.setPreco(preco);
            carga.setPrecoFrete(precoFrete);
            carga.setProduto(produto);
            carga.setEspecie(especie);


            List<String> selecionados = veiculo != null ? Arrays.asList(veiculo) : List.of();
            carga.setVeiculosLeves(selecionados.stream().filter(List.of("Todos", "3/4", "HR", "Toco", "VLC")::contains).toList());
            carga.setVeiculosMedios(selecionados.stream().filter(List.of("Bitruck", "Truck")::contains).toList());
            carga.setVeiculosPesados(selecionados.stream().filter(List.of("Bi-trem", "Carreta", "Rodotrem", "Carreta LS")::contains).toList());

            carga.setFretesFechados(freteFechado != null ? List.of(freteFechado) : List.of());
            carga.setFretesAbertos(freteAberto != null ? List.of(freteAberto) : List.of());
            carga.setFretesEspeciais(freteEspecial != null ? List.of(freteEspecial) : List.of());

            cargaFinal = carga;
        }

        // Salvar ou atualizar
        cargaService.salvar(cargaFinal);

        return "redirect:/cargas/listar";
    }

    @GetMapping("/listar")
    public String listarCargas(
            @ModelAttribute CargaFiltro filtro,
            @RequestParam(required = false) String origemCidade,
            @RequestParam(required = false) String origemEstado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataColeta,
            @RequestParam(required = false) String destinoCidade,
            @RequestParam(required = false) String destinoEstado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEntrega,
            @RequestParam(required = false) String produto,
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String veiculo,
            @RequestParam(required = false) Double preco,
            @RequestParam(required = false) Double precoFrete,
            @RequestParam(required = false) String veiculoSimples,
            @RequestParam(required = false) TipoCarga tipoCarga,
            @RequestParam(required = false) TipoEstadoCarga tipoEstadoCarga,
            @RequestParam(required = false) Boolean possuiLona,
            @RequestParam(required = false) Double pesoTotal,
            @RequestParam(required = false) Double limiteAltura,
            @RequestParam(required = false) Double volume,
            HttpSession session,
            Model model) {

        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        model.addAttribute("tipoUsuario", tipoUsuario);

        List<Carga> cargasFiltradas = cargaService.buscarComFiltro(filtro);

        model.addAttribute("cargas", cargasFiltradas);
        model.addAttribute("filtro", filtro != null ? filtro : new CargaFiltro());
        model.addAttribute("cidades", cargaService.listarCidades());
        model.addAttribute("estados", cargaService.listarEstados());

        return "cargas/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarCarga(@PathVariable Long id, Model model) {
        Carga carga = cargaService.buscarPorId(id);

        // Inicializar listas se forem nulas
        if (carga.getVeiculosLeves() == null) {
            carga.setVeiculosLeves(new ArrayList<>());
        }
        if (carga.getVeiculosMedios() == null) {
            carga.setVeiculosMedios(new ArrayList<>());
        }
        if (carga.getVeiculosPesados() == null) {
            carga.setVeiculosPesados(new ArrayList<>());
        }
        if (carga.getFretesFechados() == null) {
            carga.setFretesFechados(new ArrayList<>());
        }
        if (carga.getFretesAbertos() == null) {
            carga.setFretesAbertos(new ArrayList<>());
        }
        if (carga.getFretesEspeciais() == null) {
            carga.setFretesEspeciais(new ArrayList<>());
        }

        model.addAttribute("carga", carga);
        model.addAttribute("estados", cargaService.listarEstados());
        model.addAttribute("cidades", cargaService.listarCidades());

        return "cargas/editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarCarga(@PathVariable Long id,
                                 @ModelAttribute Carga carga,
                                 @RequestParam(required = false) String tipoCarga,
                                 @RequestParam(required = false) String tipoEstadoCarga,
                                 @RequestParam(required = false) String possuiLona,
                                 @RequestParam(required = false) String[] veiculosLeves,
                                 @RequestParam(required = false) String[] veiculosMedios,
                                 @RequestParam(required = false) String[] veiculosPesados,
                                 @RequestParam(required = false) String[] fretesFechados,
                                 @RequestParam(required = false) String[] fretesAbertos,
                                 @RequestParam(required = false) String[] fretesEspeciais,
                                 @RequestParam(required = false) Double pesoTotal,
                                 @RequestParam(required = false) Double limiteAltura,
                                 @RequestParam(required = false) Double volume,
                                 @RequestParam(required = false) Double preco,
                                 @RequestParam(required = false) Double precoFrete,
                                 @RequestParam(required = false) String produto,
                                 @RequestParam(required = false) String especie,
                                 @RequestParam(required = false) String veiculoSimples,
                                 RedirectAttributes redirectAttributes) {

        Carga cargaExistente = cargaService.buscarPorId(id);
        if (cargaExistente == null) {
            return "redirect:/cargas/listar";
        }

        // Atualiza campos básicos
        cargaExistente.setOrigemCidade(carga.getOrigemCidade());
        cargaExistente.setOrigemEstado(carga.getOrigemEstado());
        cargaExistente.setDestinoCidade(carga.getDestinoCidade());
        cargaExistente.setDestinoEstado(carga.getDestinoEstado());
        cargaExistente.setDataColeta(carga.getDataColeta());
        cargaExistente.setDataEntrega(carga.getDataEntrega());

        // Atualiza os campos que estavam faltando
        cargaExistente.setProduto(produto != null ? produto : carga.getProduto());
        cargaExistente.setEspecie(especie != null ? especie : carga.getEspecie());
        cargaExistente.setPesoTotal(pesoTotal != null ? pesoTotal : carga.getPesoTotal());
        cargaExistente.setLimiteAltura(limiteAltura != null ? limiteAltura : carga.getLimiteAltura());
        cargaExistente.setVolume(volume != null ? volume : carga.getVolume());
        cargaExistente.setPreco(preco != null ? preco : carga.getPreco());
        cargaExistente.setPrecoFrete(precoFrete);

        if (tipoEstadoCarga != null && !tipoEstadoCarga.isEmpty()) {
            cargaExistente.setTipoEstadoCarga(TipoEstadoCarga.valueOf(tipoEstadoCarga));
        }

        if (tipoCarga != null && !tipoCarga.isEmpty()) {
            cargaExistente.setTipoCarga(TipoCarga.valueOf(tipoCarga));
        }

        cargaExistente.setPossuiLona("on".equalsIgnoreCase(possuiLona));

        // Atualiza coleções garantindo que sejam mutáveis
        cargaExistente.setVeiculosLeves(veiculosLeves != null ? new ArrayList<>(Arrays.asList(veiculosLeves)) : new ArrayList<>());
        cargaExistente.setVeiculosMedios(veiculosMedios != null ? new ArrayList<>(Arrays.asList(veiculosMedios)) : new ArrayList<>());
        cargaExistente.setVeiculosPesados(veiculosPesados != null ? new ArrayList<>(Arrays.asList(veiculosPesados)) : new ArrayList<>());
        cargaExistente.setFretesFechados(fretesFechados != null ? new ArrayList<>(Arrays.asList(fretesFechados)) : new ArrayList<>());
        cargaExistente.setFretesAbertos(fretesAbertos != null ? new ArrayList<>(Arrays.asList(fretesAbertos)) : new ArrayList<>());
        cargaExistente.setFretesEspeciais(fretesEspeciais != null ? new ArrayList<>(Arrays.asList(fretesEspeciais)) : new ArrayList<>());

        cargaService.atualizar(cargaExistente);
        return "redirect:/cargas/listar";
    }

    @GetMapping("/detalhar/{id}")
    public String detalharCarga(@PathVariable Long id, Model model) {
        Carga carga = cargaService.buscarPorId(id);

        if (carga == null) {
            return "redirect:/cargas/listar";
        }

        model.addAttribute("carga", carga);
        return "cargas/detalhar";
    }

    @GetMapping("/deletar/{id}")
    public String deletarCarga(@PathVariable Long id) {
        cargaService.deletar(id);
        return "redirect:/cargas/listar";
    }
}