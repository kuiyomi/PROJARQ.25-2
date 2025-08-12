package kuiyomi;

public class Personagem {
    private int visibilidade;
    private int poder;
    private int vidas;

    private Personagem(int visibilidade, int poder, int vidas) {
        this.visibilidade = visibilidade;
        this.poder = poder;
        this.vidas = vidas;
    }

    public static class Builder {
        private int visibilidade = 10;
        private int poder = 50;
        private int vidas = 3;

        public Builder visibilidade(int visibilidade) {
            this.visibilidade = visibilidade;
            return this;
        }

        public Builder poder(int poder) {
            this.poder = poder;
            return this;
        }

        public Builder vidas(int vidas) {
            this.vidas = vidas;
            return this;
        }

        public Personagem build() {
            return new Personagem(visibilidade, poder, vidas);
        }
    }

    public static Personagem criarNormal() {
        return new Builder().build();
    }

    public static Personagem criarPoderoso(int poder) {
        return new Builder().poder(poder).build();
    }

    public static Personagem criarSoturno(int visibilidade) {
        return new Builder().visibilidade(visibilidade).build();
    }

    public static Personagem criarCustomizado(int visibilidade, int poder, int vidas) {
        return new Builder().visibilidade(visibilidade).poder(poder).vidas(vidas).build();
    }

    @Override
    public String toString() {
        return "Personagem { \n" +
                "Visibilidade: \n" + visibilidade +
                "Poder: \n" + poder +
                "Vidas: \n" + vidas +
                '}';
    }
}