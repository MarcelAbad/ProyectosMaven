package dam.code.persistence;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Adaptador de Gson para serializar y deserializar LocalDate en JSON.
 * Necesario porque Gson no soporta LocalDate de forma nativa.
 * @author Marcel Abad
 * @version 1.0
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    /**
     * Convierte un LocalDate a String y lo escribe en el JSON.
     * Si el valor es null escribe un null en el JSON.
     * @param out escritor JSON
     * @param value fecha a escribir
     * @throws IOException si ocurre un error al escribir
     */
    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toString());
        }
    }

    /**
     * Lee un String del JSON y lo convierte a LocalDate.
     * @param in lector JSON
     * @return LocalDate parseado
     * @throws IOException si ocurre un error al leer
     */
    @Override
    public LocalDate read(JsonReader in) throws IOException {
        String date = in.nextString();
        return LocalDate.parse(date);
    }
}