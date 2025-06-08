package com.cadastroMot.CadastroMotorista.service;

import com.cadastroMot.CadastroMotorista.domain.*;
import com.cadastroMot.CadastroMotorista.repository.FreteRepository;
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
    private final FreteRepository freteRepository;
    private final FreteService freteService;

    @Autowired
    public MotoristaService(MotoristaRepository motoristaRepository,
                            UsuarioRepository usuarioRepository,
                            PasswordEncoder passwordEncoder,
                            FreteRepository freteRepository,
                            FreteService freteService) {

        this.motoristaRepository = motoristaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.freteRepository = freteRepository;
        this.freteService = freteService;
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

    public List<Motorista> listarTodos() {
        List<Motorista> lista = motoristaRepository.findAll();
        return lista != null ? lista : List.of();
    }

    public Motorista buscarPorId(Long id) {
        return motoristaRepository.findById(id).orElse(null);
    }

    public Motorista findByUsuario(Usuario usuario) {
        return motoristaRepository.findByUsuario(usuario);
    }

    public List<Motorista> listarPorTransportadora(Transportadora transportadora) {
        return motoristaRepository.findByTransportadoraMotorista(transportadora);
    }

    public Long contarFretesAtivos(Motorista motorista){
        return freteRepository.countByMotoristaFrete(motorista);
    }

    public Long contarFretesAtivosEStatus(Motorista motorista, TipoEstadoFrete status) {
        return freteService.contarFretesAtivosEStatus(motorista, String.valueOf(status));
    }
}
