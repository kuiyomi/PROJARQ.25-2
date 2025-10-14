package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PagamentoService;

@Component
public class PagarPedidoUC {
    private final PagamentoService pagamentoService;
    private final PedidosRepository pedidosRepository; // Dependência para buscar o pedido

    @Autowired
    public PagarPedidoUC(PagamentoService pagamentoService, PedidosRepository pedidosRepository){
        this.pagamentoService = pagamentoService;
        this.pedidosRepository = pedidosRepository;
    }

    // A assinatura do método agora recebe o e-mail do cliente autenticado
    public void run(long pedidoId, String meioPagamento, String clienteEmail){
        Pedido pedido = pedidosRepository.recuperaPorId(pedidoId);

        // Verificação 1: O pedido existe?
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido inexistente: " + pedidoId);
        }

        // Verificação 2 (Segurança): O cliente logado é o dono do pedido?
        if (!pedido.getCliente().getEmail().equals(clienteEmail)) {
            throw new AccessDeniedException("Você não tem permissão para pagar este pedido.");
        }

        // Verificação 3 (Regra de Negócio): O pedido pode ser pago?
        if (pedido.getStatus() != Pedido.Status.APROVADO) {
            throw new IllegalStateException("Este pedido não pode ser pago, pois seu status é: " + pedido.getStatus());
        }

        pagamentoService.pagarPedido(pedidoId, meioPagamento);

        pedidosRepository.atualizaStatus(pedidoId, Pedido.Status.PAGO.name());
    }
}