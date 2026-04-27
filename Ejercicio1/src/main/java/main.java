import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class main {
    static void main() {

        try (BufferedReader br = new BufferedReader(new FileReader("hola.txt"))) {
            String line;
            while ((line = br.readLine() )!= null){
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
