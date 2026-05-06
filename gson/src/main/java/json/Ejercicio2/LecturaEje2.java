package json.Ejercicio2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LecturaEje2 {
    static void main(String[] args) {
        ArrayList<Estudiante> estudiantes;
        Path pa = Paths.get("C:\\Users\\alumno\\Downloads\\PROGRAMA\\Java nio\\src\\main\\java\\json\\Ejercicio2\\archivo.json");
        try {
            String jsonLeido = Files.readString(pa);
            Gson gson = new Gson();

            Type tipoLista = new TypeToken<ArrayList<Estudiante>>() {
            }.getType();
            estudiantes = gson.fromJson(jsonLeido, tipoLista);
            for (Estudiante i : estudiantes) {

                System.out.println(i.toString());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
