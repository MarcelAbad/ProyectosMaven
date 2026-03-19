package dam.code.persistence;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dam.code.dto.PeliculaDto;
import dam.code.exceptions.PeliculaException;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona la persistencia de peliculas en archivos JSON.
 * Cada usuario tiene su propio archivo en la carpeta visualizaciones/.
 * @author Marcel Abad
 * @version 1.0
 */
public class JsonManager {

    private static final String CARPETA = "visualizaciones/";

    private final Gson gson;

    /**
     * Crea el JsonManager configurando Gson para manejar LocalDate
     * y crea la carpeta de almacenamiento si no existe.
     */
    public JsonManager() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .setPrettyPrinting()
                .create();

        crearCarpetaSiNoExiste();
    }

    /**
     * Crea la carpeta visualizaciones/ si no existe.
     */
    private void crearCarpetaSiNoExiste() {
        File carpeta = new File(CARPETA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    /**
     * Devuelve la ruta del archivo JSON del usuario.
     * @param usuario nombre del usuario
     * @return ruta del archivo
     */
    private String getRuta(String usuario) {
        return CARPETA + usuario + ".json";
    }

    /**
     * Carga la lista de peliculas del archivo JSON del usuario.
     * Devuelve una lista vacia si el archivo no existe o hay un error.
     * @param usuario nombre del usuario
     * @return lista de PeliculaDto
     */
    public List<PeliculaDto> cargar(String usuario) {
        File archivo = new File(getRuta(usuario));

        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(archivo)) {
            Type listType = new TypeToken<List<PeliculaDto>>() {}.getType();
            List<PeliculaDto> lista = gson.fromJson(reader, listType);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Guarda la lista de peliculas en el archivo JSON del usuario.
     * @param usuario nombre del usuario
     * @param peliculas lista de peliculas a guardar
     * @throws PeliculaException si ocurre un error al escribir el archivo
     */
    public void guardar(String usuario, List<PeliculaDto> peliculas) throws PeliculaException {
        File archivo = new File(getRuta(usuario));

        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(peliculas, writer);
        } catch (IOException e) {
            throw new PeliculaException("Error al guardar las películas: " + e.getMessage());
        }
    }

    /**
     * Comprueba si existe el archivo JSON del usuario.
     * @param usuario nombre del usuario
     * @return true si existe, false si no
     */
    public boolean existeArchivoUsuario(String usuario) {
        return new File(getRuta(usuario)).exists();
    }

    /**
     * Elimina el archivo JSON del usuario.
     * @param usuario nombre del usuario
     * @throws PeliculaException si el archivo no se puede eliminar
     */
    public void eliminarArchivoUsuario(String usuario) throws PeliculaException {
        File archivo = new File(getRuta(usuario));
        if (archivo.exists() && !archivo.delete()) {
            throw new PeliculaException("Error al eliminar el archivo de visualizaciones");
        }
    }
}