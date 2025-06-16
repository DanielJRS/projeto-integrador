package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "tb_usuario")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    // Remover cascade para evitar conflitos de relacionamento circular
    @OneToOne(mappedBy = "usuario")
    private Motorista motorista;

    @OneToOne(mappedBy = "usuario")
    private Empresa empresa;

    @OneToOne(mappedBy = "usuario")
    private Transportadora transportadora;


    public Empresa getEmpresa() {
        return empresa;
    }



}