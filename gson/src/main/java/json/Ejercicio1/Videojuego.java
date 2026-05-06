package json.Ejercicio1;

public class Videojuego {
    private String titulo;
    private String desarrolladora;
    private int anioLanzamiento;

    public Videojuego(String titulo, String desarrolladora, int anioLanzamiento) {
        this.titulo = titulo;
        this.desarrolladora = desarrolladora;
        this.anioLanzamiento = anioLanzamiento;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Videojuego{");
        sb.append("titulo='").append(titulo).append('\'');
        sb.append(", desarrolladora='").append(desarrolladora).append('\'');
        sb.append(", anioLanzamiento=").append(anioLanzamiento);
        sb.append('}');
        return sb.toString();
    }
}
