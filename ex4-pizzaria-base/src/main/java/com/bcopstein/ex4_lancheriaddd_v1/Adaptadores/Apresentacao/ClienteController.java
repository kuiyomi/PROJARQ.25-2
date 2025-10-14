package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes") 
@CrossOrigin("*")
public class ClienteController {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public ClienteController(JdbcTemplate jdbcTemplate,
                             JdbcUserDetailsManager userDetailsManager,
                             PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/registrar")
    public String registrarCliente(@RequestParam String nome,
                                   @RequestParam String cpf,
                                   @RequestParam String celular,
                                   @RequestParam String endereco,
                                   @RequestParam String email,
                                   @RequestParam String senha) {
       
        userDetailsManager.createUser(
            User.withUsername(email)
                .password(passwordEncoder.encode(senha))
                .roles("CLIENTE") 
                .build()
        );

        jdbcTemplate.update(
            "INSERT INTO clientes(cpf, nome, celular, endereco, email) VALUES (?, ?, ?, ?, ?)",
            cpf, nome, celular, endereco, email
        );

        return "Cliente cadastrado com sucesso!";
    }

}