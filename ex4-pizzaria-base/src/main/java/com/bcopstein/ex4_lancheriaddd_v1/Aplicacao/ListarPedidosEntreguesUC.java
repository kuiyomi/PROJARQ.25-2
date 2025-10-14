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
public class ListarPedidosEntreguesUC {
    private final PedidosRepository pedidos;

    @Autowired
    public ListarPedidosEntreguesUC(PedidosRepository pedidos) {
        this.pedidos = pedidos;
    }

    // Lógica do UC8
    public List<Map<String, Object>> run(LocalDate dataInicio, LocalDate dataFim) {
        return pedidos.findPedidosEntreguesEntreDatas(dataInicio, dataFim)
                .stream()
                .map(pedido -> {
                    // Para cada pedido, cria um Map e o preenche
                    Map<String, Object> pedidoMap = new HashMap<>();
                    pedidoMap.put("pedidoId", pedido.getId());
                    pedidoMap.put("valorCobrado", pedido.getValorCobrado());
                    pedidoMap.put("status", pedido.getStatus().name());
                    // Adicione outros campos que desejar
                    return pedidoMap;
                })
                .collect(Collectors.toList());
    }
}