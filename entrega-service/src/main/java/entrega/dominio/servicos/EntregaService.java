package entrega.dominio.servicos;

import entrega.dominio.entidades.Entrega;
import entrega.dominio.repositorios.EntregaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class EntregaService {
    private static final Logger log = LoggerFactory.getLogger(EntregaService.class);
    private final EntregaRepository entregaRepository;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    public EntregaService(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }

    @Transactional
    public void iniciarEntrega(Long pedidoId) {
        Optional<Entrega> optionalEntrega = entregaRepository.findById(pedidoId);
        Entrega entrega;
        if (optionalEntrega.isPresent()) {
            entrega = optionalEntrega.get();
            log.info("Entrega existente para o pedido {}. Atualizando status para AGUARDANDO.", pedidoId);
        } else {
            entrega = new Entrega(pedidoId, Entrega.Status.AGUARDANDO);
            log.info("Nova entrega criada para o pedido {}. Status: AGUARDANDO.", pedidoId);
        }
        entrega.setStatus(Entrega.Status.AGUARDANDO);
        entregaRepository.save(entrega);

        log.info("[{}] Pedido {} aguardando entregador.", Thread.currentThread().getName(), pedidoId);

        scheduler.schedule(() -> atribuirEntregador(pedidoId), 2, TimeUnit.SECONDS);
    }

    @Transactional
    private void atribuirEntregador(Long pedidoId) {
        Optional<Entrega> optionalEntrega = entregaRepository.findById(pedidoId);
        if (optionalEntrega.isPresent()) {
            Entrega entrega = optionalEntrega.get();
            entrega.setStatus(Entrega.Status.TRANSPORTE);
            entrega.setEntregador("Entregador-X");
            entregaRepository.save(entrega);
            log.info("[{}] Pedido {} atribuído ao entregador {}. Status: TRANSPORTE.", Thread.currentThread().getName(), pedidoId, entrega.getEntregador());

            scheduler.schedule(() -> finalizarEntrega(pedidoId), 5, TimeUnit.SECONDS);
        } else {
            log.warn("[{}] Tentativa de atribuir entregador a pedido {} inexistente.", Thread.currentThread().getName(), pedidoId);
        }
    }

    @Transactional
    private void finalizarEntrega(Long pedidoId) {
        Optional<Entrega> optionalEntrega = entregaRepository.findById(pedidoId);
        if (optionalEntrega.isPresent()) {
            Entrega entrega = optionalEntrega.get();
            entrega.setStatus(Entrega.Status.ENTREGUE);
            entregaRepository.save(entrega);
            log.info("[{}] Pedido {} entregue com sucesso! Status: ENTREGUE.", Thread.currentThread().getName(), pedidoId);
        } else {
            log.warn("[{}] Tentativa de finalizar entrega de pedido {} inexistente.", Thread.currentThread().getName(), pedidoId);
        }
    }
    
    public Optional<Entrega> consultarStatusEntrega(Long pedidoId) {
        return entregaRepository.findById(pedidoId);
    }
}