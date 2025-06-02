package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cargas")
public class Carga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origemCidade;
    private String origemEstado;
    private LocalDate dataColeta;

    private String destinoCidade;
    private String destinoEstado;
    private LocalDate dataEntrega;

    private String produto;
    private String especie;
    private String veiculo;
    private Double preco;

    @Enumerated(EnumType.STRING)
    private TipoCarga tipoCarga;

    @Enumerated(EnumType.STRING)
    private TipoEstadoCarga tipoEstadoCarga = TipoEstadoCarga.DISPONIVEL;

    private Boolean possuiLona;
    private Double pesoTotal;
    private Double limiteAltura;
    private Double volume;

    @Enumerated(EnumType.STRING)
    private StatusCarga statusCarga = StatusCarga.DISPONIVEL;

    @ElementCollection
    @CollectionTable(name = "carga_veiculos_leves", joinColumns = @JoinColumn(name = "carga_id"))
    @Column(name = "veiculo_leve")
    private List<String> veiculosLeves = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "carga_veiculos_medios", joinColumns = @JoinColumn(name = "carga_id"))
    @Column(name = "veiculo_medio")
    private List<String> veiculosMedios = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "carga_veiculos_pesados", joinColumns = @JoinColumn(name = "carga_id"))
    @Column(name = "veiculo_pesado")
    private List<String> veiculosPesados = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "carga_fretes_fechados", joinColumns = @JoinColumn(name = "carga_id"))
    @Column(name = "frete_fechado")
    private List<String> fretesFechados = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "carga_fretes_abertos", joinColumns = @JoinColumn(name = "carga_id"))
    @Column(name = "frete_aberto")
    private List<String> fretesAbertos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "carga_fretes_especiais", joinColumns = @JoinColumn(name = "carga_id"))
    @Column(name = "frete_especial")
    private List<String> fretesEspeciais = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    @OneToOne
    @JoinColumn (name = "frete_id")
    private Frete frete;

    @ManyToOne
    @JoinColumn (name = "empresa_id")
    private Empresa empresaCarga;


    public TipoEstadoCarga getTipoEstadoCarga() {
        return tipoEstadoCarga;
    }

    public void setTipoEstadoCarga(TipoEstadoCarga tipoEstadoCarga) {
        this.tipoEstadoCarga = tipoEstadoCarga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigemCidade() {
        return origemCidade;
    }

    public void setOrigemCidade(String origemCidade) {
        this.origemCidade = origemCidade;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public Double getPreco() {
        return this.preco != null ? this.preco : 0.0;
    }

    public void setPreco(double preco) {
        this.preco = preco;
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

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getDestinoEstado() {
        return destinoEstado;
    }

    public void setDestinoEstado(String destinoEstado) {
        this.destinoEstado = destinoEstado;
    }

    public String getDestinoCidade() {
        return destinoCidade;
    }

    public void setDestinoCidade(String destinoCidade) {
        this.destinoCidade = destinoCidade;
    }

    public LocalDate getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(LocalDate dataColeta) {
        this.dataColeta = dataColeta;
    }

    public String getOrigemEstado() {
        return origemEstado;
    }

    public void setOrigemEstado(String origemEstado) {
        this.origemEstado = origemEstado;
    }


    public TipoCarga getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(TipoCarga tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public Boolean getPossuiLona() {
        return possuiLona;
    }

    public void setPossuiLona(Boolean possuiLona) {
        this.possuiLona = possuiLona;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public Double getLimiteAltura() {
        return limiteAltura;
    }

    public void setLimiteAltura(Double limiteAltura) {
        this.limiteAltura = limiteAltura;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public List<String> getVeiculosLeves() {
        return veiculosLeves;
    }

    public void setVeiculosLeves(List<String> veiculosLeves) {
        this.veiculosLeves = veiculosLeves;
    }

    public List<String> getVeiculosMedios() {
        return veiculosMedios;
    }

    public void setVeiculosMedios(List<String> veiculosMedios) {
        this.veiculosMedios = veiculosMedios;
    }

    public List<String> getVeiculosPesados() {
        return veiculosPesados;
    }

    public void setVeiculosPesados(List<String> veiculosPesados) {
        this.veiculosPesados = veiculosPesados;
    }

    public List<String> getFretesFechados() {
        return fretesFechados;
    }

    public void setFretesFechados(List<String> fretesFechados) {
        this.fretesFechados = fretesFechados;
    }

    public List<String> getFretesAbertos() {
        return fretesAbertos;
    }

    public void setFretesAbertos(List<String> fretesAbertos) {
        this.fretesAbertos = fretesAbertos;
    }

    public List<String> getFretesEspeciais() {
        return fretesEspeciais;
    }

    public void setFretesEspeciais(List<String> fretesEspeciais) {
        this.fretesEspeciais = fretesEspeciais;
    }
}