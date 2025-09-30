package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Repository
public class PedidosRepositoryJDBC implements PedidosRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProdutosRepository produtosRepository;

    @Autowired
    public PedidosRepositoryJDBC(JdbcTemplate jdbcTemplate, ProdutosRepository produtosRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.produtosRepository = produtosRepository;
    }

    @Override
    public long inserePedido(String clienteCpf, String status, double valor, double impostos, double desconto, double valorCobrado) {
        String sql = "INSERT INTO pedidos (cliente_cpf, status, data_hora_pagamento, valor, impostos, desconto, valor_cobrado) VALUES (?,?,?,?,?,?,?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, clienteCpf);
            ps.setString(2, status);
            ps.setTimestamp(3, null);
            ps.setDouble(4, valor);
            ps.setDouble(5, impostos);
            ps.setDouble(6, desconto);
            ps.setDouble(7, valorCobrado);
            return ps;
        }, kh);
        return kh.getKey().longValue();
    }

    @Override
    public void insereItensPedido(long pedidoId, List<ItemPedido> itens) {
        String sql = "INSERT INTO itens_pedido (pedido_id,produto_id,quantidade) VALUES (?,?,?)";
        for (ItemPedido it : itens) {
            jdbcTemplate.update(sql, pedidoId, it.getItem().getId(), it.getQuantidade());
        }
    }

    @Override
    public Pedido recuperaPorId(long id) {
        String sql = "SELECT p.id, p.cliente_cpf, p.status, p.data_hora_pagamento, p.valor, p.impostos, p.desconto, p.valor_cobrado " +
                     "FROM pedidos p WHERE p.id = ?";
        var list = jdbcTemplate.query(sql, ps -> ps.setLong(1, id), (rs, rn) -> {
            String cpf = rs.getString("cliente_cpf");
            // so puxa op cliente
            var clienteList = jdbcTemplate.query("SELECT cpf,nome,celular,endereco,email FROM clientes WHERE cpf = ?",
                ps2 -> ps2.setString(1, cpf),
                (r2, rn2) -> new Cliente(r2.getString("cpf"), r2.getString("nome"), r2.getString("celular"), r2.getString("endereco"), r2.getString("email"))
            );
            Cliente cliente = clienteList.isEmpty() ? null : clienteList.get(0);
            LocalDateTime dh = rs.getTimestamp("data_hora_pagamento") == null ? null : rs.getTimestamp("data_hora_pagamento").toLocalDateTime();
            // e os itens
            var itens = jdbcTemplate.query("SELECT ip.produto_id, ip.quantidade FROM itens_pedido ip WHERE ip.pedido_id = ?",
                ps3 -> ps3.setLong(1, rs.getLong("id")),
                (r3, rn3) -> {
                    var prod = produtosRepository.recuperaProdutoPorid(r3.getLong("produto_id"));
                    return new ItemPedido(prod, r3.getInt("quantidade"));
                });
            Pedido.Status status = Pedido.Status.valueOf(rs.getString("status"));
            return new Pedido(rs.getLong("id"), cliente, dh, itens, status, rs.getDouble("valor"), rs.getDouble("impostos"), rs.getDouble("desconto"), rs.getDouble("valor_cobrado"));
        });
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int contaPedidosClienteEntre(String cpf, LocalDateTime de, LocalDateTime ate) {
        String sql = "SELECT count(*) FROM pedidos WHERE cliente_cpf = ? AND data_hora_pagamento BETWEEN ? AND ?";
        Timestamp t1 = Timestamp.valueOf(de);
        Timestamp t2 = Timestamp.valueOf(ate);
        Integer cnt = jdbcTemplate.queryForObject(sql, Integer.class, cpf, t1, t2);
        return cnt == null ? 0 : cnt;
    }

    @Override
    public void atualizaStatus(long id, String status) {
        jdbcTemplate.update("UPDATE pedidos SET status = ? WHERE id = ?", status, id);
    }
}