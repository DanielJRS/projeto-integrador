package com.cadastroMot.CadastroMotorista.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
