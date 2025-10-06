package kuiyomi.core;

import java.util.List;

public interface IAcervo {
    List<Livro> todos();
    List<Livro> porAutor(String autor);
    List<Livro> porAno(int ano);
    Livro adicionar(String titulo, String autor, int ano);
}