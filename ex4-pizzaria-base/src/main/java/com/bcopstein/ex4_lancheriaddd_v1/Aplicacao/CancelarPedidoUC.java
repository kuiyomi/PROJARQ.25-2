package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class CancelarPedidoUC {
    private final PedidosRepository pedidos;

    public CancelarPedidoUC(PedidosRepository pedidos) {
        this.pedidos = pedidos;
    }

    public void run(long pedidoId){
        var p = pedidos.recuperaPorId(pedidoId);
        if (p == null) throw new IllegalArgumentException("Pedido inexistente");
        if (p.getStatus() != Pedido.Status.APROVADO)
            throw new IllegalStateException("Somente pedidos APROVADOS podem ser cancelados");
        pedidos.atualizaStatus(pedidoId, Pedido.Status.CANCELADO.name());
    }
}
