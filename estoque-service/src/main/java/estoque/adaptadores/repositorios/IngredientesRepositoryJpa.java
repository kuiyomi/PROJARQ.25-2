package estoque.adaptadores.repositorios;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.IngredientesRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Ingrediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientesRepositoryJpa implements IngredientesRepository {

    private final IngredienteJpaRepository ingredienteJpaRepository;

    @Autowired
    public IngredientesRepositoryJpa(IngredienteJpaRepository ingredienteJpaRepository) {
        this.ingredienteJpaRepository = ingredienteJpaRepository;
    }

    @Override
    public List<Ingrediente> recuperaTodos() {
        return ingredienteJpaRepository.findAll();
    }

    @Override
    public List<Ingrediente> recuperaIngredientesReceita(long id) {
        return List.of(); 
    }
}