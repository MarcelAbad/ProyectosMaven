package dam.code.persistence;

import dam.code.exceptions.PersonaException;
import dam.code.model.Persona;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona la persistencia de usuarios en un archivo binario.
 * Guarda y carga un mapa de Persona y su contrasena en registros.dat.
 * @author Marcel Abad
 * @version 1.0
 */
public class RegistroDAO {

    private static final String RUTA = "registros.dat";

    /**
     * Carga el mapa de registros desde el archivo binario.
     * Devuelve un mapa vacio si el archivo no existe o hay un error.
     * @return mapa de Persona y contrasena
     */
    public Map<Persona, String> cargar() {
        File archivo = new File(RUTA);

        if (!archivo.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof Map<?, ?> mapa) {
                return (Map<Persona, String>) mapa;
            }
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    /**
     * Guarda el mapa de registros en el archivo binario.
     * @param registros mapa de Persona y contrasena a guardar
     * @throws PersonaException si ocurre un error al escribir el archivo
     */
    public void guardar(Map<Persona, String> registros) throws PersonaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA))) {
            oos.writeObject(registros);
        } catch (IOException e) {
            throw new PersonaException("Error al guardar los registros: " + e.getMessage());
        }
    }

    /**
     * Comprueba si el archivo de registros existe y tiene datos.
     * @return true si hay registros, false si no
     */
    public boolean existenRegistros() {
        File archivo = new File(RUTA);
        if (!archivo.exists()) return false;

        Map<Persona, String> registros = cargar();
        return !registros.isEmpty();
    }
}