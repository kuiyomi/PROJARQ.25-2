package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PagamentoService;

@Component
public class PagarPedidoUC {
    private final PagamentoService pagamentoService;

    public PagarPedidoUC(PagamentoService pagamentoService){
        this.pagamentoService = pagamentoService;
    }

    public void run(long pedidoId, String meioPagamento){
        pagamentoService.pagarPedido(pedidoId, meioPagamento);
    }
}