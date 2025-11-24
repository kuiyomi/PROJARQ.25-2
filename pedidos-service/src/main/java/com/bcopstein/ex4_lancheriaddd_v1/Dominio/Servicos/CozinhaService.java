package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;

import Pedidos.Adaptadores.Mensagens.PedidoParaEntregaDTO;
import Pedidos.Config.RabbitMQConfig;

@Service
public class CozinhaService {
    private Queue<Pedido> filaEntrada;
    private Pedido emPreparacao;
    private Queue<Pedido> filaSaida;

    private ScheduledExecutorService scheduler;

    private final PedidosRepository pedidosRepository;
    private final RabbitTemplate rabbitTemplate;

    public CozinhaService(PedidosRepository pedidosRepository, 
                          RabbitTemplate rabbitTemplate) {
        this.pedidosRepository = pedidosRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.filaEntrada = new LinkedBlockingQueue<>();
        this.emPreparacao = null;
        this.filaSaida = new LinkedBlockingQueue<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    private synchronized void colocaEmPreparacao(Pedido pedido){
        pedido.setStatus(Pedido.Status.PREPARACAO);
        pedidosRepository.atualizaStatus(pedido.getId(), Pedido.Status.PREPARACAO.name());
        emPreparacao = pedido;
        System.out.println("Pedido em preparacao: "+pedido);
        scheduler.schedule(this::pedidoPronto, 5, TimeUnit.SECONDS);
    }

    public synchronized void chegadaDePedido(Pedido p) {
        p.setStatus(Pedido.Status.AGUARDANDO);
        pedidosRepository.atualizaStatus(p.getId(), Pedido.Status.AGUARDANDO.name());
        
        filaEntrada.add(p);
        System.out.println("Pedido na fila de entrada da cozinha: "+p);
        if (emPreparacao == null) {
            colocaEmPreparacao(filaEntrada.poll());
        }
    }

    public synchronized void pedidoPronto() {
        emPreparacao.setStatus(Pedido.Status.PRONTO);
        pedidosRepository.atualizaStatus(emPreparacao.getId(), Pedido.Status.PRONTO.name());
        filaSaida.add(emPreparacao);
        System.out.println("Pedido pronto, na fila de saída da cozinha: "+emPreparacao);

        PedidoParaEntregaDTO dto = new PedidoParaEntregaDTO(emPreparacao.getId(), emPreparacao.getCliente().getEmail());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "", dto);
        System.out.println("Mensagem de pedido pronto para entrega enviada ao RabbitMQ para o pedido: " + emPreparacao.getId());

        emPreparacao = null;
        if (!filaEntrada.isEmpty()){
            Pedido prox = filaEntrada.poll();
            scheduler.schedule(() -> colocaEmPreparacao(prox), 1, TimeUnit.SECONDS);
        }
    }
}