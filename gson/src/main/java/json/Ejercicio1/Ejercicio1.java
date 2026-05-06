package json.Ejercicio1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ejercicio1 {

    static void main(String[] args) {
        Path pa= Paths.get("C:\\Users\\alumno\\Downloads\\PROGRAMA\\Java nio\\src\\main\\java\\json\\Ejercicio1\\archivoLecturaEscritura.json");
        Videojuego v = new Videojuego("Mario kart","Nintendo",2026);

        try {
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            String gson = g.toJson(v);
            Files.writeString(pa,gson);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
