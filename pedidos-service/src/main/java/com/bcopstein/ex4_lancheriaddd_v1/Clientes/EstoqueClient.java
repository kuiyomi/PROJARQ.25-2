package com.bcopstein.ex4_lancheriaddd_v1.Clientes;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.DTO.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "estoque-service")
public interface EstoqueClient {

    @GetMapping("/produtos/{id}")
    Optional<ProdutoDTO> getProdutoById(@PathVariable("id") Long id);
}