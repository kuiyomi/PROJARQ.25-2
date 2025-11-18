package estoque;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueRepository;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {
  private final ItemEstoqueRepository repo;

  public EstoqueController(ItemEstoqueRepository repo){ this.repo = repo; }

  @GetMapping
  public String home() {
      return "Estoque-service OK";
  }
  
  @GetMapping("/ingrediente/{id}")
  public ResponseEntity<ItemDTO> get(@PathVariable long id){
    var it = repo.recuperaPorIngredienteId(id);
    if (it==null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(new ItemDTO(id, it.getQuantidade()));
  }

  @PostMapping("/baixa")
  public ResponseEntity<Void> baixa(@RequestBody List<BaixaReq> reqs){
    reqs.forEach(r -> repo.reduzQuantidade(r.ingredienteId(), r.quantidade()));
    return ResponseEntity.noContent().build();
  }

  public record ItemDTO(long ingredienteId, int quantidade){}
  public record BaixaReq(long ingredienteId, int quantidade){}
}
