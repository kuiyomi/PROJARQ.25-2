package com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Requests;

import java.util.List;

public class PedidoRequest {
    public String clienteCpf;
    public List<ItemPedidoRequest> itens;
    public String endereco;
}