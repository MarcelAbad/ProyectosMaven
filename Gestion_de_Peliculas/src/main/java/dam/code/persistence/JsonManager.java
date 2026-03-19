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

public class JsonManager {

    private static final String CARPETA = "visualizaciones/";

    private final Gson gson;

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

    private void crearCarpetaSiNoExiste() {
        File carpeta = new File(CARPETA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    private String getRuta(String usuario) {
        return CARPETA + usuario + ".json";
    }

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

    public void guardar(String usuario, List<PeliculaDto> peliculas) throws PeliculaException {
        File archivo = new File(getRuta(usuario));

        try (Writer writer = new FileWriter(archivo)) {
            gson.toJson(peliculas, writer);
        } catch (IOException e) {
            throw new PeliculaException("Error al guardar las películas: " + e.getMessage());
        }
    }

    public boolean existeArchivoUsuario(String usuario) {
        return new File(getRuta(usuario)).exists();
    }

    public void eliminarArchivoUsuario(String usuario) throws PeliculaException {
        File archivo = new File(getRuta(usuario));
        if (archivo.exists() && !archivo.delete()) {
            throw new PeliculaException("Error al eliminar el archivo de visualizaciones");
        }
    }
}