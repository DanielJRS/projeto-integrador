package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.domain.TipoUsuario;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.repository.MotoristaRepository;
import com.cadastroMot.CadastroMotorista.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MotoristaService {
    private final MotoristaRepository motoristaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MotoristaService(MotoristaRepository motoristaRepository,
                            UsuarioRepository usuarioRepository,
                            PasswordEncoder passwordEncoder) {
        this.motoristaRepository = motoristaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Motorista salvar(Motorista motorista){
        return motoristaRepository.save(motorista);
    }

    @Transactional
    public Motorista salvarComUsuario(Motorista motorista, String email, String senha) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Já existe um usuário cadastrado com este email");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(passwordEncoder.encode(senha));
        novoUsuario.setTipo(TipoUsuario.MOTORISTA);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        motorista.setUsuario(usuarioSalvo);

        return motoristaRepository.save(motorista);
    }

    public List<Motorista> listarTodos(){
        return motoristaRepository.findAll();
    }

    public Motorista buscarPorId(Long id) {
        return motoristaRepository.findById(id).orElse(null);
    }
}