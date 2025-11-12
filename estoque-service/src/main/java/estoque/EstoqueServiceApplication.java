package estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
  scanBasePackages = { "estoque", "com.bcopstein.ex4_lancheriaddd_v1" }
)
@EnableDiscoveryClient
public class EstoqueServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(EstoqueServiceApplication.class, args);
  }
}
