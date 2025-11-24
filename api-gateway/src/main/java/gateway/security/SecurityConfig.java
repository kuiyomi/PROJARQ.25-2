package gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.web.server.ServerHttpSecurity;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder pe() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MapReactiveUserDetailsService users(PasswordEncoder pe) {
        UserDetails u = User.withUsername("cliente@teste.com")
                .password(pe.encode("1234"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(u);
    }

    @Bean
    SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex.anyExchange().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    // Propaga o usuário autenticado aos serviços downstream
    @Bean
    GlobalFilter userHeader() {
        return (exchange, chain) -> exchange.getPrincipal()
                // pega o nome do usuário autenticado
                .map(p -> p.getName())
                // se não tiver principal (não autenticado), usa "anon"
                .defaultIfEmpty("anon")
                .flatMap(username -> {
                    ServerHttpRequest req = exchange.getRequest().mutate()
                            .header("X-User", username)
                            .build();
                    return chain.filter(exchange.mutate().request(req).build());
                });
    }
}
