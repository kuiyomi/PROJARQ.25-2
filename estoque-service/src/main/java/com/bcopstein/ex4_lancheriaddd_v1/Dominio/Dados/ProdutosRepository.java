package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface ProdutosRepository extends JpaRepository<Produto, Long> {
    default Produto recuperaProdutoPorid(long id) {
        return findById(id).orElse(null);
    }
}