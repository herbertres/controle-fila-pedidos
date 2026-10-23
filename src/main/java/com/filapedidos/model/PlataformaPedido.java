package com.filapedidos.model;

/**
 * PlataformaPedido
 */
public enum PlataformaPedido {
    IFOOD("IFOOD"),
    NINETY_NINE("99"),
    UBER_EATS("UBER EATS"),
    OUTRA("OUTRA"),
    INTERNO("INTERNO");

    private PlataformaPedido(String descricao){
        this.descricao = descricao;
    }
    
    private String descricao;

    public String getDescricao() {
        return descricao;
    }
}