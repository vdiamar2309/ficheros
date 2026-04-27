import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class main {
    static void main(String[] args) {
        Path p = Paths.get("hola.txt");

        try {
            List <String> lineas= Files.readAllLines(p);
            for (String linea : lineas){
                System.out.print(linea+" ");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
