package kuiyomi;

public class App {
    public static void main(String[] args) {
        // 1) Esportivo: gasolina, 6 Km/l, tanque 45L
        Carro esportivo = new Carro("Esportivo", TipoCombustivel.GASOLINA, 6, 45);
        esportivo.abastece(TipoCombustivel.GASOLINA, 45);
        esportivo.viaja(120); // exemplo de viagem
        System.out.println(esportivo);

        // 2) Utilitario: diesel, 5 Km/l, tanque 70L
        Carro utilitario = new Carro("Utilitario", TipoCombustivel.DIESEL, 5, 70);
        utilitario.abastece(TipoCombustivel.DIESEL, 70);
        utilitario.viaja(100);
        System.out.println(utilitario);

        // 3) SUV: motor gasolina (8 Km/l), tanque FLEX 55L
        Carro suv = new Carro("SUV", TipoCombustivel.GASOLINA, 8, 55, TipoCombustivel.FLEX);
        suv.abastece(TipoCombustivel.ALCOOL, 30); // pode abastecer com álcool ou gasolina
        suv.abastece(TipoCombustivel.GASOLINA, 25);
        suv.viaja(200);
        System.out.println(suv);

        // 4) SUVFlex: motor FLEX (8 Km/l gasolina, 6 Km/l álcool), tanque FLEX 65L
        Carro suvFlex = new CarroFlex("SUVFlex", 8, 6, 65);
        suvFlex.abastece(TipoCombustivel.ALCOOL, 65); // abastecendo com álcool
        suvFlex.viaja(120);
        System.out.println(suvFlex);

        // 5) Econo: gasolina, tanque 55L, consumo inicial 20 Km/l, reduz 1 Km/l a cada
        // 5000 Km até 10 Km/l
        Carro econo = new CarroEco("Econo", 20, 55);
        econo.abastece(TipoCombustivel.GASOLINA, 55);
        econo.viaja(5000); // consumo reduz para 19
        econo.viaja(5000); // consumo reduz para 18
        econo.viaja(30000); // consumo reduz até 10
        System.out.println(econo);
    }
}