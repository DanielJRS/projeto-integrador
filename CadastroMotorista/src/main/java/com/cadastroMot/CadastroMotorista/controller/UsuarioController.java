package com.cadastroMot.CadastroMotorista.controller;

import  com.cadastroMot.CadastroMotorista.domain.Usuario;
import  com.cadastroMot.CadastroMotorista.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        model.addAttribute("usuario", usuario);
        return "usuarios/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";
    }
}