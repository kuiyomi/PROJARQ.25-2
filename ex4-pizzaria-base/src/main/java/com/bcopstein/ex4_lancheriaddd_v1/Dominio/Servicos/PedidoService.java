package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.*;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.*;

@Service
public class PedidoService {
    private final ProdutosRepository produtosRepository;
    private final ReceitasRepository receitasRepository;
    private final IngredientesRepository ingredientesRepository;
    private final ItemEstoqueRepository itemEstoqueRepository;
    private final PedidosRepository pedidosRepository;
    private final ImpostoService impostoService;
    private final DescontoService descontoService;
    @Autowired
    private PagamentoService pagamentoService;
    @Autowired
    private CozinhaService cozinhaService;

    public PedidoService(ProdutosRepository produtosRepository,
            ReceitasRepository receitasRepository,
            IngredientesRepository ingredientesRepository,
            ItemEstoqueRepository itemEstoqueRepository,
            PedidosRepository pedidosRepository,
            ImpostoService impostoService,
            DescontoService descontoService) {
        this.produtosRepository = produtosRepository;
        this.receitasRepository = receitasRepository;
        this.ingredientesRepository = ingredientesRepository;
        this.itemEstoqueRepository = itemEstoqueRepository;
        this.pedidosRepository = pedidosRepository;
        this.impostoService = impostoService;
        this.descontoService = descontoService;
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

    // submete pedido para aprovação: retorna ResultadoAprovacao. Não faz pagamento.
    public ResultadoAprovacao submeteParaAprovacao(String clienteCpf, List<ItemPedido> itens) {
        // produtos indisponiveis
        List<Long> produtosIndisponiveis = new ArrayList<>();
        for (ItemPedido itemPedido : itens) {
            Produto produto = produtosRepository.recuperaProdutoPorid(itemPedido.getItem().getId());
            if (produto == null) {
                produtosIndisponiveis.add(itemPedido.getItem().getId());
            }
        }

        if (!produtosIndisponiveis.isEmpty()) {
            return new ResultadoAprovacao(false, produtosIndisponiveis, 0, 0, 0, 0, -1);
        }

        // se tiver tudo ok, calcula preço
        double soma = 0.0;
        for (ItemPedido ip : itens) {
            Produto p = produtosRepository.recuperaProdutoPorid(ip.getItem().getId());
            soma += ((double) p.getPreco()) * ip.getQuantidade();
        }
        double descontoPercent = descontoService.percentualDescontoSeElegivel(clienteCpf);
        double desconto = soma * descontoPercent;
        double base = soma - desconto;
        double impostos = impostoService.calculaImposto(base);
        double valorCobrado = base + impostos;

        // salva pedido (status APROVADO)
        long pedidoId = pedidosRepository.inserePedido(clienteCpf, Pedido.Status.APROVADO.name(), soma, impostos,
                desconto, valorCobrado);
        pedidosRepository.insereItensPedido(pedidoId, itens);

        return new ResultadoAprovacao(true, List.of(), soma, desconto, impostos, valorCobrado, pedidoId);
    }

    

}