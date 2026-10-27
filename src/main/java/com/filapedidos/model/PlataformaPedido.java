package com.filapedidos.model;

/**
 * PlataformaPedido
 */
public enum PlataformaPedido {
    IFOOD("IFOOD"),
    NINETY_NINE("99");

    private PlataformaPedido(String descricao){
        this.descricao = descricao;
    }
    
    private String descricao;

    public String getDescricao() {
        return descricao;
    }
}