package com.cadastroMot.CadastroMotorista.domain;

public enum TipoEstadoCarga {
    DISPONIVEL("Disponível"),
    INDISPONIVEL("Indisponível"),
    ANDAMENTO("Em andamento"),
    ACEITA("Aceita"),
    EM_TRANSITO("Em trânsito");

    private final String descricao;

    TipoEstadoCarga(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

