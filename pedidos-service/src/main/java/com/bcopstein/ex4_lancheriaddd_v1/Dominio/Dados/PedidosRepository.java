package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;

public interface PedidosRepository {
    long inserePedido(String clienteCpf, String status, double valor, double impostos, double desconto, double valorCobrado);
    void insereItensPedido(long pedidoId, List<ItemPedido> itens);
    Pedido recuperaPorId(long id);
    int contaPedidosClienteEntre(String cpf, LocalDateTime de, LocalDateTime ate);
    void atualizaStatus(long id, String status);
    void registraPagamento(long id, java.time.LocalDateTime dataHora);
    List<Pedido> findPedidosEntreguesPorClienteEntreDatas(String clienteEmail, LocalDate dataInicio, LocalDate dataFim);
    List<Pedido> findPedidosEntreguesEntreDatas(LocalDate dataInicio, LocalDate dataFim);

}