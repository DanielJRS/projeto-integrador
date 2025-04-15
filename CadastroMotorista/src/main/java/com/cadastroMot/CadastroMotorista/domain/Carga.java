package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.*;

@Entity
@Table(name =  "cargas")

public class Carga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String origem;
    private String destino;
    private String produto;
    private String especie;
    private String veiculo;
    private String preco;

    public Carga() {
    }

    public Carga(Long id, String origem, String destino, String produto, String especie, String veiculo, String preco) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.produto = produto;
        this.especie = especie;
        this.veiculo = veiculo;
        this.preco = preco;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
}
