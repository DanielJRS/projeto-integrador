package Dto;

import java.time.LocalDate;
import java.util.List;

public class CargasDTO {
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
    private Double precoFrete;
    private String tipoCarga;
    private String tipoEstadoCarga;
    private Boolean possuiLona;
    private Double pesoTotal;
    private Double limiteAltura;
    private Double volume;
    private List<String> veiculosLeves;
    private List<String> veiculosMedios;
    private List<String> veiculosPesados;
    private List<String> fretesFechados;
    private List<String> fretesAbertos;
    private List<String> fretesEspeciais;

    public CargasDTO() {}

    public CargasDTO(Long id, String origemCidade, String origemEstado, LocalDate dataColeta, String destinoCidade, String destinoEstado, LocalDate dataEntrega, String produto, String especie, String veiculo, Double preco, Double precoFrete, String tipoCarga, String tipoEstadoCarga, Boolean possuiLona, Double pesoTotal, Double limiteAltura, Double volume, List<String> veiculosLeves, List<String> veiculosMedios, List<String> veiculosPesados, List<String> fretesFechados, List<String> fretesAbertos, List<String> fretesEspeciais) {
        this.id = id;
        this.origemCidade = origemCidade;
        this.origemEstado = origemEstado;
        this.dataColeta = dataColeta;
        this.destinoCidade = destinoCidade;
        this.destinoEstado = destinoEstado;
        this.dataEntrega = dataEntrega;
        this.produto = produto;
        this.especie = especie;
        this.veiculo = veiculo;
        this.preco = preco;
        this.precoFrete = precoFrete;
        this.tipoCarga = tipoCarga;
        this.tipoEstadoCarga = tipoEstadoCarga;
        this.possuiLona = possuiLona;
        this.pesoTotal = pesoTotal;
        this.limiteAltura = limiteAltura;
        this.volume = volume;
        this.veiculosLeves = veiculosLeves;
        this.veiculosMedios = veiculosMedios;
        this.veiculosPesados = veiculosPesados;
        this.fretesFechados = fretesFechados;
        this.fretesAbertos = fretesAbertos;
        this.fretesEspeciais = fretesEspeciais;
    }

    // Getters e setters para todos os campos

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrigemCidade() { return origemCidade; }
    public void setOrigemCidade(String origemCidade) { this.origemCidade = origemCidade; }

    public String getOrigemEstado() { return origemEstado; }
    public void setOrigemEstado(String origemEstado) { this.origemEstado = origemEstado; }

    public LocalDate getDataColeta() { return dataColeta; }
    public void setDataColeta(LocalDate dataColeta) { this.dataColeta = dataColeta; }

    public String getDestinoCidade() { return destinoCidade; }
    public void setDestinoCidade(String destinoCidade) { this.destinoCidade = destinoCidade; }

    public String getDestinoEstado() { return destinoEstado; }
    public void setDestinoEstado(String destinoEstado) { this.destinoEstado = destinoEstado; }

    public LocalDate getDataEntrega() { return dataEntrega; }
    public void setDataEntrega(LocalDate dataEntrega) { this.dataEntrega = dataEntrega; }

    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getVeiculo() { return veiculo; }
    public void setVeiculo(String veiculo) { this.veiculo = veiculo; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Double getPrecoFrete() { return precoFrete; }
    public void setPrecoFrete(Double precoFrete) { this.precoFrete = precoFrete; }

    public String getTipoCarga() { return tipoCarga; }
    public void setTipoCarga(String tipoCarga) { this.tipoCarga = tipoCarga; }

    public String getTipoEstadoCarga() { return tipoEstadoCarga; }
    public void setTipoEstadoCarga(String tipoEstadoCarga) { this.tipoEstadoCarga = tipoEstadoCarga; }

    public Boolean getPossuiLona() { return possuiLona; }
    public void setPossuiLona(Boolean possuiLona) { this.possuiLona = possuiLona; }

    public Double getPesoTotal() { return pesoTotal; }
    public void setPesoTotal(Double pesoTotal) { this.pesoTotal = pesoTotal; }

    public Double getLimiteAltura() { return limiteAltura; }
    public void setLimiteAltura(Double limiteAltura) { this.limiteAltura = limiteAltura; }

    public Double getVolume() { return volume; }
    public void setVolume(Double volume) { this.volume = volume; }

    public List<String> getVeiculosLeves() { return veiculosLeves; }
    public void setVeiculosLeves(List<String> veiculosLeves) { this.veiculosLeves = veiculosLeves; }

    public List<String> getVeiculosMedios() { return veiculosMedios; }
    public void setVeiculosMedios(List<String> veiculosMedios) { this.veiculosMedios = veiculosMedios; }

    public List<String> getVeiculosPesados() { return veiculosPesados; }
    public void setVeiculosPesados(List<String> veiculosPesados) { this.veiculosPesados = veiculosPesados; }

    public List<String> getFretesFechados() { return fretesFechados; }
    public void setFretesFechados(List<String> fretesFechados) { this.fretesFechados = fretesFechados; }

    public List<String> getFretesAbertos() { return fretesAbertos; }
    public void setFretesAbertos(List<String> fretesAbertos) { this.fretesAbertos = fretesAbertos; }

    public List<String> getFretesEspeciais() { return fretesEspeciais; }
    public void setFretesEspeciais(List<String> fretesEspeciais) { this.fretesEspeciais = fretesEspeciais; }
}
