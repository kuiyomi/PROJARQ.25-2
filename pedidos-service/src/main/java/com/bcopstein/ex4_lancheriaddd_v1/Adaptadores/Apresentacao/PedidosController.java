package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.reader.ReaderException;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmeterPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests.PedidoRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CancelarPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.PagarPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RecuperaStatusPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListarPedidosClienteUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListarPedidosEntreguesUC;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    private final SubmeterPedidoUC submeterPedidoUC;
    private final RecuperaStatusPedidoUC recuperaStatusPedidoUC;
    private final CancelarPedidoUC cancelarPedidoUC;
    private final PagarPedidoUC pagarPedidoUC;
    private final ListarPedidosEntreguesUC listarPedidosEntreguesUC;
    private final ListarPedidosClienteUC listarPedidosClienteUC;

    public PedidosController(
            SubmeterPedidoUC submeterPedidoUC,
            RecuperaStatusPedidoUC recuperaStatusPedidoUC,
            CancelarPedidoUC cancelarPedidoUC,
            PagarPedidoUC pagarPedidoUC,
            ListarPedidosEntreguesUC listarPedidosEntreguesUC,
            ListarPedidosClienteUC listarPedidosClienteUC) {

        this.submeterPedidoUC = submeterPedidoUC;
        this.recuperaStatusPedidoUC = recuperaStatusPedidoUC;
        this.cancelarPedidoUC = cancelarPedidoUC;
        this.pagarPedidoUC = pagarPedidoUC;
        this.listarPedidosEntreguesUC = listarPedidosEntreguesUC;
        this.listarPedidosClienteUC = listarPedidosClienteUC;
    }

    // ---------------------------------------------------------
    // 1) Submeter novo pedido
    // ---------------------------------------------------------
    @PostMapping("/submit")
    @CrossOrigin("*")
    public ResponseEntity<PedidoResponse> submeterPedido(
            @RequestBody PedidoRequest req,
            Authentication authentication) {

        String clienteEmail = authentication.getName();
        PedidoResponse response = submeterPedidoUC.run(req, clienteEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ---------------------------------------------------------
    // 2) Recuperar status de um pedido
    // ---------------------------------------------------------
    @GetMapping("/{id}/status")
    @CrossOrigin("*")
    public ResponseEntity<String> statusPedido(
            @PathVariable("id") long id,
            Authentication authentication) {

        String clienteEmail = authentication.getName();
        try {
            String status = recuperaStatusPedidoUC.run(id, clienteEmail);
            return ResponseEntity.ok(status);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // ---------------------------------------------------------
    // 3) Cancelar um pedido
    // ---------------------------------------------------------
    @PostMapping("/{id}/cancelar")
    @CrossOrigin("*")
    public ResponseEntity<String> cancelar(
            @PathVariable("id") long id,
            Authentication authentication) {

        String clienteEmail = authentication.getName();
        try {
            cancelarPedidoUC.run(id, clienteEmail);
            return ResponseEntity.ok("Pedido cancelado com sucesso.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (ReaderException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // ---------------------------------------------------------
    // 4) Pagar um pedido
    // ---------------------------------------------------------
    @PostMapping("/{id}/pagar")
    @CrossOrigin("*")
    public ResponseEntity<String> pagar(
            @PathVariable("id") long id,
            @RequestParam(defaultValue = "CREDITO") String meio,
            Authentication authentication) {

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

    // ---------------------------------------------------------
    // 5) Listar pedidos ENTREGUES em um intervalo de datas
    // ---------------------------------------------------------
    @GetMapping("/entregues")
    @CrossOrigin("*")
    public ResponseEntity<List<Map<String, Object>>> listarPedidosEntregues(
            @RequestParam("inicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam("fim")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<Map<String, Object>> pedidos = listarPedidosEntreguesUC.run(dataInicio, dataFim);
        return ResponseEntity.ok(pedidos);
    }

    // ---------------------------------------------------------
    // 6) Listar pedidos do cliente logado em um intervalo de datas
    // ---------------------------------------------------------
    @GetMapping("/cliente")
    @CrossOrigin("*")
    public ResponseEntity<List<Map<String, Object>>> listarPedidosCliente(
            Authentication authentication,
            @RequestParam("inicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam("fim")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        String emailCliente = authentication.getName();

        List<Map<String, Object>> pedidos =
                listarPedidosClienteUC.run(emailCliente, dataInicio, dataFim);

        return ResponseEntity.ok(pedidos);
    }
}
