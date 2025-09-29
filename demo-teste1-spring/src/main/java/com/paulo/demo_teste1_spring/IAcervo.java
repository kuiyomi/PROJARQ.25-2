package com.paulo.demo_teste1_spring;

import java.util.List;

public interface IAcervo {
    List<Livro> todos();
    List<Livro> todosAutor(String autor);
    List<Livro> todosAutorAno(String autor, int ano);
    boolean cadastraLivro(Livro livro);
    boolean removeLivro(int codigo);
}
