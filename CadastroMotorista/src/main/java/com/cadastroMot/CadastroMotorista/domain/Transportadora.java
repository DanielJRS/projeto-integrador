package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table (name = "tb_transportadora")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transportadora {
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
    private boolean ativo;
    private boolean souTransportadora;
    private String numeroRegistroANTT;
    private String tipoFrota;
    private Integer quantidadeVeiculos;
    private Boolean possuiSeguroCarga;
    private String tiposMercadorias;
    private String capacidadeCarga;
    private Boolean rastreamentoVeiculos;
    private Integer prazoPadraoEntrega;
    private LocalDate dataVencimentoLicenca;
    private String categoriasLicenca;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany (mappedBy = "transportadoraMotorista")
    private List<Motorista> motoristas;

    @OneToMany (mappedBy = "transportadoraVeiculo")
    private List<Veiculo> veiculos;

    @OneToMany (mappedBy = "transportadoraFrete")
    private List<Frete> fretes;


}
