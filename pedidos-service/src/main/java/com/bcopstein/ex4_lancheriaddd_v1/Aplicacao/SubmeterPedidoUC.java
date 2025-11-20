package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemPedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.DTO.ProdutoDTO;

@Component
public class SubmeterPedidoUC {
    private final PedidoService pedidoService;

    @Autowired
    public SubmeterPedidoUC(PedidoService pedidoService){ 
        this.pedidoService = pedidoService;
    }

    public PedidoResponse run(PedidoRequest req, String clienteCpf){
        List<ItemPedido> itens = req.itens.stream()
            .map(i -> {
                return new ItemPedido(new Produto(i.produtoId, "", null, 0), i.quantidade);
            })
            .collect(Collectors.toList());


        var res = pedidoService.submeteParaAprovacao(clienteCpf, itens);
        
        PedidoResponse r = new PedidoResponse();
        r.aprovado = res.aprovado;
        r.produtosIndisponiveis = res.produtosIndisponiveis;
        r.valor = res.valor;
        r.desconto = res.desconto;
        r.impostos = res.impostos;
        r.valorCobrado = res.valorCobrado;
        r.pedidoId = res.pedidoId;
        return r;
    }
}