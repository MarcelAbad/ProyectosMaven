package dam.code.persistence;

import dam.code.exceptions.PersonaException;
import dam.code.model.Persona;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RegistroDAO {

    private static final String RUTA = "registros.dat";

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

    public void guardar(Map<Persona, String> registros) throws PersonaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA))) {
            oos.writeObject(registros);
        } catch (IOException e) {
            throw new PersonaException("Error al guardar los registros: " + e.getMessage());
        }
    }

    public boolean existenRegistros() {
        File archivo = new File(RUTA);
        if (!archivo.exists()) return false;

        Map<Persona, String> registros = cargar();
        return !registros.isEmpty();
    }
}