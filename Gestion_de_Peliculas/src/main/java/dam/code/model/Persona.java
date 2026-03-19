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

public class Persona  implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String dni;
    private final String nombre;
    private final String apellido;
    private final String email;

    /**
     * Constructor principal de la clase Persona contiene los atributos
     **/
    public Persona(String dni, String nombre, String apellido, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(dni, persona.dni) && Objects.equals(nombre, persona.nombre) && Objects.equals(apellido, persona.apellido) && Objects.equals(email, persona.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, nombre, apellido, email);
    }
}