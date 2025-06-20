package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.Empresa;
import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.repository.EmpresaRepository;
import com.cadastroMot.CadastroMotorista.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Empresa salvar (Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa salvarComUsuario(Empresa empresa, String email, String senha) {
        if (usuarioRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Já existe uma empresa cadastrada com este e-mail");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(passwordEncoder.encode(senha));
        novoUsuario.setTipo(TipoUsuario.EMPRESA);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        empresa.setUsuario(usuarioSalvo);

        return empresaRepository.save(empresa);
    }

    public List<Empresa> listarTodos() {
        return empresaRepository.findAll();
    }

    public List<Empresa> buscarEmpresas(String razaoSocial, String nomeFantasia, String cnpj) {
    return empresaRepository.findByRazaoSocialContainingIgnoreCaseAndNomeFantasiaContainingIgnoreCaseAndCnpjContainingIgnoreCase(
        razaoSocial == null ? "" : razaoSocial,
        nomeFantasia == null ? "" : nomeFantasia,
        cnpj == null ? "" : cnpj
    );
}
}
