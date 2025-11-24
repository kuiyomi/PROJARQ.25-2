package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

import java.util.List;

public class PedidoResponse {
    public boolean aprovado;
    public List<Long> produtosIndisponiveis;
    public double valor;
    public double desconto;
    public double impostos;
    public double valorCobrado;
    public long pedidoId;

    // nao precisa de construtor ne ???
}
