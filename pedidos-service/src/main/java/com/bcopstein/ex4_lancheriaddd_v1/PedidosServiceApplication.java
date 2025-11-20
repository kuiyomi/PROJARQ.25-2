package com.bcopstein.ex4_lancheriaddd_v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.bcopstein.ex4_lancheriaddd_v1.Clientes")
public class PedidosServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PedidosServiceApplication.class, args);
    }
}