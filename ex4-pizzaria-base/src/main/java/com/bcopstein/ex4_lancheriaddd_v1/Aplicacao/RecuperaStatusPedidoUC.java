package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

        // Se o pedido não existe, retorna null.
        if (pedido == null) {
            return null;
        }

        if (!pedido.getCliente().getEmail().equals(clienteEmail)) {
            throw new AccessDeniedException("Acesso negado.");
        }

        // Se tudo estiver certo, retorna o status.
        return pedido.getStatus().name();
    }
}