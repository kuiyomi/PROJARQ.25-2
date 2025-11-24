package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.PedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ProdutosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class SubmeterPedidoUC {
    private final PedidoService pedidoService;
    private final ProdutosRepository produtosRepository;

    @Autowired
    public SubmeterPedidoUC(PedidoService pedidoService, ProdutosRepository produtosRepository){
        this.pedidoService = pedidoService;
        this.produtosRepository = produtosRepository;
    }

    public PedidoResponse run(PedidoRequest req, String clienteCpf){
        List<ItemPedido> itens = req.itens.stream()
            .map(i -> {
                var prod = produtosRepository.recuperaProdutoPorid(i.produtoId);
                if (prod == null) {
                    throw new IllegalArgumentException("Produto inexistente: " + i.produtoId);
                }
                return new ItemPedido(
                    new Produto(prod.getId(), prod.getDescricao(), prod.getReceita(), prod.getPreco()),
                    i.quantidade
                );
            })
            .collect(Collectors.toList());

        var res = pedidoService.submeteParaAprovacao(clienteCpf, itens);

        // Agora criamos o DTO usando o construtor com campos privados/getters
        return new PedidoResponse(
            res.aprovado,
            res.produtosIndisponiveis,
            res.valor,
            res.desconto,
            res.impostos,
            res.valorCobrado,
            res.pedidoId
        );
    }
}
