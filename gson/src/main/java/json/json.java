package json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class json {
    static void main(String[] args) {


        Persona n = new Persona("Victo", 20, 212361237, "28282828F");
        Persona n1 = new Persona("Victo1", 20, 212361237, "28282828F");
        Persona n2 = new Persona("Victo2", 20, 212361237, "28282828F");
        Persona n3 = new Persona("Victo3", 20, 212361237, "28282828F");
        Persona n4 = new Persona("Victo4", 20, 212361237, "28282828F");
        Persona n5 = new Persona("Victo5", 20, 212361237, "28282828F");
        Persona n6 = new Persona("Victo6", 20, 212361237, "28282828F");
        Persona n7 = new Persona("Victo7", 20, 212361237, "28282828F");
        Persona n8 = new Persona("Victo8", 20, 212361237, "28282828F");
        Persona n9 = new Persona("Victo900000", 20, 212361237, "28282828F");

        List <Persona> lista = List.of(n1,n2,n3,n4,n5,n6,n7,n8,n9,n);


        Path p = Paths.get("C:\\Users\\alumno\\Downloads\\PROGRAMA\\Java nio\\src\\main\\java\\json\\java.json");

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(lista);
            Files.writeString(p, json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}