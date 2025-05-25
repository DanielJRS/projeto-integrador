package com.cadastroMot.CadastroMotorista.domain;

public enum TipoCarga {
    COMPLETA("Completa"),
    CONSOLIDADA("Consolidada");

    private final String descricao;

    TipoCarga(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoCarga fromDescricao(String descricao) {
        for (TipoCarga tipoCarga : TipoCarga.values()) {
            if (tipoCarga.getDescricao().equalsIgnoreCase(descricao)) {
                return tipoCarga;
            }
        }
        throw new IllegalArgumentException("Tipo de carga inválido: " + descricao);
    }
}