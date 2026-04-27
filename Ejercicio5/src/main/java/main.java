import util.MiEntradaSalida;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class main {
    static void main(String[] args) {
        boolean run=true;
        while (run){
            String mensaje = (String) MiEntradaSalida.solicitarCadena("Que acción quiere hacer");
            switch (mensaje){
                case "lista" -> {
                    System.out.println(acciones);
                    break;
                }
                case "mkdir" -> {
                    String nombre = (String) MiEntradaSalida.solicitarCadena("¿Que nombre le quiere poner al directorio?");
                    crearDirectorio(nombre);
                }
                case "touch"->{

                }
                default -> System.out.println(acciones);
            }
        }
    }

    public static String acciones = "Bla bla bla aqui van las acciones";
    public static void crearDirectorio(String directorio){
        Path p = Paths.get("C:\\Users\\alumno\\IdeaProjects\\ficheros\\Ejercicio5",directorio);
        try {
            Files.createDirectory(p);
            System.out.println("El directorio se creará cuando se cierre la aplicación");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
