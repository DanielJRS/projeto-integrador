package com.cadastroMot.CadastroMotorista.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table (name = "tb_empresa")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Empresa {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    // cadastro de empresa padrão
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
    private LocalDate dataFundacao;
    private boolean ativo;

    // cadastro para empresa caso seja também uma transportadora
    private boolean souTransportadora;
    private String numeroRegistroANTT;
    private String tipoFrota;
    private int quantidadeVeiculos;
    private boolean possuiSeguroCarga;
    private String tiposMercadorias;
    private String capacidadeCarga;
    private boolean rastreamentoVeiculos;
    private int prazoPadraoEntrega;
    private LocalDate dataVencimentoLicenca;
    private String categoriasLicenca;
}
