package estoque.adaptadores.repositorios;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredienteJpaRepository extends JpaRepository<Ingrediente, Long> {
    Optional<Ingrediente> findById(Long id);
}