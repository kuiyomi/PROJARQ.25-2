package kuiyomi.security;

import kuiyomi.core.AcervoMemoria;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    ApplicationRunner carregarDados(AcervoMemoria acervo) {
        return args -> {
            if (acervo.vazio()) {
                acervo.adicionar("Livro 1", "Autor 1", 2025);
                acervo.adicionar("Livro 2", "Autor 2", 2024);
                acervo.adicionar("Livro 3", "Autor 1", 2023);
            }
        };
    }
}