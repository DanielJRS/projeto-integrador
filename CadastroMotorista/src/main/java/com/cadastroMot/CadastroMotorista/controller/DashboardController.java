package com.cadastroMot.CadastroMotorista.controller;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.service.CargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private CargaService cargaService;

    @GetMapping("/")
    public String index(Model model) {
        System.out.println("Entrou no dashboard");
        List<Carga> cargas = cargaService.listarTodos();
        model.addAttribute("cargas", cargas);
        return "index";
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
}