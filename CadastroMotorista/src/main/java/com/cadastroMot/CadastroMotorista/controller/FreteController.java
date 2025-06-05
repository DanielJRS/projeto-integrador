package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.*;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import com.cadastroMot.CadastroMotorista.service.FreteService;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FreteController {

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

        System.out.println("ID carga: " + carga.getId());
        System.out.println("ID motorista: " + motorista.getId());

        Frete frete = new Frete();
        frete.setCargas(carga);
        frete.setMotoristaFrete(motorista);
        frete.setOrigemCidade(carga.getOrigemCidade());
        frete.setOrigemEstado(carga.getOrigemEstado());
        frete.setDestinoCidade(carga.getDestinoCidade());
        frete.setDestinoEstado(carga.getDestinoEstado());
        carga.setTipoEstadoCarga(TipoEstadoCarga.ANDAMENTO);

        freteService.salvar(frete);
        cargaService.salvar(carga);

        return "redirect:/cargas/listar";
    }
}
