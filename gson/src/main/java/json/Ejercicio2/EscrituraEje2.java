package json.Ejercicio2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class EscrituraEje2 {
    static void main(String[] args) {
        Path p = Paths.get("C:\\Users\\alumno\\Downloads\\PROGRAMA\\Java nio\\src\\main\\java\\json\\Ejercicio2\\archivo.json");
        ArrayList <Estudiante> estudiantes= new ArrayList<>();
        try {
            Files.createFile(p);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Estudiante moi = new Estudiante("Moisés","1º DAM",8.5);
        Estudiante vic = new Estudiante("Victor", "1º DAM",7.9);
        Estudiante javi = new Estudiante("Javier", "1º DAM",7.9);

        estudiantes.add(moi);
        estudiantes.add(vic);
        estudiantes.add(javi);

        try {
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            String gson = g.toJson(estudiantes);
            Files.writeString(p, gson);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
