package pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
  scanBasePackages = {
    "pedidos",
    "com.bcopstein.ex4_lancheriaddd_v1"  // escaneia as classes copiadas do monólito
  }
)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "pedidos.dados.externo")
public class PedidosServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(PedidosServiceApplication.class, args);
  }
}
