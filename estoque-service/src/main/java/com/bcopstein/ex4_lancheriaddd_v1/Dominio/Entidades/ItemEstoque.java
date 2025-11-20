package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "itensEstoque")
public class ItemEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;

    @ManyToOne 
    @JoinColumn(name = "ingrediente_id", referencedColumnName = "id", nullable = false)
    private Ingrediente ingrediente;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    public ItemEstoque() {}

    public ItemEstoque(Ingrediente ingrediente, int quantidade) {
        this.ingrediente = ingrediente;
        this.quantidade = quantidade;
    }

    public long getId() { return id; }
    public Ingrediente getIngrediente() { return ingrediente; }
    public int getQuantidade() { return quantidade; }

    public void setId(long id) { this.id = id; }
    public void setIngrediente(Ingrediente ingrediente) { this.ingrediente = ingrediente; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    @Override
    public String toString() {
        return "ItemEstoque [id=" + id + ", ingrediente=" + (ingrediente != null ? ingrediente.getDescricao() : "N/A") + ", quantidade=" + quantidade + "]";
    }
}