package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table (name = "tb_frete")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String data_criacao;
    private String valor;
    private String origemCidade;
    private String origemEstado;
    private String destinoEstado;
    private String destinoCidade;
    private String produto;
    private Double pesoTotal;
//    private Long fretesAtivos;
    private Long fretesFinalizados;

    private String nomeFantasia;
    private String cnpj;
    private String telefone;
    private String email;

    @OneToOne (mappedBy = "frete")
    private Carga cargas;

    @ManyToOne
    @JoinColumn (name = "motorista_id")
    private Motorista motoristaFrete;

    @ManyToOne
    @JoinColumn (name = "transportadora_id")
    private Transportadora transportadoraFrete;
}
