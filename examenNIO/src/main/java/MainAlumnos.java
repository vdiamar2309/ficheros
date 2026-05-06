import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.print.attribute.standard.Destination;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.nio.file.Files.*;

public class MainAlumnos {

    // Ruta base donde se organizarán todos los logs procesados
    public final static Path RUTA_DESTINO = Path.of("./logs_procesados");

    // Patrón para identificar ficheros de log con el formato: server{N}_app{N}.log
    private static final Pattern patron = Pattern.compile("server(?<server>[0-9])_app(?<app>[12])(.log)$"); //TODO: crear el patrón con la regexp del nombre del archivo

    // Patrón para parsear líneas de log de la app1
    // Formato esperado: yyyy/MM/dd HH:mm:ss - [NIVEL] - Mensaje
    private final static Pattern patronLogApp1 = Pattern.compile("(?<fecha>[0-9]{4}\\/[0-9]{2}\\/[0-9]{2})\\s(?<hora>[0-2][0-9]:[0-5][0-9]:[0-5][0-9])\\s-\\s\\[(?<nivel>[A-Z]*)\\]\\s-\\s(?<mensaje>([A-z]*[\\ ]*)*[\\.]*)"); //TODO: crear el patrón con la regexp del formato de logs de la app1. Consejo, usa grupos de captura nombrados.

    // Patrón para parsear líneas de log de la app2
    // Formato esperado: [dd-MM-yyyy|HH:mm:ss] <NIVEL> Mensaje
    private final static Pattern patronLogApp2 = Pattern.compile("\\[(?<fecha>[0-3][0-9]-[0-1][0-9]-[1-2][0-9]{3})\\|(?<hora>[0-9]{2}:[0-5][0-9]:[0-5][0-9])\\] <(?<nivel>[A-Z]*)> (?<mensaje>[[A-z]* ]*.*)"); //TODO: ídem del anterior pero con el formato de la app2

    public static void main(String[] args) {

        // Generamos el entorno de prueba con los logs desordenados
        GeneradorLogsExamen.execute();

        // Limpiamos la carpeta de destino antes de empezar para evitar
        // mezclar resultados de ejecuciones anteriores
        eliminarDirectorioRecursivo(RUTA_DESTINO);

        Path carpetaRaiz = Path.of("./entorno_examen_logs");
        organizaCaosLogs(carpetaRaiz);

    }

    /**
     * Recorre recursivamente la carpeta raíz en busca de ficheros de log,
     * los mueve a su ubicación organizada y extrae los errores de cada uno.
     *
     * @param carpetaRaiz Ruta del directorio origen donde están los logs mezclados.
     */
    private static void organizaCaosLogs(Path carpetaRaiz) {

        // Lista acumuladora de todos los errores encontrados en todos los ficheros
        List<DetalleError> todosLosErrores = new ArrayList<>();

        //Solo procesamos ficheros cuyo nombre coincide con el patrón esperado
        //Construimos la ruta destino organizada por servidor y aplicación
        //Creamos los directorios intermedios si no existen
        //Movemos el fichero a su nueva ubicación organizada
        //Inspeccionamos el fichero ya movido en busca de líneas de nivel ERROR,
        //usando el patrón correspondiente según la aplicación
        //Una vez procesados todos los ficheros, escribimos el reporte global de errores
        try {
            List<Path> rutas = Files.walk(carpetaRaiz).toList();

            for (Path i : rutas) {
                Matcher m = patron.matcher(i.toString());
                if (m.find()) {
                    Path p = Paths.get(RUTA_DESTINO.toString(), "server" + m.group("server"));
                    if (Files.notExists(p)) {
                        Files.createDirectories(p);
                    }

                    Path rutaX = Paths.get(p + "\\app" + m.group("app"));

                    if (Files.notExists(rutaX)) {
                        Files.createDirectory(rutaX);
                    }


                    Files.move(i, rutaX.resolve(i.getFileName()));

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
       todosLosErrores = guardarErroresDeTodosLosFicheros();
        for (DetalleError i : todosLosErrores){
            System.out.println(i.fecha()+" "+i.server()+" "+i.aplicacion());
        }

    }

    /**
     * Lee un fichero de log línea a línea y extrae aquellas cuyo nivel sea ERROR.
     * <p>
     * //* @param p          Ruta del fichero a analizar
     *
     * @param logPattern Patrón regex correspondiente al formato de la aplicación
     * @param server     ID del servidor de origen
     * @param app        ID de la aplicación de origen
     * @return Lista de errores encontrados en el fichero
     * @throws LogException Si ocurre un error al leer el fichero
     */

    //Aportas un servidor, la aplicación y te resuelve automaticamente la ruta
    private static List<DetalleError> buscarErrores(Pattern logPattern, String server, String app) throws LogException {
        // Aplicamos el patrón a cada línea para obtener un Matcher
        // Descartamos las líneas que no coinciden con el formato esperado
        // Nos quedamos solo con las líneas de nivel ERROR
        // Construimos el objeto DetalleError con los datos extraídos
        Path ruta = RUTA_DESTINO.resolve(server, app + "\\" + server + "_" + app + ".log");

        List<DetalleError> devolverErrores = new ArrayList<>();
        try {
            for (String i : Files.readAllLines(ruta)) {
                Matcher m = logPattern.matcher(i);

                if (m.find() && m.group("nivel").equals("ERROR")) {
                    DetalleError dt = new DetalleError(server, app, m.group("fecha"), m.group("hora"), m.group("mensaje"));
                    devolverErrores.add(dt);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return devolverErrores;
    }

    /**
     * Serializa la lista de errores a JSON con formato legible
     * y la escribe en un fichero de reporte dentro de la carpeta de destino.
     *
     * @param errores Lista de errores recolectados a escribir.
     */
    private static void escribirErrorAFichero(List<DetalleError> errores) throws LogException {
        if (errores.isEmpty()) {
            throw new LogException("El fichero de errores está vacío");
        }
        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        String gson = gs.toJson(errores);
        for (DetalleError i : errores) {
            System.out.println(i.descripcion());
        }

        System.out.println(errores.size());
        Path p = RUTA_DESTINO.resolve(errores.getLast().server());

    }

    /**
     * Borra un directorio y todo su contenido de forma recursiva.
     * Los ficheros y subdirectorios se eliminan antes que sus padres
     * para evitar errores al borrar directorios no vacíos.
     *
     * @param ruta Ruta del directorio a eliminar.
     */
    private static void eliminarDirectorioRecursivo(Path ruta) {
        if (Files.exists(ruta)) {
            try (Stream<Path> walk = walk(ruta)) {
                walk.sorted(java.util.Comparator.reverseOrder()) // Borra hijos antes que padres
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (IOException e) {
                                System.err.println("No se pudo borrar: " + p + " -> " + e.getMessage());
                            }
                        });
                System.out.println("Carpeta de destino limpia.");
            } catch (IOException e) {
                System.err.println("Error al intentar limpiar el directorio: " + e.getMessage());
            }
        }
    }


    private static List<DetalleError> guardarErroresDeTodosLosFicheros() {
        List<DetalleError> detalleErrores= new ArrayList<>();
        //Una vez ordenado solamente tenemos que buscar los ficheros y guardarlos en la lista
        Pattern regexFichero;
        try {
            // Buscamos en el directorio después de haberlos ordenado
            List<Path> rutas = Files.walk(RUTA_DESTINO, 10).toList();

            //Descartamos los directorios y solo los ficheros
            List<Path> rutaLimpia = rutas.stream().filter(ruta -> !Files.isDirectory(ruta)
            ).toList();

            for (Path i : rutaLimpia) {
                Pattern p = Pattern.compile(".\\\\logs_procesados\\\\(?<server>server[1-3])\\\\(?<app>app[1-2])");
                Matcher m = p.matcher(i.toString());
                String server;
                String app;
                if (m.find()) {
                    app = m.group("app");
                    server = m.group("server");
                    if (m.group("app").equals("app1")){
                        regexFichero=patronLogApp1;
                    } else {
                        regexFichero=patronLogApp2;
                    }

                    for (String j : readAllLines(i)){
                        Matcher mErrores = regexFichero.matcher(j);
                        if (mErrores.find()){
                            DetalleError de = new DetalleError(server,app,mErrores.group("fecha"),mErrores.group("hora"), mErrores.group("mensaje"));
                            detalleErrores.add(de);
                        }
                    }

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return detalleErrores;
    }


}