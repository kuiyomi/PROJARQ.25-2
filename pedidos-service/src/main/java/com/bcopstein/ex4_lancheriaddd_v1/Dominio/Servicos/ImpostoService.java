package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import org.springframework.stereotype.Service;

@Service
public class ImpostoService {
    private static final double TAXA_IMPOSTO = 0.10;

    public double calculaImposto(double base) {
        return base * TAXA_IMPOSTO;
    }
}