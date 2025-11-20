package com.bcopstein.ex4_lancheriaddd_v1.Dominio.DTO;

public class ProdutoDTO {
    private Long id;
    private String nome;
    private double preco;

    public ProdutoDTO() {}

    public ProdutoDTO(Long id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", preco=" + preco +
               '}';
    }
}