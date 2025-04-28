package com.cadastroMot.CadastroMotorista.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/")
    public String index (){

        return "index";
    }

    @GetMapping("/motoristas")
    public String dashboardMotoristas() {
        return "dashboard/motorista-index";
    }
}