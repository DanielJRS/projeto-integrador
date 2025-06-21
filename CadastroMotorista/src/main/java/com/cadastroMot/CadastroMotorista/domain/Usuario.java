package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

    @OneToOne(mappedBy = "usuario")
    @EqualsAndHashCode.Exclude
    private Motorista motorista;

    @OneToOne(mappedBy = "usuario")
    @EqualsAndHashCode.Exclude
    private Empresa empresa;

    @OneToOne(mappedBy = "usuario")
    @EqualsAndHashCode.Exclude
    private Transportadora transportadora;


    public Empresa getEmpresa() {
        return empresa;
    }



}