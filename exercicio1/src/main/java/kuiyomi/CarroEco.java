package kuiyomi;

public class CarroEco extends Carro {
    private int consumoAtual;
    private int quilometragemTotal;

    public CarroEco(String modelo, int consumoInicial, int capacidadeTanque) {
        super(modelo, TipoCombustivel.GASOLINA, consumoInicial, capacidadeTanque);
        this.consumoAtual = consumoInicial;
        this.quilometragemTotal = 0;
    }

    @Override
    public boolean viaja(int distancia) {
        int combustivelNecessario = distancia / consumoAtual;
        if (getCombustivelDisponivel() >= combustivelNecessario) {
            quilometragemTotal += distancia;
            super.motor.percorre(distancia);
            super.tanque.gasta(combustivelNecessario);
            // Reduz consumo a cada 5000 km, mínimo 10 Km/l
            int reducoes = quilometragemTotal / 5000;
            consumoAtual = Math.max(20 - reducoes, 10);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "CarroEco:\n  Modelo=" + getModelo() + "\n  ConsumoAtual=" + consumoAtual +
                " Km/l, QuilometragemTotal=" + quilometragemTotal + "\n  Tanque=" + super.tanque;
    }
}