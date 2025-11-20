package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredientes")
public class Ingrediente {
    @Id
    private long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    public Ingrediente() {}

    public Ingrediente(long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public long getId() { return id; }
    public String getDescricao() { return descricao; }
    
    public void setId(long id) { this.id = id; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return "Ingrediente [id=" + id + ", descricao=" + descricao + "]";
    }
}