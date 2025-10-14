package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroClienteService {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public CadastroClienteService(JdbcTemplate jdbcTemplate,
                             JdbcUserDetailsManager userDetailsManager,
                             PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional 
    public void run(String nome, String cpf, String celular, String endereco, String email, String senha) {
        // A verificação de e-mail existente continua aqui
        if (userDetailsManager.userExists(email)) {
            throw new IllegalArgumentException("O e-mail informado já está cadastrado.");
        }

        // 1. Cria o usuário no sistema de segurança
        userDetailsManager.createUser(
            User.withUsername(email)
                .password(passwordEncoder.encode(senha))
                .roles("CLIENTE")
                .build()
        );

        // 2. Insere os dados cadastrais na tabela 'clientes'
        jdbcTemplate.update(
            "INSERT INTO clientes(cpf, nome, celular, endereco, email) VALUES (?, ?, ?, ?, ?)",
            cpf, nome, celular, endereco, email
        );
    }
}
