package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.ItemEstoque;

public interface ItemEstoqueRepository {
    ItemEstoque recuperaPorIngredienteId(long ingredienteId);
    void reduzQuantidade(long ingredienteId, int quantidade);
}