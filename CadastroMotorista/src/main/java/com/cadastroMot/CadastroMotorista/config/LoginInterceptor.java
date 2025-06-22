package com.cadastroMot.CadastroMotorista.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String uri = request.getRequestURI();
        System.out.println("Interceptando URI: " + uri);

        if      (uri.startsWith("/api/") ||
                uri.startsWith("/swagger-ui/") ||
                uri.startsWith("/v3/api-docs") ||
                uri.equals("/login") ||
                uri.equals("/") ||
                uri.equals("/index") ||
                uri.equals("/logout") ||
                uri.startsWith("/css/") ||
                uri.startsWith("/js/") ||
                uri.startsWith("/img/") ||
                uri.equals("/motoristas/formulario-motorista") ||
                uri.equals("/empresas/formulario-empresa") ||
                uri.equals("/transportadoras/formulario-transportadora") ||
                uri.equals("/transportadora/novo") ||
                uri.equals("/motorista/novo") ||
                uri.equals("/empresa/novo") ||
                uri.equals("/motorista/salvar") ||
                uri.equals("/empresa/salvar") ||
                uri.equals("/transportadora/salvar") ||
                uri.equals("/motoristas/dashboard-motorista") ||
                uri.equals("/empresas/dashboard-empresa") ||
                uri.equals("/transportadoras/dashboard-transportadora") ||
                uri.equals("/veiculos/novo")||
                uri.equals("/error")){

            System.out.println("Acesso permitido para: " + uri);
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogado") != null) {
            System.out.println("Usuário logado, acesso permitido para: " + uri);
            return true;
        }

        System.out.println("Acesso negado, redirecionando para login: " + uri);
        response.sendRedirect("/");
        return false;
    }
}