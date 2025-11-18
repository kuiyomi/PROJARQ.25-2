package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;

@Component
public class ListarPedidosClienteUC {
    private final PedidosRepository pedidos;

    @Autowired
    public ListarPedidosClienteUC(PedidosRepository pedidos) {
        this.pedidos = pedidos;
    }

    public List<Map<String, Object>> run(String clienteEmail, LocalDate dataInicio, LocalDate dataFim) {
        return pedidos.findPedidosEntreguesPorClienteEntreDatas(clienteEmail, dataInicio, dataFim)
                .stream()
                .map(pedido -> {
                    Map<String, Object> pedidoMap = new HashMap<>();
                    pedidoMap.put("pedidoId", pedido.getId());
                    pedidoMap.put("valorCobrado", pedido.getValorCobrado());
                    pedidoMap.put("status", pedido.getStatus().name());
                    return pedidoMap;
                })
                .collect(Collectors.toList());
    }
}