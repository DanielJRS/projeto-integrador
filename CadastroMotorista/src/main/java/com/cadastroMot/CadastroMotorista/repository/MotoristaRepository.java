package com.cadastroMot.CadastroMotorista.repository;

import com.cadastroMot.CadastroMotorista.domain.Motorista;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import com.cadastroMot.CadastroMotorista.domain.Transportadora;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
    Motorista findByUsuario(Usuario usuario);
}

//package com.cadastroMot.CadastroMotorista.repository;
//
//import com.cadastroMot.CadastroMotorista.domain.Motorista;
//import com.cadastroMot.CadastroMotorista.domain.Usuario;
//import com.cadastroMot.CadastroMotorista.domain.Transportadora;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
//    Motorista findByUsuario(Usuario usuario);
//    List<Motorista> findByTransportadora(Transportadora transportadora);


//package com.cadastroMot.CadastroMotorista.service;
//
//import com.cadastroMot.CadastroMotorista.domain.Motorista;
//import com.cadastroMot.CadastroMotorista.repository.MotoristaRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class MotoristaService {
//    @Autowired
//    private MotoristaRepository motoristaRepository;
//
//    public List<Motorista> filtrar(String nome, String cidade, String estado) {
//        return motoristaRepository.findByFiltros(nome, cidade, estado);
//    }
//}