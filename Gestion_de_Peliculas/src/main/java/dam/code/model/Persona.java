package dam.code.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entidad principal, es la clase Persona que implemententa Serializable
 * Contiene 4 atributos propios
 * @see Serializable
 * @author Marcel Abad
 * @version 1.0
 */
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String dni;
    private final String nombre;
    private final String apellido;
    private final String email;

    /**
     * Constructor principal de la clase Persona.
     * Inicializa los 4 atributos del objeto.
     * @param dni identificador unico de la persona
     * @param nombre nombre de la persona
     * @param apellido apellido de la persona
     * @param email correo electronico de la persona
     */
    public Persona(String dni, String nombre, String apellido, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    /**
     * Devuelve el DNI de la persona.
     * @return dni
     */
    public String getDni() { return dni; }

    /**
     * Devuelve el nombre de la persona.
     * @return nombre
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve el apellido de la persona.
     * @return apellido
     */
    public String getApellido() { return apellido; }

    /**
     * Devuelve el email de la persona.
     * @return email
     */
    public String getEmail() { return email; }

    /**
     * Compara dos objetos Persona por sus 4 atributos.
     * Devuelve true si todos los campos son iguales.
     * @param o objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(dni, persona.dni) && Objects.equals(nombre, persona.nombre) && Objects.equals(apellido, persona.apellido) && Objects.equals(email, persona.email);
    }

    /**
     * Genera el hashCode.
     * Usado para colecciones como HashMap o HashSet.
     * @return valor hash del objeto
     */
    @Override
    public int hashCode() {
        return Objects.hash(dni, nombre, apellido, email);
    }
}