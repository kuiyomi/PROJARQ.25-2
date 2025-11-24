package ms.Estoque.Repositorio;

import ms.Estoque.Entidades.ItemEstoque; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; 

@Repository 
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
    Optional<ItemEstoque> findByIngredienteId(Long ingredienteId);
}