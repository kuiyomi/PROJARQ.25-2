package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

// Adicione as importações para ResponseEntity e outros pacotes necessários
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.reader.ReaderException;

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
    public ResponseEntity<PedidoResponse> submeterPedido(@RequestBody PedidoRequest req, Authentication authentication){
        String clienteEmail = authentication.getName();
        PedidoResponse response = submeterPedidoUC.run(req, clienteEmail);
        // Retorna 201 Created, que é o status correto para criação de um novo recurso.
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/status")
    @CrossOrigin("*")
    public ResponseEntity<String> statusPedido(@PathVariable("id") long id, Authentication authentication){
        String clienteEmail = authentication.getName();
        try {
            String status = recuperaStatusPedidoUC.run(id, clienteEmail);
            return ResponseEntity.ok(status);
        } catch (AccessDeniedException e) {
            // Retorna 403 Forbidden se o usuário não for o dono do pedido
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/cancelar")
    @CrossOrigin("*")
    public ResponseEntity<String> cancelar(@PathVariable("id") long id, Authentication authentication){
        String clienteEmail = authentication.getName();
        try {
            cancelarPedidoUC.run(id, clienteEmail);
            return ResponseEntity.ok("Pedido cancelado com sucesso.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ReaderException e) {
            // Retorna 409 Conflict se uma regra de negócio impedir o cancelamento
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/pagar")
    @CrossOrigin("*")
    public ResponseEntity<String> pagar(@PathVariable("id") long id, @RequestParam(defaultValue = "CREDITO") String meio, Authentication authentication){
        String clienteEmail = authentication.getName();
        try {
            pagarPedidoUC.run(id, meio, clienteEmail);
            return ResponseEntity.ok("Pagamento efetuado. Pedido enviado para a cozinha.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ReaderException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}