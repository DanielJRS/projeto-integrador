package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_empresa")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String inscricaoEstadual;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String email;
    private String senha;
    private LocalDate dataFundacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany (mappedBy = "empresaCarga")
    private List<Carga> cargas;
}
