package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.CozinhaService; // ajuste conforme seu package

@Service
public class PagamentoService {

    private final PedidosRepository pedidosRepository;
    private final CozinhaService cozinhaService;

    @Autowired
    public PagamentoService(PedidosRepository pedidosRepository, CozinhaService cozinhaService) {
        this.pedidosRepository = pedidosRepository;
        this.cozinhaService = cozinhaService;
    }

    public boolean processarPagamento(String meio, double valor){
        // fake: sempre vai aprovar
        return true;
    }

    public void pagarPedido(long pedidoId, String meioPagamento) {
        var p = pedidosRepository.recuperaPorId(pedidoId);
        if (p == null)
            throw new IllegalArgumentException("Pedido inexistente");
        if (p.getStatus() != Pedido.Status.APROVADO)
            throw new IllegalStateException("Apenas pedidos APROVADOS podem ser pagos");

        boolean ok = processarPagamento(meioPagamento, p.getValorCobrado());
        if (!ok)
            throw new IllegalStateException("Pagamento recusado");

        pedidosRepository.atualizaStatus(pedidoId, Pedido.Status.PAGO.name());

        p.setStatus(Pedido.Status.AGUARDANDO);
        pedidosRepository.atualizaStatus(pedidoId, Pedido.Status.AGUARDANDO.name());
        cozinhaService.chegadaDePedido(p);
    }
}