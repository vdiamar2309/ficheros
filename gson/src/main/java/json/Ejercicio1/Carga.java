package json.Ejercicio1;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Carga {
    static void main(String[] args) {
        Path pa = Paths.get("C:\\Users\\alumno\\Downloads\\PROGRAMA\\Java nio\\src\\main\\java\\json\\Ejercicio1\\archivoLecturaEscritura.json");


        try {
            String jsonLeido = Files.readString(pa);
            Gson gson = new Gson();
            Videojuego v = gson.fromJson(jsonLeido, Videojuego.class);
            System.out.println(v.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
