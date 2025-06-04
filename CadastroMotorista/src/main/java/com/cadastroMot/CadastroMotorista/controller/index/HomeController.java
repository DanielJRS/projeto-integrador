package com.cadastroMot.CadastroMotorista.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")  // ← MUDANÇA AQUI
    public String home(Model model) {
        return "index";
    }
}