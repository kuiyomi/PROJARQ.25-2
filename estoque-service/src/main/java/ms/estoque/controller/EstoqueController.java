package ms.estoque.controller;

import ms.estoque.dto.AtualizarEstoqueRequest;
import ms.estoque.dto.ItemEstoqueDTO;
import ms.estoque.dto.VerificarDisponibilidadeLoteRequest;
import ms.estoque.dto.VerificarDisponibilidadeLoteResponse;
import ms.estoque.dto.VerificarDisponibilidadeRequest;
import ms.estoque.service.EstoqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping("/status")
    public String status() {
        return "Estoque-service OK!";
    }

    @GetMapping
    public ResponseEntity<List<ItemEstoqueDTO>> listarTodos() {
        return ResponseEntity.ok(estoqueService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemEstoqueDTO> buscarPorId(@PathVariable Long id) {
        return estoqueService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ingrediente/{ingredienteId}")
    public ResponseEntity<ItemEstoqueDTO> buscarPorIngredienteId(@PathVariable Long ingredienteId) {
        return estoqueService.buscarPorIngredienteId(ingredienteId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/verificar-disponibilidade")
    public ResponseEntity<Map<String, Boolean>> verificarDisponibilidade(
            @RequestBody VerificarDisponibilidadeRequest request) {
        boolean disponivel = estoqueService.verificarDisponibilidade(
            request.getIngredienteId(),
            request.getQuantidadeNecessaria()
        );
        Map<String, Boolean> response = new HashMap<>();
        response.put("disponivel", disponivel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verificar-disponibilidade-lote")
    public ResponseEntity<VerificarDisponibilidadeLoteResponse> verificarDisponibilidadeLote(
            @RequestBody VerificarDisponibilidadeLoteRequest request) {
        return ResponseEntity.ok(estoqueService.verificarDisponibilidadeLote(request));
    }

    @PostMapping("/baixar")
    public ResponseEntity<?> baixarEstoque(@RequestBody AtualizarEstoqueRequest request) {
        try {
            ItemEstoqueDTO resultado = estoqueService.baixarEstoque(
                request.getIngredienteId(),
                request.getQuantidade()
            );
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionarEstoque(@RequestBody AtualizarEstoqueRequest request) {
        try {
            ItemEstoqueDTO resultado = estoqueService.adicionarEstoque(
                request.getIngredienteId(),
                request.getQuantidade()
            );
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarItemEstoque(@RequestBody AtualizarEstoqueRequest request) {
        try {
            ItemEstoqueDTO resultado = estoqueService.criarItemEstoque(
                request.getIngredienteId(),
                request.getQuantidade()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}