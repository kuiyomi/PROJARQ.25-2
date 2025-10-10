package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

// implementar interface
@Service
public class EntregaService {
    private final PedidosRepository pedidosRepository;
    private final Queue<Long> filaEntrega = new LinkedBlockingQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public EntregaService(PedidosRepository pedidosRepository){
        this.pedidosRepository = pedidosRepository;
    }

    public void enfileirar(long pedidoId){
        filaEntrega.add(pedidoId);
        // Simula atribuição a entregador e entrega
        scheduler.schedule(() -> {
            pedidosRepository.atualizaStatus(pedidoId, Pedido.Status.TRANSPORTE.name());
            scheduler.schedule(() ->
                pedidosRepository.atualizaStatus(pedidoId, Pedido.Status.ENTREGUE.name()),
                5, TimeUnit.SECONDS);
        }, 2, TimeUnit.SECONDS);
    }
}