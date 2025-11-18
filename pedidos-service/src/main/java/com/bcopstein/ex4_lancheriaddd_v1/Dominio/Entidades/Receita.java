package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import java.util.Map;

public class Receita {
    private Produto produto;
    // quantidade de cada ingrediente necessário para esse produto
    private Map<Ingrediente, Integer> ingredientes;

    public Receita(Produto produto, Map<Ingrediente, Integer> ingredientes) {
        this.produto = produto;
        this.ingredientes = ingredientes;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Map<Ingrediente, Integer> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Map<Ingrediente, Integer> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
