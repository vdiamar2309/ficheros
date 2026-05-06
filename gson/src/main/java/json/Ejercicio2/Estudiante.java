package json.Ejercicio2;

public class Estudiante {
    private String nombre;
    private String curso;
    private Double notaMedia;

    public Estudiante(String nombre, String curso, Double notaMedia) {
        this.nombre = nombre;
        this.curso = curso;
        this.notaMedia = notaMedia;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Estudiante{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", curso='").append(curso).append('\'');
        sb.append(", notaMedia=").append(notaMedia);
        sb.append('}');
        return sb.toString();
    }
}
