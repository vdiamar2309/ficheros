import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class main {
    public static String ayuda = "1. Listar directorio\n" +
            "2. Listar directorio y buscar ficheros que comiencen por una palabra\n" +
            "3. Listar archivos con cierta extensión de un directorio\n" +
            "4. Buscar un archivo en un directorio\n" +
            "5. Buscar recursivamente un archivo en un directorio\n" +
            "6. Salir";

    static void main() {

        boolean corriendo = true;


        while (corriendo) {

            String comando = util.MiEntradaSalida.solicitarCadena("Que acción quiere hacer?");
            switch (comando) {
                default -> {
                    System.out.println(ayuda);
                }
                case "listar" ->{
                    String ruta = util.MiEntradaSalida.solicitarCadena("Introduce la ruta del directorio que quiere listar");

                }
            }
        }
    }


    public static void listar(String ruta){
        Path p = Paths.get("C:\\",ruta);
        try {
            List<Path> ubicaciones = Files.list(p).toList();



            for (Path i : ubicaciones){
                System.out.print(i.toString()+" "+formatearTamanio(Files.size(i)));

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public static String formatearTamanio(double bytes) {
        if (bytes < 1024)
            return bytes + " B";
        else if (bytes < 1024 * 1024)
            return String.format("%.2f KB", bytes / 1024);
        else if (bytes < 1024 * 1024 * 1024)
            return String.format("%.2f MB", bytes / (1024 * 1024));
        else if (bytes < 1024L * 1024 * 1024 * 1024)
            return String.format("%.2f GB", bytes / (1024 * 1024 * 1024));
        else
            return String.format("%.2f TB", bytes / (1024L * 1024 * 1024 * 1024));
    }













}
