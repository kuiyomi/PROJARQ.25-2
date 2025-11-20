package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class RecuperaStatusPedidoUC {
    private final PedidosRepository pedidosRepository;

    @Autowired
    public RecuperaStatusPedidoUC(PedidosRepository pedidosRepository){
        this.pedidosRepository = pedidosRepository;
    }

    public String run(long pedidoId, String clienteEmail){
        Pedido pedido = pedidosRepository.recuperaPorId(pedidoId);

        if (pedido == null) {
            throw new IllegalArgumentException("Pedido inexistente: " + pedidoId);
        }

        if (!pedido.getCliente().getEmail().equals(clienteEmail)) {
            throw new IllegalStateException("Acesso negado. O pedido não pertence a este cliente.");
        }

        return pedido.getStatus().name();
    }
}