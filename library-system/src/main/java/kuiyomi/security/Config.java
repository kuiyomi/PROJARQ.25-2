package kuiyomi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Config {

    @Bean
    public InMemoryUserDetailsManager userDetails(PasswordEncoder encoder) {
        UserDetails user = User.withUsername("usuario")
                .password(encoder.encode("usuario123"))
                .roles("USER")
                .build();
        UserDetails biblio = User.withUsername("bibliotecario")
                .password(encoder.encode("bibliotecario123"))
                .roles("BIBLIO")
                .build();
        return new InMemoryUserDetailsManager(user, biblio);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain http(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api", "/api/usuarios").permitAll()              
                .requestMatchers(HttpMethod.POST, "/api/livros").hasRole("BIBLIO") 
                .requestMatchers("/api/livros/**").authenticated()                 
                .anyRequest().denyAll()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}