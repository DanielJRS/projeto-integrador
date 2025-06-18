package com.cadastroMot.CadastroMotorista.domain;

import java.time.LocalDate;

public class CargaFiltro {

    // FILTRO POR EMPRESA - CAMPO PRINCIPAL PARA SEU CASO
    private Empresa empresa;

    // FILTROS DE LOCALIZAÇÃO
    private String origemCidade;
    private String origemEstado;
    private String destinoCidade;
    private String destinoEstado;

    // FILTROS DE PRODUTO
    private String produto;
    private String especie;

    // FILTROS DE TIPO
    private TipoCarga tipoCarga;
    private TipoEstadoCarga tipoEstadoCarga;

    // FILTROS DE DATA
    private LocalDate dataColetaDe;
    private LocalDate dataColetaAte;
    private LocalDate dataEntregaDe;
    private LocalDate dataEntregaAte;

    // FILTROS DE PREÇO
    private Double precoMinimo;
    private Double precoMaximo;

    // FILTROS DE PESO
    private Double pesoMinimo;
    private Double pesoMaximo;

    // FILTROS ADICIONAIS
    private Boolean possuiLona;
    private Double volumeMinimo;
    private Double volumeMaximo;

    // CONSTRUTORES
    public CargaFiltro() {}

    // GETTERS E SETTERS

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getOrigemCidade() {
        return origemCidade;
    }

    public void setOrigemCidade(String origemCidade) {
        this.origemCidade = origemCidade;
    }

    public String getOrigemEstado() {
        return origemEstado;
    }

    public void setOrigemEstado(String origemEstado) {
        this.origemEstado = origemEstado;
    }

    public String getDestinoCidade() {
        return destinoCidade;
    }

    public void setDestinoCidade(String destinoCidade) {
        this.destinoCidade = destinoCidade;
    }

    public String getDestinoEstado() {
        return destinoEstado;
    }

    public void setDestinoEstado(String destinoEstado) {
        this.destinoEstado = destinoEstado;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public TipoCarga getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(TipoCarga tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public TipoEstadoCarga getTipoEstadoCarga() {
        return tipoEstadoCarga;
    }

    public void setTipoEstadoCarga(TipoEstadoCarga tipoEstadoCarga) {
        this.tipoEstadoCarga = tipoEstadoCarga;
    }

    public LocalDate getDataColetaDe() {
        return dataColetaDe;
    }

    public void setDataColetaDe(LocalDate dataColetaDe) {
        this.dataColetaDe = dataColetaDe;
    }

    public LocalDate getDataColetaAte() {
        return dataColetaAte;
    }

    public void setDataColetaAte(LocalDate dataColetaAte) {
        this.dataColetaAte = dataColetaAte;
    }

    public LocalDate getDataEntregaDe() {
        return dataEntregaDe;
    }

    public void setDataEntregaDe(LocalDate dataEntregaDe) {
        this.dataEntregaDe = dataEntregaDe;
    }

    public LocalDate getDataEntregaAte() {
        return dataEntregaAte;
    }

    public void setDataEntregaAte(LocalDate dataEntregaAte) {
        this.dataEntregaAte = dataEntregaAte;
    }

    public Double getPrecoMinimo() {
        return precoMinimo;
    }

    public void setPrecoMinimo(Double precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    public Double getPrecoMaximo() {
        return precoMaximo;
    }

    public void setPrecoMaximo(Double precoMaximo) {
        this.precoMaximo = precoMaximo;
    }

    public Double getPesoMinimo() {
        return pesoMinimo;
    }

    public void setPesoMinimo(Double pesoMinimo) {
        this.pesoMinimo = pesoMinimo;
    }

    public Double getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(Double pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public Boolean getPossuiLona() {
        return possuiLona;
    }

    public void setPossuiLona(Boolean possuiLona) {
        this.possuiLona = possuiLona;
    }

    public Double getVolumeMinimo() {
        return volumeMinimo;
    }

    public void setVolumeMinimo(Double volumeMinimo) {
        this.volumeMinimo = volumeMinimo;
    }

    public Double getVolumeMaximo() {
        return volumeMaximo;
    }

    public void setVolumeMaximo(Double volumeMaximo) {
        this.volumeMaximo = volumeMaximo;
    }
}