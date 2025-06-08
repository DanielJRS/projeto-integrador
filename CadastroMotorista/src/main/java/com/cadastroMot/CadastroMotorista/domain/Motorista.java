package com.cadastroMot.CadastroMotorista.domain;

import com.cadastroMot.CadastroMotorista.domain.Carga;
import com.cadastroMot.CadastroMotorista.domain.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table (name = "tb_motorista")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Motorista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    @OneToMany (mappedBy = "motorista")
    private List<Carga> cargas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany (mappedBy = "motoristaFrete")
    private List<Frete> fretes;

    @OneToMany (mappedBy = "motoristaVeiculo")
    private List<Veiculo> veiculos;

    @ManyToOne
    @JoinColumn (name = "transportadora_id")
    private Transportadora transportadoraMotorista;
}