package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import com.cadastroMot.CadastroMotorista.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private CargaService cargaService;

    @Autowired
    private MotoristaService motoristaService;

    @GetMapping("/")
    public String index(Model model) {
        System.out.println("Entrou no dashboard");
        List<Carga> cargas = cargaService.listarTodos();
        model.addAttribute("cargas", cargas);
        return "dashboard/index";
    }

    @GetMapping("/motoristas")
    public String dashboardMotoristas() {
        return "dashboard/motorista-index";
    }

    @GetMapping("/cargas")
    public String dashboardCargas() {
        return "dashboard/carga-index";
    }

    @GetMapping("/empresas")
    public String dashboardEmpresa() {
        return "dashboard/empresa-index";
    }

    @GetMapping("/veiculos")
    public String dashboardVeiculos() {
        return "dashboard/veiculo-index";
    }


    @GetMapping("/motoristas-listartodos")
    public String listarTodosMotoristas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String transportadora,
            HttpSession session,
            Model model) {

        Object tipoUsuario = session.getAttribute("tipoUsuario");
        if (tipoUsuario == null || !"ADMIN".equals(tipoUsuario.toString())) {
            return "redirect:/login";
        }

        List<Motorista> motoristas = motoristaService.buscarMotoristas(nome, transportadora);
        model.addAttribute("motoristas", motoristas);
        model.addAttribute("nome", nome);
        model.addAttribute("transportadora", transportadora);

        return "dashboard/mototristas-listartodos";
    }
}