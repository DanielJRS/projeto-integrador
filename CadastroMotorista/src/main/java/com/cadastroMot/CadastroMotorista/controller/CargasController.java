package com.cadastroMot.CadastroMotorista.controller;

import  java.util.Collections;
import com.cadastroMot.CadastroMotorista.domain.*;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import com.cadastroMot.CadastroMotorista.service.UsuarioService;
import com.cadastroMot.CadastroMotorista.service.VeiculoService;
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
    private VeiculoService veiculoService;

    @Autowired
    private CargaService cargaService;

    @GetMapping("/novo")
    public String formularioCarga(Model model, HttpSession session) {
        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");


        if (tipoUsuario != TipoUsuario.EMPRESA) {
            return "redirect:/cargas/listar";
        }

        Carga carga = new Carga();
        carga.setTipoEstadoCarga(TipoEstadoCarga.DISPONIVEL);
        carga.setEmpresaCarga(usuarioLogado.getEmpresa());

        model.addAttribute("carga", carga);
        model.addAttribute("cidades", cargaService.listarCidades());
        model.addAttribute("estados", cargaService.listarEstados());
        model.addAttribute("tiposCarga", TipoCarga.values());

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
                                           HttpSession session,
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
                                           @RequestParam(required = false) String especie) {


        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        if (tipoUsuario != TipoUsuario.EMPRESA) {
            return "redirect:/cargas/listar";
        }

        Carga cargaFinal;

        if (carga.getId() != null) {

            Carga existente = cargaService.buscarPorId(carga.getId());
            if (existente == null) {
                return "redirect:/cargas/listar";
            }
            cargaFinal = existente;
            atualizarCamposCarga(cargaFinal, carga, tipoCarga, possuiLona, veiculo,
                    freteFechado, freteAberto, freteEspecial, pesoTotal,
                    limiteAltura, volume, preco, precoFrete, produto, especie);
        } else {

            cargaFinal = carga;
            atualizarCamposCarga(cargaFinal, carga, tipoCarga, possuiLona, veiculo,
                    freteFechado, freteAberto, freteEspecial, pesoTotal,
                    limiteAltura, volume, preco, precoFrete, produto, especie);


            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
            if (cargaFinal.getEmpresaCarga() == null && usuarioLogado != null && usuarioLogado.getEmpresa() != null) {
                cargaFinal.setEmpresaCarga(usuarioLogado.getEmpresa());
            }
        }

        cargaService.salvar(cargaFinal);
        return "redirect:/cargas/listar";
    }

    private void atualizarCamposCarga(Carga carga, Carga formData, String tipoCarga, String possuiLona,
                                      String[] veiculo, String[] freteFechado, String[] freteAberto,
                                      String[] freteEspecial, Double pesoTotal, Double limiteAltura,
                                      Double volume, Double preco, Double precoFrete, String produto, String especie) {


        carga.setOrigemCidade(formData.getOrigemCidade());
        carga.setOrigemEstado(formData.getOrigemEstado());
        carga.setDestinoCidade(formData.getDestinoCidade());
        carga.setDestinoEstado(formData.getDestinoEstado());
        carga.setDataColeta(formData.getDataColeta());
        carga.setDataEntrega(formData.getDataEntrega());


        carga.setTipoCarga(tipoCarga != null && !tipoCarga.isEmpty() ? TipoCarga.valueOf(tipoCarga) : null);
        carga.setPesoTotal(pesoTotal);
        carga.setLimiteAltura(limiteAltura);
        carga.setVolume(volume);
        carga.setPreco(preco);
        carga.setPrecoFrete(precoFrete);
        carga.setProduto(produto);
        carga.setEspecie(especie);

        // Processar veículos
        List<String> selecionados = veiculo != null ? Arrays.asList(veiculo) : new ArrayList<>();
        carga.setVeiculosLeves(new ArrayList<>(selecionados.stream()
                .filter(v -> List.of("Todos", "3/4", "HR", "Toco", "VLC").contains(v))
                .toList()));
        carga.setVeiculosMedios(new ArrayList<>(selecionados.stream()
                .filter(v -> List.of("Bitruck", "Truck").contains(v))
                .toList()));
        carga.setVeiculosPesados(new ArrayList<>(selecionados.stream()
                .filter(v -> List.of("Bi-trem", "Carreta", "Rodotrem", "Carreta LS").contains(v))
                .toList()));

        // Processar fretes (garantindo listas mutáveis)
        carga.setFretesFechados(freteFechado != null ? new ArrayList<>(Arrays.asList(freteFechado)) : new ArrayList<>());
        carga.setFretesAbertos(freteAberto != null ? new ArrayList<>(Arrays.asList(freteAberto)) : new ArrayList<>());
        carga.setFretesEspeciais(freteEspecial != null ? new ArrayList<>(Arrays.asList(freteEspecial)) : new ArrayList<>());
    }

    @GetMapping("/listar")
    public String listarCargas(
            @ModelAttribute CargaFiltro filtro,
            HttpSession session,
            Model model) {

        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");


        if (usuarioLogado != null && tipoUsuario == TipoUsuario.EMPRESA) {
            Empresa empresa = usuarioLogado.getEmpresa();
            if (empresa != null) {
                filtro.setEmpresa(empresa);
            }
        }

        List<Carga> cargasFiltradas = cargaService.buscarComFiltro(filtro);

        model.addAttribute("cargas", cargasFiltradas);
        model.addAttribute("filtro", filtro != null ? filtro : new CargaFiltro());
        model.addAttribute("cidades", cargaService.listarCidades());
        model.addAttribute("estados", cargaService.listarEstados());
        model.addAttribute("tipoUsuario", tipoUsuario);
        model.addAttribute("tiposCarga", TipoCarga.values());
        model.addAttribute("statusCargas", TipoEstadoCarga.values());

        return "cargas/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarCarga(@PathVariable Long id, Model model, HttpSession session) {
        // Verificar permissão
        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        if (tipoUsuario != TipoUsuario.EMPRESA) {
            return "redirect:/cargas/listar";
        }

        Carga carga = cargaService.buscarPorId(id);
        if (carga == null) {
            return "redirect:/cargas/listar";
        }


        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null && usuarioLogado.getEmpresa() != null &&
                !usuarioLogado.getEmpresa().equals(carga.getEmpresaCarga())) {
            return "redirect:/cargas/listar";
        }


        inicializarListasCarga(carga);

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
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {


        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        if (tipoUsuario != TipoUsuario.EMPRESA) {
            return "redirect:/cargas/listar";
        }

        Carga cargaExistente = cargaService.buscarPorId(id);
        if (cargaExistente == null) {
            return "redirect:/cargas/listar";
        }


        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null && usuarioLogado.getEmpresa() != null &&
                !usuarioLogado.getEmpresa().equals(cargaExistente.getEmpresaCarga())) {
            return "redirect:/cargas/listar";
        }


        atualizarCargaExistente(cargaExistente, carga, tipoCarga, tipoEstadoCarga, possuiLona,
                veiculosLeves, veiculosMedios, veiculosPesados, fretesFechados,
                fretesAbertos, fretesEspeciais, pesoTotal, limiteAltura, volume,
                preco, precoFrete, produto, especie);

        cargaService.atualizar(cargaExistente);
        return "redirect:/cargas/listar";
    }

    private void atualizarCargaExistente(Carga cargaExistente, Carga formData, String tipoCarga,
                                         String tipoEstadoCarga, String possuiLona, String[] veiculosLeves,
                                         String[] veiculosMedios, String[] veiculosPesados, String[] fretesFechados,
                                         String[] fretesAbertos, String[] fretesEspeciais, Double pesoTotal,
                                         Double limiteAltura, Double volume, Double preco, Double precoFrete,
                                         String produto, String especie) {


        cargaExistente.setOrigemCidade(formData.getOrigemCidade());
        cargaExistente.setOrigemEstado(formData.getOrigemEstado());
        cargaExistente.setDestinoCidade(formData.getDestinoCidade());
        cargaExistente.setDestinoEstado(formData.getDestinoEstado());
        cargaExistente.setDataColeta(formData.getDataColeta());
        cargaExistente.setDataEntrega(formData.getDataEntrega());


        cargaExistente.setProduto(produto);
        cargaExistente.setEspecie(especie);
        cargaExistente.setPesoTotal(pesoTotal);
        cargaExistente.setLimiteAltura(limiteAltura);
        cargaExistente.setVolume(volume);
        cargaExistente.setPreco(preco);
        cargaExistente.setPrecoFrete(precoFrete);

        if (tipoEstadoCarga != null && !tipoEstadoCarga.isEmpty()) {
            cargaExistente.setTipoEstadoCarga(TipoEstadoCarga.valueOf(tipoEstadoCarga));
        }

        if (tipoCarga != null && !tipoCarga.isEmpty()) {
            cargaExistente.setTipoCarga(TipoCarga.valueOf(tipoCarga));
        }

        cargaExistente.setPossuiLona("on".equalsIgnoreCase(possuiLona));


        cargaExistente.setVeiculosLeves(veiculosLeves != null ? new ArrayList<>(Arrays.asList(veiculosLeves)) : new ArrayList<>());
        cargaExistente.setVeiculosMedios(veiculosMedios != null ? new ArrayList<>(Arrays.asList(veiculosMedios)) : new ArrayList<>());
        cargaExistente.setVeiculosPesados(veiculosPesados != null ? new ArrayList<>(Arrays.asList(veiculosPesados)) : new ArrayList<>());
        cargaExistente.setFretesFechados(fretesFechados != null ? new ArrayList<>(Arrays.asList(fretesFechados)) : new ArrayList<>());
        cargaExistente.setFretesAbertos(fretesAbertos != null ? new ArrayList<>(Arrays.asList(fretesAbertos)) : new ArrayList<>());
        cargaExistente.setFretesEspeciais(fretesEspeciais != null ? new ArrayList<>(Arrays.asList(fretesEspeciais)) : new ArrayList<>());
    }

    private void inicializarListasCarga(Carga carga) {
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
    }

    @GetMapping("/detalhar/{id}")
    public String detalharCarga(@PathVariable Long id, Model model, HttpSession session) {
        Carga carga = cargaService.buscarPorId(id);

        if (carga == null) {
            return "redirect:/cargas/listar";
        }


        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        if (tipoUsuario == TipoUsuario.EMPRESA) {
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
            if (usuarioLogado != null && usuarioLogado.getEmpresa() != null &&
                    !usuarioLogado.getEmpresa().equals(carga.getEmpresaCarga())) {
                return "redirect:/cargas/listar";
            }
        }

        List<Veiculo> veiculos = veiculoService.buscarPorTransportadoraId(id);
        model.addAttribute("veiculo", veiculos);

        model.addAttribute("carga", carga);
        return "cargas/detalhar";
    }

    @GetMapping("/detalhar-admin/{id}")
    public String detalharCargaAdmin(@PathVariable Long id, Model model, HttpSession session) {
        Carga carga = cargaService.buscarPorId(id);
        if (carga == null) {
            return "redirect:/cargas/listar";
        }

        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        if (tipoUsuario != TipoUsuario.ADMIN) {
            return "redirect:/cargas/listar";
        }

        model.addAttribute("carga", carga);
        model.addAttribute("tipoUsuario", tipoUsuario);
        return "cargas/detalhar-cargas";
    }

    @GetMapping("/deletar/{id}")
    public String deletarCarga(@PathVariable Long id, HttpSession session) {

        TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuario");
        if (tipoUsuario != TipoUsuario.EMPRESA) {
            return "redirect:/cargas/listar";
        }

        Carga carga = cargaService.buscarPorId(id);
        if (carga != null) {
            // Verificar se a carga pertence à empresa do usuário
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
            if (usuarioLogado != null && usuarioLogado.getEmpresa() != null &&
                    usuarioLogado.getEmpresa().equals(carga.getEmpresaCarga())) {
                cargaService.deletar(id);
            }
        }

        return "redirect:/cargas/listar";
    }
}