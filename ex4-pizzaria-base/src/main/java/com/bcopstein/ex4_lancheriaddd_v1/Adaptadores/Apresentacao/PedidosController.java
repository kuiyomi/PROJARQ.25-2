package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.web.bind.annotation.*;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmeterPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperaStatusPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.PedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {
    private final SubmeterPedidoUC submeterPedidoUC;
    private final RecuperaStatusPedidoUC recuperaStatusPedidoUC;

    public PedidosController(SubmeterPedidoUC submeterPedidoUC, RecuperaStatusPedidoUC recuperaStatusPedidoUC){
        this.submeterPedidoUC = submeterPedidoUC;
        this.recuperaStatusPedidoUC = recuperaStatusPedidoUC;
    }

    @PostMapping("/submit")
    @CrossOrigin("*")
    public PedidoResponse submeterPedido(@RequestBody PedidoRequest req){
        return submeterPedidoUC.run(req);
    }

    @GetMapping("/{id}/status")
    @CrossOrigin("*")
    public String statusPedido(@PathVariable("id") long id){
        return recuperaStatusPedidoUC.run(id);
    }
}