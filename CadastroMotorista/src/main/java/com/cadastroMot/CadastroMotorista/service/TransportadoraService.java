package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.repository.TransportadoraRepository;
import com.cadastroMot.CadastroMotorista.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class TransportadoraService {
    private final TransportadoraRepository transportadoraRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TransportadoraService (TransportadoraRepository transportadoraRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.transportadoraRepository = transportadoraRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Transportadora salvar (Transportadora transportadora){
        return transportadoraRepository.save(transportadora);
    }

    @Transactional
    public Transportadora salvarComUsuario(Transportadora transportadora, String email, String senha){
        if (usuarioRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Já existe um usuário cadastrado com este e-mail");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(passwordEncoder.encode(senha));
        novoUsuario.setTipo(TipoUsuario.TRANSPORTADORA);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        transportadora.setUsuario(usuarioSalvo);

        return transportadoraRepository.save(transportadora);
    }

    public List<Transportadora> listarTodos() {
        return transportadoraRepository.findAll();
    }

    public List<Transportadora> buscarTransportadoras(String razaoSocial, String nomeFantasia, String cnpj) {
        if ((razaoSocial == null || razaoSocial.isEmpty()) &&
            (nomeFantasia == null || nomeFantasia.isEmpty()) &&
            (cnpj == null || cnpj.isEmpty())) {
            return transportadoraRepository.findAll();
        }
    
        return transportadoraRepository.findByRazaoSocialContainingIgnoreCaseAndNomeFantasiaContainingIgnoreCaseAndCnpjContainingIgnoreCase(
            razaoSocial == null ? "" : razaoSocial,
            nomeFantasia == null ? "" : nomeFantasia,
            cnpj == null ? "" : cnpj
        );
    }

    public Transportadora buscarPorId(Long id) {
        return transportadoraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transportadora não encontrada com ID: " + id));
    }

    public void excluirPorId(Long id) {
        transportadoraRepository.deleteById(id);
    }

    public Transportadora buscarPorIdOuNull(Long id) {
        return transportadoraRepository.findById(id).orElse(null);
    }
}
