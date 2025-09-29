package com.paulo.demo_teste1_spring;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class Controller {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public Controller(JdbcTemplate jdbcTemplate, 
                      JdbcUserDetailsManager userDetailsManager,
                      PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    // (T) Mensagem de boas-vindas
    @GetMapping("")
    public String welcomeMessage() {
        return "Bem-vindo à biblioteca central!";
    }

    // (A) Consultar todos os livros
    @GetMapping("/livros")
    public List<Livro> getListaLivros(Authentication authentication) {
        System.out.println("Usuário logado: " + authentication.getName());
        return this.jdbcTemplate.query(
            "SELECT * FROM livros",
            (rs, rowNum) -> new Livro(
                rs.getInt("codigo"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getInt("ano")
            )
        );
    }

    // (A) Consultar livros por autor
    @GetMapping("/livros/autor")
    public List<Livro> getListaLivrosDoAutor(@RequestParam("autor") String autor,
                                             Authentication authentication) {
        System.out.println("Usuário logado: " + authentication.getName());
        return this.jdbcTemplate.query(
            "SELECT * FROM livros WHERE autor = ?",
            (rs, rowNum) -> new Livro(
                rs.getInt("codigo"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getInt("ano")
            ),
            autor.trim()
        );
    }

    // (A) Consultar livros por ano
    @GetMapping("/livros/ano")
    public List<Livro> getListaLivrosDoAno(@RequestParam("ano") int ano,
                                           Authentication authentication) {
        System.out.println("Usuário logado: " + authentication.getName());
        return this.jdbcTemplate.query(
            "SELECT * FROM livros WHERE ano = ?",
            (rs, rowNum) -> new Livro(
                rs.getInt("codigo"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getInt("ano")
            ),
            ano
        );
    }

    // (B) Cadastrar novo livro
    @PostMapping("/livros")
    public boolean insereLivro(@RequestBody Livro livro,
                               Authentication authentication) {
        System.out.println("Usuário bibliotecário: " + authentication.getName());
        this.jdbcTemplate.update(
            "INSERT INTO livros(codigo,titulo,autor,ano) VALUES (?,?,?,?)",
            livro.getId(),
            livro.getTitulo(),
            livro.getAutor(),
            livro.getAno()
        );
        return true;
    }

    // (T) Cadastrar novo usuário
    @PostMapping("/usuarios")
    public String insereUsuario(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String role) {
        userDetailsManager.createUser(
            User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .roles(role)
                .build()
        );
        return "Usuário criado!";
}
}