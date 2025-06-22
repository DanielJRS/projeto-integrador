package Dto;

public class MotoristaDTO {
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
    private String tipoFoto;

    public MotoristaDTO() {}

    public MotoristaDTO(Long id, String nome, String cpf, String endereco, String celular, String cidade, String estado, String pais, String cnh, String antt, String tipoFoto) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.celular = celular;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cnh = cnh;
        this.antt = antt;
        this.tipoFoto = tipoFoto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getCnh() { return cnh; }
    public void setCnh(String cnh) { this.cnh = cnh; }

    public String getAntt() { return antt; }
    public void setAntt(String antt) { this.antt = antt; }

    public String getTipoFoto() { return tipoFoto; }
    public void setTipoFoto(String tipoFoto) { this.tipoFoto = tipoFoto; }
}
