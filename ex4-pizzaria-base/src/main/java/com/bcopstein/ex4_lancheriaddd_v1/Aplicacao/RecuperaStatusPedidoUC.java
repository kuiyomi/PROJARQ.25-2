package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

@Component
public class RecuperaStatusPedidoUC {
    private final PedidosRepository pedidosRepository;

    // errado: depender do repositorio pra esse request, so vale a pena quando depender de mais de um servico
    @Autowired
    public RecuperaStatusPedidoUC(PedidosRepository pedidosRepository){
        this.pedidosRepository = pedidosRepository;
    }

    public String run(long pedidoId){
        Pedido p = pedidosRepository.recuperaPorId(pedidoId);
        return p == null ? "NAO_ENCONTRADO" : p.getStatus().name();
    }
}