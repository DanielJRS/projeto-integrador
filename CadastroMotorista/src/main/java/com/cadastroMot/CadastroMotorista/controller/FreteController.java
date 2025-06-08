package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.*;
import com.cadastroMot.CadastroMotorista.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class FreteController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private CargaService cargaService;

    @Autowired
    private MotoristaService motoristaService;

    @Autowired
    private FreteService freteService;

    @GetMapping("/aceitar-frete")
    public String aceitarFrete(@RequestParam Long cargaId,
                             HttpSession session,
                             Model model) {

        Carga carga = cargaService.buscarPorId(cargaId);

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        Motorista motorista = motoristaService.buscarPorId(usuarioLogado.getId());

        Frete frete = new Frete();
        frete.setCargas(carga);
        frete.setMotoristaFrete(motorista);
        frete.setOrigemCidade(carga.getOrigemCidade());
        frete.setOrigemEstado(carga.getOrigemEstado());
        frete.setDestinoCidade(carga.getDestinoCidade());
        frete.setDestinoEstado(carga.getDestinoEstado());
        frete.setProduto(carga.getProduto());
        frete.setPesoTotal(carga.getPesoTotal());
        frete.setValor(carga.getPreco().toString());
        frete.setStatus(TipoEstadoFrete.ATIVO.toString());

        carga.setTipoEstadoCarga(TipoEstadoCarga.ANDAMENTO);

        freteService.salvar(frete);
        cargaService.salvar(carga);

        return "redirect:/motorista/dashboard";
    }

    @GetMapping("/frete/{id}")
    public String detalheFrete(@PathVariable Long id,
                               Model model) {

        Frete frete = freteService.buscarPorId(id).orElse(null);

        if (frete == null) {
            return "redirect:/motorista";
        }

        Empresa empresa = empresaService.empresa();

        frete.setNomeFantasia(empresa.getNomeFantasia());
        frete.setTelefone(empresa.getTelefone());
        frete.setEmail(empresa.getEmail());
        frete.setCnpj(empresa.getCnpj());

        model.addAttribute("empresa", empresa);
        model.addAttribute("frete", frete);

        return "/fretes/dashboard-fretes";
    }
}
