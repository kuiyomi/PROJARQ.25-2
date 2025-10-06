package com.paulo.demo_teste1_spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component

public class AcervoMem implements IAcervo{
     private final List<Livro> livros = new ArrayList<>();

    public AcervoMem() {
        livros.add(new Livro(100, "Aprendendo Spring Boot", "Regina", 2020));
        livros.add(new Livro(120, "Aprendendo Java", "Zezinho Pato", 2018));
        livros.add(new Livro(140, "Aprendendo Outra coisa", "Luiz", 2019));
        livros.add(new Livro(150, "Aprendendo Uma coisa nova", "Hugo", 2021));
        livros.add(new Livro(160, "Aprendendo Outra coisa nova", "Maria", 2022));
    }

    public List<Livro> todos(){
        return livros;
    }

    public List<Livro> todosAutor(String autor) {
        return livros.stream()
        .filter(livro -> livro.getAutor().equals(autor))
        .toList();
    }

    public List<Livro> todosAutorAno(String autor, int ano) {
        return livros.stream()
        .filter(livro -> livro.getAutor().equals(autor))
        .filter(livro->livro.getAno()==ano)
        .toList();
    }

    public boolean cadastraLivro(Livro livro){
        return livros.add(livro);
    }

    @Override
    public boolean removeLivro(int codigo) {
        // remove todos os livros com o código informado; retorna true se ao menos um foi removido
        return livros.removeIf(livro -> livro.getId() == codigo);
    }

}
