package Pedidos.Dados.Externo;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "estoque-service")
public interface EstoqueFeign {
  @GetMapping("/estoque/ingrediente/{id}")
  ResponseEntity<ItemEstoqueDTO> get(@PathVariable("id") long id);

  @PostMapping("/estoque/baixa")
  ResponseEntity<Void> baixa(@RequestBody List<BaixaReq> reqs);

  record ItemEstoqueDTO(long ingredienteId, int quantidade){}
  record BaixaReq(long ingredienteId, int quantidade){}
}
