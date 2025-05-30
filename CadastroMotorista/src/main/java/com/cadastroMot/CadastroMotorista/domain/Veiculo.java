package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ElementCollection;
import java.util.List;

@Entity
@Table (name = "tb_veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private String modelo;
    private String marca;
    private Double capacidadeCarga;
    private String renavam;
    private String chassi;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    @ElementCollection
    private List<String> tipos;

    @ElementCollection
    private List<String> fretesFechados;

    @ElementCollection
    private List<String> fretesAbertos;

    @ElementCollection
    private List<String> fretesEspeciais;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public Double getCapacidadeCarga() { return capacidadeCarga; }
    public void setCapacidadeCarga(Double capacidadeCarga) { this.capacidadeCarga = capacidadeCarga; }

    public String getRenavam() { return renavam; }
    public void setRenavam(String renavam) { this.renavam = renavam; }

    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }

    public Motorista getMotorista() { return motorista; }
    public void setMotorista(Motorista motorista) { this.motorista = motorista; }
    public List<String> getTipos() { return tipos; }
    public void setTipos(List<String> tipos) { this.tipos = tipos; }

    public List<String> getFretesFechados() { return fretesFechados; }
    public void setFretesFechados(List<String> fretesFechados) { this.fretesFechados = fretesFechados; }

    public List<String> getFretesAbertos() { return fretesAbertos; }
    public void setFretesAbertos(List<String> fretesAbertos) { this.fretesAbertos = fretesAbertos; }

    public List<String> getFretesEspeciais() { return fretesEspeciais; }
    public void setFretesEspeciais(List<String> fretesEspeciais) { this.fretesEspeciais = fretesEspeciais; }

}
