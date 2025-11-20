package entrega.adaptadores.mensagens;

import entrega.config.RabbitMQConfig;
import entrega.dominio.servicos.EntregaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntregaMessageListener {
    private static final Logger log = LoggerFactory.getLogger(EntregaMessageListener.class);
    private final EntregaService entregaService;

    @Autowired
    public EntregaMessageListener(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(PedidoParaEntregaDTO pedidoParaEntrega) {
        log.info("[{}] Mensagem recebida da fila de entregas: {}", Thread.currentThread().getName(), pedidoParaEntrega);
        entregaService.iniciarEntrega(pedidoParaEntrega.getPedidoId());
    }
}