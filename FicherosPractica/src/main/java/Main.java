import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class Main {
    public static Path rutaEmpleados = Paths.get("DatosEmpleados");
    public static Pattern confidencial = Pattern.compile("CONFIDENCIAL|confidencial|Confidencial\\s");

    static void main(String[] args) {
        ArrayList<String> a = devolverNoConfidenciales();
        for (String s : a) {
            System.out.println(s);
        }

    }

    public static ArrayList<String> devolverRutaFicheros() {
        ArrayList<String> rutasDevolver = new ArrayList<>();
        List<Path> paths;
        try (Stream<Path> stream = Files.walk(rutaEmpleados)) {
            paths = stream.toList();
            Pattern pattern = Pattern.compile("\\\\empleado_[0-9]*.(txt|data)$");

            for (Path p : paths) {
                Matcher m = pattern.matcher(p.toString());
                if (m.find()) {
                    rutasDevolver.add(p.toString());
                }
            }
            return rutasDevolver;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<String> devolverNoConfidenciales() {
        ArrayList<String> rutas = devolverRutaFicheros();
        ArrayList<String> limpio = new ArrayList<>();
        Path p;
        boolean marcaIgnorar = false;
        for (String ruta : rutas) {
            try {
                for (String linea : Files.readAllLines(Path.of(ruta))) {
                    Matcher m = confidencial.matcher(linea);
                    if (m.find()) {
                        marcaIgnorar = true;
                    }
                }
                if (marcaIgnorar) {
                    marcaIgnorar = false;
                } else {
                    limpio.add(ruta);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return limpio;
    }

    public static void concatenar() {
        if (!devolverNoConfidenciales().isEmpty()) {
            for (String string : devolverNoConfidenciales()) {
                Files.write(Path.of(string));
            }
        }
    }

}


