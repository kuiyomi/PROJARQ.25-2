package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class CancelarPedidoUC {
    private final PedidosRepository pedidos;

    @Autowired
    public CancelarPedidoUC(PedidosRepository pedidos) {
        this.pedidos = pedidos;
    }

    // A assinatura do método agora recebe o e-mail do cliente autenticado
    public void run(long pedidoId, String clienteEmail){
        Pedido pedido = pedidos.recuperaPorId(pedidoId);

        // Verificação 1: O pedido existe?
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido inexistente: " + pedidoId);
        }

        if (!pedido.getCliente().getEmail().equals(clienteEmail)) {
            throw new AccessDeniedException("Você não tem permissão para cancelar este pedido.");
        }

        if (pedido.getStatus() != Pedido.Status.APROVADO) {
            throw new IllegalStateException("Somente pedidos com status 'APROVADO' podem ser cancelados.");
        }

        // Se todas as verificações passarem, atualiza o status
        pedidos.atualizaStatus(pedidoId, Pedido.Status.CANCELADO.name());
    }
}