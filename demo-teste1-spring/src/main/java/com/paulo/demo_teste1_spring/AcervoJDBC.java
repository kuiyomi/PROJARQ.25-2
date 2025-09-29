package com.paulo.demo_teste1_spring;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class AcervoJDBC implements IAcervo {
    private JdbcTemplate jdbcTemplate;

    public AcervoJDBC(JdbcTemplate jdbcTemplate) { 
        this.jdbcTemplate = jdbcTemplate; 
    }

    @Override
    public List<Livro> todos() {
        List<Livro> resp = this.jdbcTemplate.query("SELECT * from livros",
            (rs, rowNum) ->
                new Livro(rs.getInt("codigo"),
                          rs.getString("titulo"),
                          rs.getString("autor"),
                          rs.getInt("ano")));
        return resp;
    }

    @Override
    public boolean removeLivro(int codigo) {
        this.jdbcTemplate.update("DELETE FROM livros WHERE codigo = ?", codigo);
        return true;
    }

    @Override
    public boolean cadastraLivro(Livro livro) {
        this.jdbcTemplate.update(
            "INSERT INTO livros(codigo,titulo,autor,ano) VALUES (?,?,?,?)",
            livro.getId(),
            livro.getTitulo(),
            livro.getAutor(),
            livro.getAno());
        return true;
    }


    @Override
    public List<Livro> todosAutor(String autor) {
        List<Livro> resp = this.jdbcTemplate.query(
            "SELECT * FROM livros WHERE autor = ?",
            (rs, rowNum) ->
                new Livro(rs.getInt("codigo"),
                          rs.getString("titulo"),
                          rs.getString("autor"),
                          rs.getInt("ano")),
            autor);
        return resp;
    }

    @Override
    public List<Livro> todosAutorAno(String autor, int ano) {
        List<Livro> resp = this.jdbcTemplate.query(
            "SELECT * FROM livros WHERE autor = ? AND ano = ?",
            (rs, rowNum) ->
                new Livro(rs.getInt("codigo"),
                          rs.getString("titulo"),
                          rs.getString("autor"),
                          rs.getInt("ano")),
            autor, ano);
        return resp;
    }

}