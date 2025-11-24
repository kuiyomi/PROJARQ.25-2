package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses;

import java.util.List;

public class PedidoResponse {
    private final boolean aprovado;
    private final List<Long> produtosIndisponiveis;
    private final double valor;
    private final double desconto;
    private final double impostos;
    private final double valorCobrado;
    private final long pedidoId;

    public PedidoResponse(
            boolean aprovado,
            List<Long> produtosIndisponiveis,
            double valor,
            double desconto,
            double impostos,
            double valorCobrado,
            long pedidoId) {

        this.aprovado = aprovado;
        this.produtosIndisponiveis = produtosIndisponiveis;
        this.valor = valor;
        this.desconto = desconto;
        this.impostos = impostos;
        this.valorCobrado = valorCobrado;
        this.pedidoId = pedidoId;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public List<Long> getProdutosIndisponiveis() {
        return produtosIndisponiveis;
    }

    public double getValor() {
        return valor;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getImpostos() {
        return impostos;
    }

    public double getValorCobrado() {
        return valorCobrado;
    }

    public long getPedidoId() {
        return pedidoId;
    }
}
