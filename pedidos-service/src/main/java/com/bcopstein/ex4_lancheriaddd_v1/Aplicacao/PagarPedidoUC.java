package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PagamentoService;

@Component
public class PagarPedidoUC {
    private final PagamentoService pagamentoService;
    private final PedidosRepository pedidosRepository;

    @Autowired
    public PagarPedidoUC(PagamentoService pagamentoService, PedidosRepository pedidosRepository){
        this.pagamentoService = pagamentoService;
        this.pedidosRepository = pedidosRepository;
    }

    public void run(long pedidoId, String meioPagamento, String clienteEmail){
        Pedido pedido = pedidosRepository.recuperaPorId(pedidoId);

        if (pedido == null) {
            throw new IllegalArgumentException("Pedido inexistente: " + pedidoId);
        }

        if (!pedido.getCliente().getEmail().equals(clienteEmail)) {
            throw new IllegalStateException("Você não tem permissão para pagar este pedido.");
        }

        if (pedido.getStatus() != Pedido.Status.APROVADO) {
            throw new IllegalStateException("Este pedido não pode ser pago, pois seu status é: " + pedido.getStatus());
        }

        pagamentoService.pagarPedido(pedidoId, meioPagamento);

    }
}