package com.paulo.demo_teste1_spring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
          .csrf(csrf -> csrf.disable())
          .headers(headers -> headers.frameOptions(frame -> frame.disable()))
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/h2-console/**").permitAll()
              .requestMatchers("/api/welcome", "/api/usuarios").permitAll()
              .requestMatchers("/api/livros").hasRole("BIBLIOTECARIO")
              .requestMatchers("/api/livros/**").authenticated()
              .anyRequest().denyAll()
          )
          .httpBasic(Customizer.withDefaults());

      return http.build();
  }
}