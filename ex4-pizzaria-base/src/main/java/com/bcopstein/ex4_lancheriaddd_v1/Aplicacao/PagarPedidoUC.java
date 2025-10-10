package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.stereotype.Component;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.PedidoService;

@Component
public class PagarPedidoUC {
    private final PedidoService pedidoService;
    public PagarPedidoUC(PedidoService pedidoService){ this.pedidoService = pedidoService; }

    // monopolizar servicos juntos dentro das UCs, cada servico deve lidar com a sua parte 
    // (estamos misturando pedido, pagamento e mandar pra cozinha)
    public void run(long pedidoId, String meioPagamento){
        pedidoService.pagarPedido(pedidoId, meioPagamento);
    }
}