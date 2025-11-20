package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.*;

import com.bcopstein.ex4_lancheriaddd_v1.Clientes.EstoqueClient; 
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.DTO.ProdutoDTO; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.*;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.*;

@Service
public class PedidoService {
    private final ReceitasRepository receitasRepository;

    private final PedidosRepository pedidosRepository;
    private final ImpostoService impostoService;
    private final DescontoService descontoService;

    @Autowired
    private PagamentoService pagamentoService;
    @Autowired
    private CozinhaService cozinhaService;

    private final EstoqueClient estoqueClient; 
    public PedidoService(
            ReceitasRepository receitasRepository,
            PedidosRepository pedidosRepository,
            ImpostoService impostoService,
            DescontoService descontoService,
            EstoqueClient estoqueClient) {
        this.receitasRepository = receitasRepository;
        this.pedidosRepository = pedidosRepository;
        this.impostoService = impostoService;
        this.descontoService = descontoService;
        this.estoqueClient = estoqueClient;
    }

    public static class ResultadoAprovacao {
        public final boolean aprovado;
        public final List<Long> produtosIndisponiveis;
        public final double valor;
        public final double desconto;
        public final double impostos;
        public final double valorCobrado;
        public final long pedidoId;

        public ResultadoAprovacao(boolean aprovado, List<Long> produtosIndisponiveis, double valor, double desconto,
                double impostos, double valorCobrado, long pedidoId) {
            this.aprovado = aprovado;
            this.produtosIndisponiveis = produtosIndisponiveis;
            this.valor = valor;
            this.desconto = desconto;
            this.impostos = impostos;
            this.valorCobrado = valorCobrado;
            this.pedidoId = pedidoId;
        }
    }

    public ResultadoAprovacao submeteParaAprovacao(String clienteCpf, List<ItemPedido> itens) {
        List<Long> produtosIndisponiveis = new ArrayList<>();
        Map<Long, ProdutoDTO> produtosNoEstoque = new HashMap<>();

        for (ItemPedido itemPedido : itens) {
            Optional<ProdutoDTO> produtoOpt = estoqueClient.getProdutoById(itemPedido.getItem().getId());
            if (produtoOpt.isEmpty()) {
                produtosIndisponiveis.add(itemPedido.getItem().getId());
            } else {
                produtosNoEstoque.put(itemPedido.getItem().getId(), produtoOpt.get());
            }
        }

        if (!produtosIndisponiveis.isEmpty()) {
            return new ResultadoAprovacao(false, produtosIndisponiveis, 0, 0, 0, 0, -1);
        }

        double soma = 0.0;
        for (ItemPedido ip : itens) {
            ProdutoDTO p = produtosNoEstoque.get(ip.getItem().getId());
            soma += ((double) p.getPreco()) * ip.getQuantidade();
        }
        double descontoPercent = descontoService.percentualDescontoSeElegivel(clienteCpf);
        double desconto = soma * descontoPercent;
        double base = soma - desconto;
        double impostos = impostoService.calculaImposto(base);
        double valorCobrado = base + impostos;

        long pedidoId = pedidosRepository.inserePedido(clienteCpf, Pedido.Status.APROVADO.name(), soma, impostos,
                desconto, valorCobrado);
        pedidosRepository.insereItensPedido(pedidoId, itens);

        return new ResultadoAprovacao(true, List.of(), soma, desconto, impostos, valorCobrado, pedidoId);
    }
}