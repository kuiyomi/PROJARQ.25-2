package com.paulo.demo_teste1_spring;

import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initUsersAndBooks(DataSource dataSource, PasswordEncoder encoder) {
        return args -> {
            JdbcUserDetailsManager jdbcUsers = new JdbcUserDetailsManager(dataSource);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            // -------- Usuários --------
            if (!jdbcUsers.userExists("user")) {
                UserDetails user = User.withUsername("user")
                        .password(encoder.encode("user123"))
                        .roles("USER")
                        .build();
                jdbcUsers.createUser(user);
            }

            if (!jdbcUsers.userExists("admin")) {
                UserDetails admin = User.withUsername("admin")
                        .password(encoder.encode("admin123"))
                        .roles("ADMIN")
                        .build();
                jdbcUsers.createUser(admin);
            }

            if (!jdbcUsers.userExists("bibliotecario")) {
                UserDetails bibliotecario = User.withUsername("bibliotecario")
                        .password(encoder.encode("biblio123"))
                        .roles("BIBLIOTECARIO")
                        .build();
                jdbcUsers.createUser(bibliotecario);
            }

            // -------- Livros --------
            jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", "Dom Quixote", "Miguel de Cervantes", 1605);
            jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", "1984", "George Orwell", 1949);
            jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", "O Pequeno Príncipe", "Antoine de Saint-Exupéry", 1943);
            jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", "Cem Anos de Solidão", "Gabriel García Márquez", 1967);
            jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", "Orgulho e Preconceito", "Jane Austen", 1813);
            jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", "O Senhor dos Anéis", "J.R.R. Tolkien", 1954);
            jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", "Alice no País das Maravilhas", "Lewis Carroll", 1865);
            jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", "A Revolução dos Bichos", "George Orwell", 1945);
            jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", "O Hobbit", "J.R.R. Tolkien", 1937);
            jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", "Hamlet", "William Shakespeare", 1603);
        };
    }
}