import util.MiEntradaSalida;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class main {
    static void main(String[] args) throws IOException {

       String linea= MiEntradaSalida.solicitarCadena("Introduce texto para añadir al fichero");
        Path p = Paths.get( "C:\\Users\\alumno\\IdeaProjects\\ficheros\\Ejercicio3\\salidaEJ3.txt");

        /*
        StandartOpenOption.APPEND es para que no sobrescriba el texto que ya había
         */
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(p,StandardOpenOption.APPEND)) {

            /*
            El \n es un salto de linea para cuando añadas la linea no se añada donde ya hay texto sino que vaya a la siguiente
             */
            bufferedWriter.write("\n"+linea);

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
