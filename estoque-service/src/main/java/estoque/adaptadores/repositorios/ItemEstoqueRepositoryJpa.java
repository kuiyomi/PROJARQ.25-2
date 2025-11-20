package estoque.adaptadores.repositorios;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.ItemEstoqueRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class ItemEstoqueRepositoryJpa implements ItemEstoqueRepository {

    private final ItemEstoqueJpaRepository itemEstoqueJpaRepository;

    @Autowired
    public ItemEstoqueRepositoryJpa(ItemEstoqueJpaRepository itemEstoqueJpaRepository) {
        this.itemEstoqueJpaRepository = itemEstoqueJpaRepository;
    }

    @Override
    public ItemEstoque recuperaPorIngredienteId(long ingredienteId) {
        return itemEstoqueJpaRepository.findByIngredienteId(ingredienteId).orElse(null);
    }

    @Override
    @Transactional
    public void reduzQuantidade(long ingredienteId, int quantidade) {
        Optional<ItemEstoque> itemEstoqueOpt = itemEstoqueJpaRepository.findByIngredienteId(ingredienteId);
        if (itemEstoqueOpt.isPresent()) {
            ItemEstoque item = itemEstoqueOpt.get();
            if (item.getQuantidade() < quantidade) {
                throw new IllegalArgumentException("Quantidade insuficiente em estoque para o ingrediente ID " + ingredienteId);
            }
            item.setQuantidade(item.getQuantidade() - quantidade);
            itemEstoqueJpaRepository.save(item); 
        } else {
            throw new IllegalArgumentException("Item de estoque para ingrediente ID " + ingredienteId + " não encontrado.");
        }
    }
}