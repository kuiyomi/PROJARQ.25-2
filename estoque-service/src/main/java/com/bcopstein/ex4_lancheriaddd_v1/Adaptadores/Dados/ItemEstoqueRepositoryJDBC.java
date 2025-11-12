ItemEstoqueRepositoryJDBC.java:
package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;

@Repository
public class ItemEstoqueRepositoryJDBC implements ItemEstoqueRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemEstoqueRepositoryJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ItemEstoque recuperaPorIngredienteId(long ingredienteId) {
        String sql = "SELECT ie.id, ie.quantidade, ie.ingrediente_id, i.descricao " +
                     "FROM itensEstoque ie JOIN ingredientes i ON ie.ingrediente_id = i.id WHERE ie.ingrediente_id = ?";
        var list = jdbcTemplate.query(sql, ps -> ps.setLong(1, ingredienteId),
            (rs, rn) -> {
                Ingrediente ing = new Ingrediente(rs.getLong("ingrediente_id"), rs.getString("descricao"));
                return new ItemEstoque(ing, rs.getInt("quantidade"));
            });
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void reduzQuantidade(long ingredienteId, int quantidade) {
        String sql = "UPDATE itensEstoque SET quantidade = quantidade - ? WHERE ingrediente_id = ?";
        jdbcTemplate.update(sql, quantidade, ingredienteId);
    }
}