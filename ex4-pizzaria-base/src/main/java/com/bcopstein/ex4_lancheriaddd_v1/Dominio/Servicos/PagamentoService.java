package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    public boolean processarPagamento(String meio, double valor){
        // fake: sempre vai aprovar
        return true;
    }
}