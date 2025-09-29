package kuiyomi.api;

import kuiyomi.core.IAcervo;
import kuiyomi.core.Livro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class Controller {

    private final IAcervo acervo;
    private final UserDetailsManager users;
    private final PasswordEncoder encoder;

    public Controller(IAcervo acervo,
                      UserDetailsManager users,
                      PasswordEncoder encoder) {
        this.acervo = acervo;
        this.users = users;
        this.encoder = encoder;
    }

    @GetMapping
    public String boasVindas() {
        return "Bem-vindo(a) ao sistema de biblioteca!";
    }

    @GetMapping("/livros")
    public List<Livro> listar() {
        return acervo.todos();
    }

    @GetMapping("/livros/autor")
    public List<Livro> porAutor(@RequestParam String nome) {
        return acervo.porAutor(nome);
    }

    @GetMapping("/livros/ano/{ano}")
    public List<Livro> porAno(@PathVariable int ano) {
        return acervo.porAno(ano);
    }

    public record NovoLivroRequest(String titulo, String autor, int ano) {}
    public record NovoUsuarioRequest(String username, String password, String role) {}

    @PostMapping("/livros")
    public ResponseEntity<Livro> novo(@RequestBody NovoLivroRequest req) {
        Livro salvo = acervo.adicionar(req.titulo(), req.autor(), req.ano());
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<String> registrar(@RequestBody NovoUsuarioRequest req) {
        if (users.userExists(req.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe");
        }
        String role = (req.role() == null || req.role().isBlank()) ? "USER" : req.role().toUpperCase();
        users.createUser(org.springframework.security.core.userdetails.User
                .withUsername(req.username())
                .password(encoder.encode(req.password()))
                .roles(role)
                .build());
        return ResponseEntity.ok("Criado com ROLE_" + role);
    }
}