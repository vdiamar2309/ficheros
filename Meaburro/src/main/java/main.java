import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    static void main(String[] args) {
        Path p = Paths.get(".\\matriculas.txt");
        Pattern patron = Pattern.compile("^(?<numeros>[0-9]{4})-(?<letras>[A-Z&&[^AEIOU]]{3})");

        if (!Files.exists(p)) {
            try {
                Files.createFile(p);
            } catch (IOException e) {
                System.out.println(e);
            }
        }


        try {


            for (String i :Files.readAllLines(p) ){
                Matcher m = patron.matcher(i);

                if (m.find()){
                    String numeros = m.group("numeros");
                    String letras = m.group("letras");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
