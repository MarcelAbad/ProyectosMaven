package dam.code.exceptions;

/**
 * Excepcion personalizada para errores relacionados con peliculas.
 * @author Marcel Abad
 * @version 1.0
 */
public class PeliculaException extends Exception {

    /**
     * Crea la excepcion con un mensaje descriptivo del error.
     * @param message mensaje del error
     */
    public PeliculaException(String message) {
        super(message);
    }
}