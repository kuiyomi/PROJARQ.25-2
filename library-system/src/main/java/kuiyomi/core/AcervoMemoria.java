package kuiyomi.core;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AcervoMemoria implements IAcervo {

    private final ConcurrentMap<Long, Livro> dados = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    @Override
    public List<Livro> todos() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public List<Livro> porAutor(String autor) {
        if (autor == null || autor.isBlank()) return List.of();
        String alvo = autor.trim().toLowerCase();
        return dados.values().stream()
                .filter(l -> l.autor() != null && l.autor().toLowerCase().equals(alvo))
                .toList();
    }

    @Override
    public List<Livro> porAno(int ano) {
        return dados.values().stream()
                .filter(l -> l.ano() == ano)
                .toList();
    }

    @Override
    public Livro adicionar(String titulo, String autor, int ano) {
        long id = seq.getAndIncrement();
        Livro novo = new Livro(id, titulo, autor, ano);
        dados.put(id, novo);
        return novo;
    }

    public boolean vazio() {
        return dados.isEmpty();
    }
}