package Dto;

import com.cadastroMot.CadastroMotorista.domain.TipoCarga;
import com.cadastroMot.CadastroMotorista.domain.TipoEstadoCarga;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CargaFiltroDTO {
    // Localização
    private String origemCidade;
    private String origemEstado;
    private String destinoCidade;
    private String destinoEstado;

    // Datas
    private LocalDate dataColetaDe;
    private LocalDate dataColetaAte;
    private LocalDate dataEntregaDe;
    private LocalDate dataEntregaAte;

    // Produto
    private String produto;
    private String especie;

    // Transporte
    private TipoCarga tipoCarga;
    private Boolean possuiLona;

    // Valores
    private Double precoMinimo;
    private Double precoMaximo;
    private Double pesoMinimo;
    private Double pesoMaximo;
    private Double volumeMinimo;
    private Double volumeMaximo;

    // Status
    private TipoEstadoCarga tipoEstadoCarga;

    // Empresa (opcional)
    private Long empresaId;

    // Construtores
    public CargaFiltroDTO() {
    }

    // Getters e Setters
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

    public Boolean getPossuiLona() {
        return possuiLona;
    }

    public void setPossuiLona(Boolean possuiLona) {
        this.possuiLona = possuiLona;
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

    public TipoEstadoCarga getTipoEstadoCarga() {
        return tipoEstadoCarga;
    }

    public void setTipoEstadoCarga(TipoEstadoCarga tipoEstadoCarga) {
        this.tipoEstadoCarga = tipoEstadoCarga;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    // Método utilitário para verificar se há filtros aplicados
    public boolean temFiltros() {
        return (origemCidade != null && !origemCidade.trim().isEmpty()) ||
                (origemEstado != null && !origemEstado.trim().isEmpty()) ||
                (destinoCidade != null && !destinoCidade.trim().isEmpty()) ||
                (destinoEstado != null && !destinoEstado.trim().isEmpty()) ||
                dataColetaDe != null || dataColetaAte != null ||
                dataEntregaDe != null || dataEntregaAte != null ||
                (produto != null && !produto.trim().isEmpty()) ||
                (especie != null && !especie.trim().isEmpty()) ||
                tipoCarga != null || possuiLona != null ||
                precoMinimo != null || precoMaximo != null ||
                pesoMinimo != null || pesoMaximo != null ||
                volumeMinimo != null || volumeMaximo != null ||
                tipoEstadoCarga != null || empresaId != null;
    }
}