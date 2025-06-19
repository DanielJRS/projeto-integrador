package com.cadastroMot.CadastroMotorista.domain;

public enum TipoEstadoCarga {
    DISPONIVEL("Disponível"),
    INDISPONIVEL("Indisponível"),
    ANDAMENTO("Em andamento");

    private final String descricao;

    TipoEstadoCarga(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

