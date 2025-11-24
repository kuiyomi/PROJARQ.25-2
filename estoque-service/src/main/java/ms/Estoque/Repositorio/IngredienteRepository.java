package ms.Estoque.Repositorio;

import ms.Estoque.Entidades.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
}