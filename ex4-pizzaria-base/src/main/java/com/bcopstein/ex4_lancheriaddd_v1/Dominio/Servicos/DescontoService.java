package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidosRepository;

@Service
public class DescontoService {
    private final PedidosRepository pedidosRepository;
    private static final double TAXA_DESCONTO = 0.07;

    @Autowired
    public DescontoService(PedidosRepository pedidosRepository){
        this.pedidosRepository = pedidosRepository;
    }

    public double percentualDescontoSeElegivel(String clienteCpf) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicio = agora.minusDays(20);
        LocalDateTime fim = agora;
        int cnt = pedidosRepository.contaPedidosClienteEntre(clienteCpf, inicio, fim);
        return cnt > 3 ? TAXA_DESCONTO : 0.0;
    }
}