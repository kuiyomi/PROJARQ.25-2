package kuiyomi;

public class CarroFlex extends Carro {
    private int consumoGasolina;
    private int consumoAlcool;
    private TipoCombustivel ultimoCombustivel;

    public CarroFlex(String modelo, int consumoGasolina, int consumoAlcool, int capacidadeTanque) {
        super(modelo, TipoCombustivel.FLEX, consumoGasolina, capacidadeTanque, TipoCombustivel.FLEX);
        this.consumoGasolina = consumoGasolina;
        this.consumoAlcool = consumoAlcool;
        this.ultimoCombustivel = TipoCombustivel.GASOLINA;
    }

    @Override
    public int abastece(TipoCombustivel tipoCombustivel, int quantidade) {
        if (tipoCombustivel == TipoCombustivel.GASOLINA || tipoCombustivel == TipoCombustivel.ALCOOL) {
            ultimoCombustivel = tipoCombustivel;
        }
        return super.abastece(tipoCombustivel, quantidade);
    }

    @Override
    public boolean viaja(int distancia) {
        int consumo = (ultimoCombustivel == TipoCombustivel.ALCOOL) ? consumoAlcool : consumoGasolina;
        int combustivelNecessario = distancia / consumo;
        if (getCombustivelDisponivel() >= combustivelNecessario) {
            super.motor.percorre(distancia);
            super.tanque.gasta(combustivelNecessario);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "CarroFlex:\n  Modelo=" + getModelo() + "\n  Motor(FLEX) [consumoGasolina=" + consumoGasolina +
                ", consumoAlcool=" + consumoAlcool + ", ultimoCombustivel=" + ultimoCombustivel + "]\n  Tanque="
                + super.tanque;
    }
}