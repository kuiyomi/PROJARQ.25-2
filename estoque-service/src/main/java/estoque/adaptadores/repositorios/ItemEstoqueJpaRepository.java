package estoque.adaptadores.repositorios;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemEstoqueJpaRepository extends JpaRepository<ItemEstoque, Long> {
    Optional<ItemEstoque> findByIngredienteId(Long ingredienteId);
}