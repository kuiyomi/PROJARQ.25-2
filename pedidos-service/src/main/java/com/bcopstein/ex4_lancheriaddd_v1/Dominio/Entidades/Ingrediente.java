package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity // <--- 1. Transforma a classe em uma tabela no banco
public class Ingrediente {

    @Id // <--- 2. Define quem é a Chave Primária (PK)
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Descomente se quiser que o banco gere o ID sozinho
    private long id;
    
    private String descricao;

    // <--- 3. CONSTRUTOR VAZIO (OBRIGATÓRIO PARA O JPA/HIBERNATE)
    protected Ingrediente() {
    }

    // Seu construtor original (pode manter para uso no código Java)
    public Ingrediente(long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}