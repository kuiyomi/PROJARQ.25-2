package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.web.bind.annotation.*;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmeterPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CancelarPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.PagarPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperaStatusPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.PedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {
    private final SubmeterPedidoUC submeterPedidoUC;
    private final RecuperaStatusPedidoUC recuperaStatusPedidoUC;
    private final CancelarPedidoUC cancelarPedidoUC;  
    private final PagarPedidoUC pagarPedidoUC;         

    public PedidosController(SubmeterPedidoUC submeterPedidoUC, RecuperaStatusPedidoUC recuperaStatusPedidoUC, CancelarPedidoUC cancelarPedidoUC, PagarPedidoUC pagarPedidoUC){
        this.submeterPedidoUC = submeterPedidoUC;
        this.recuperaStatusPedidoUC = recuperaStatusPedidoUC;
        this.cancelarPedidoUC = cancelarPedidoUC;
        this.pagarPedidoUC = pagarPedidoUC;  
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

    // cancelar o pedido aprovado, pedido n pago
    @PostMapping("/{id}/cancelar")
    @CrossOrigin("*")
    public void cancelar(@PathVariable("id") long id){
        cancelarPedidoUC.run(id);
    }

    // pagar pedido e enviar pra cozinha
    @PostMapping("/{id}/pagar")
    @CrossOrigin("*")
    public void pagar(@PathVariable("id") long id, @RequestParam(defaultValue = "CREDITO") String meio){
        pagarPedidoUC.run(id, meio);
    }
}