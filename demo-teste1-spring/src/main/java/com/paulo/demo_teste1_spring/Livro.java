package com.paulo.demo_teste1_spring;

import java.util.Objects;

public class Livro {
    
    private int id;
    private String titulo;
    private String autor;
    private int ano;

    // Construtor sem argumentos
    public Livro() {
    }

    // Construtor completo
    public Livro(int id, String titulo, String autor, int ano) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livro)) return false;
        Livro livro = (Livro) o;
        return id == livro.id &&
               ano == livro.ano &&
               Objects.equals(titulo, livro.titulo) &&
               Objects.equals(autor, livro.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, autor, ano);
    }

    @Override
    public String toString() {
        return "Livro{" +
               "id=" + id +
               ", titulo='" + titulo + '\'' +
               ", autor='" + autor + '\'' +
               ", ano=" + ano +
               '}';
    }
    
}
