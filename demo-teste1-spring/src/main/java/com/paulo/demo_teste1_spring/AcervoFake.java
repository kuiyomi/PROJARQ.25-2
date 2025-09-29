package com.paulo.demo_teste1_spring;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AcervoFake implements IAcervo {

    @Override
    public List<Livro> todos() {
       return List.of(new Livro(10, "Titulo", "Autor", 2020));
    }

    @Override
    public List<Livro> todosAutor(String autor) {
       return List.of(new Livro(10, "Titulo", "Autor", 2020));
    }

    @Override
    public List<Livro> todosAutorAno(String autor, int ano) {
        return List.of(new Livro(10, "Titulo", "Autor", 2020));
    }

    @Override
    public boolean cadastraLivro(Livro livro) {
        return true;
    }

    @Override
    public boolean removeLivro(int codigo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeLivro'");
    }
    
}
