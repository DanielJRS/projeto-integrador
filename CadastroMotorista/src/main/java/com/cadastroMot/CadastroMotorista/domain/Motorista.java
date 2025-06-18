package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_motorista")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Motorista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String endereco;
    private String celular;
    private String cidade;
    private String estado;
    private String pais;
    private String cnh;
    private String antt;
    private byte[] foto;
    private String tipoFoto;

    @OneToMany(mappedBy = "motorista")
    private List<Carga> cargas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "motoristaFrete")
    private List<Frete> fretes;

    @OneToMany(mappedBy = "motoristaVeiculo")
    private List<Veiculo> veiculos;

    @ManyToOne
    @JoinColumn(name = "transportadora_id")
    private Transportadora transportadoraMotorista;



    @ManyToOne
    @JoinColumn(name = "transportadora_id", insertable = false, updatable = false)
    private Transportadora transportadora;
}