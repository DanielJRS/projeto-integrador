package com.cadastroMot.CadastroMotorista;

import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String inicioLogin(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "login";
    }

    @PostMapping("/login")
    public String processarLogin(@ModelAttribute("usuario") Usuario usuario,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        System.out.println("Tentativa de login para: " + usuario.getEmail());

        Optional<Usuario> usuarioEncontrado = usuarioService.buscarPorEmail(usuario.getEmail());

        if (usuarioEncontrado.isPresent()) {
            Usuario usuarioLogado = usuarioEncontrado.get();

            boolean senhaValida;
            if (usuarioLogado.getSenha().startsWith("$2a$")) {
                senhaValida = passwordEncoder.matches(usuario.getSenha(), usuarioLogado.getSenha());
            } else {
                senhaValida = usuario.getSenha().equals(usuarioLogado.getSenha());
                System.out.println("AVISO: Senha não criptografada detectada!");
            }

            if (senhaValida) {
                System.out.println("Login bem-sucedido. Tipo: " + usuarioLogado.getTipo());
                session.setAttribute("usuarioLogado", usuarioLogado);
                session.setAttribute("tipoUsuario", usuarioLogado.getTipo());
                session.setAttribute("usuarioId", usuarioLogado.getId());

                String redirect = redirecionarPorTipoUsuario(usuarioLogado);
                return redirect;
            }
        }

        System.out.println("Credenciais inválidas");
        redirectAttributes.addFlashAttribute("mensagemErro", "Credenciais inválidas");
        return "redirect:/login";
    }

    private String redirecionarPorTipoUsuario(Usuario usuario) {
        switch (usuario.getTipo()) {
            case MOTORISTA:
                return "redirect:/motorista/dashboard";

            case EMPRESA:
                return "redirect:/empresa/dashboard";

            case TRANSPORTADORA:
                return "redirect:/transportadora/dashboard";

            default:
                return "redirect:/index";
        }
    }

    @GetMapping("/index")
    public String home(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuarioLogado);

        return redirecionarPorTipoUsuario(usuarioLogado);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}