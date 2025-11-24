package estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// FORÇA O SPRING A OLHAR PARA O PACOTE ANTIGO
@EntityScan(basePackages = {
    "estoque.dominio.entidades", // Se tiver locais
    "com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades" // As entidades importadas
})
// (Opcional se os repositórios estiverem em estoque.*, mas bom garantir)
@EnableJpaRepositories(basePackages = {
    "estoque.adaptadores.repositorios"
})
public class EstoqueServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EstoqueServiceApplication.class, args);
    }
}