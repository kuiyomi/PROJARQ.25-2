package Pedidos.Dados.Externo;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IngredientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;

@Repository
public class ItemEstoqueRepositoryFeign implements ItemEstoqueRepository {
  private final EstoqueFeign client;
  private final IngredientesRepository ingredientesRepo;

  public ItemEstoqueRepositoryFeign(EstoqueFeign c, IngredientesRepository ir){
    this.client = c; this.ingredientesRepo = ir;
  }

  @Override
  public ItemEstoque recuperaPorIngredienteId(long ingredienteId){
    var r = client.get(ingredienteId);
    if (!r.getStatusCode().is2xxSuccessful() || r.getBody()==null) return null;

    Ingrediente ing = ingredientesRepo.recuperaTodos().stream()
                         .filter(i -> i.getId()==ingredienteId).findFirst().orElse(null);
    return new ItemEstoque(ing, r.getBody().quantidade());
  }

  @Override
  public void reduzQuantidade(long ingredienteId, int quantidade){
    client.baixa(List.of(new EstoqueFeign.BaixaReq(ingredienteId, quantidade)));
  }
}
