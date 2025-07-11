package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    private Long id;

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
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany (mappedBy = "empresaCarga")
    @EqualsAndHashCode.Exclude
    private List<Carga> cargas;

    @OneToMany (mappedBy = "empresaFrete")
    private List<Frete> fretes;


}
