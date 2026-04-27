import util.MiEntradaSalida;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class main {
    public static String acciones = "mkdir -> Crear Directorio\n" +
            "touch -> Crear Fichero con texto\n" +
            "ls -> Listar ficheros Carpetas\n" +
            "Default -> acciones\n" +
            "acciones -> acciones\n" +
            "del -> borrar fichero o carpeta";

    static void main(String[] args) {
        boolean run = true;
        while (run) {
            String mensaje = (String) MiEntradaSalida.solicitarCadena("Que acción quiere hacer");
            switch (mensaje) {
                case "lista" -> {
                    System.out.println(acciones);
                    break;
                }
                case "mkdir" -> {
                    String nombre = MiEntradaSalida.solicitarCadena("¿Que nombre le quiere poner al directorio?");
                    crearDirectorio(nombre);
                }
                case "touch" -> {
                    String nombre =  MiEntradaSalida.solicitarCadena("¿Que nombre le quiere poner al fichero?");
                    crearFichero(nombre);
                }
                case "del" -> {
                    String nombre = MiEntradaSalida.solicitarCadena("¿Que fichero o directorio quiere borrar?");
                    borrarFicheroODirectorio(nombre);
                }
                case "ls"->{
                    String nombre = MiEntradaSalida.solicitarCadena("¿Que directorio quieres listar?");
                    listarFicherosCarpeta(nombre);
                }
                default -> System.out.println(acciones);
            }
        }
    }

    public static void crearDirectorio(String directorio) {
        Path p = Paths.get("C:\\Users\\alumno\\IdeaProjects\\ficheros\\Ejercicio5", directorio);
        try {
            Files.createDirectory(p);
            System.out.println("El directorio se creará cuando se cierre la aplicación");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void crearFichero(String fichero){
        Path p= Paths.get("C:\\Users\\alumno\\IdeaProjects\\ficheros\\Ejercicio5", fichero);
        try {
            Files.createFile(p);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        String texto = MiEntradaSalida.solicitarCadena("Introduce la  cadena de texto");


        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(p,StandardOpenOption.APPEND)) {
        bufferedWriter.write(texto);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void borrarFicheroODirectorio(String algo){

        Path p = Paths.get("C:\\Users\\alumno\\IdeaProjects\\ficheros\\Ejercicio5",algo);
        try {
            Files.deleteIfExists(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void listarFicherosCarpeta(String carpeta){
        Path p = Paths.get("C:\\",carpeta);

        try  {
            Files.list(p).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
