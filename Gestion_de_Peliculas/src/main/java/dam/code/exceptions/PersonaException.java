package dam.code.exceptions;

/**
 * Excepcion personalizada para errores relacionados con personas.
 * @author Marcel Abad
 * @version 1.0
 */
public class PersonaException extends Exception {

    /**
     * Crea la excepcion con un mensaje descriptivo del error.
     * @param message mensaje del error
     */
    public PersonaException(String message) {
        super(message);
    }
}