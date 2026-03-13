package dam.code.model;

import dam.code.dto.PersonaDto;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Persona {

    private final StringProperty dni;
    private final StringProperty nombre;
    private final StringProperty apellido;
    private final StringProperty email;

    public Persona(String dni, String nombre, String apellido, String email) {
        this.dni = new SimpleStringProperty(dni);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.email = new SimpleStringProperty(email);
    }

    public StringProperty getDni() {return dni;}

    public StringProperty getNombre() {return nombre;}

    public StringProperty getApellido() {return apellido;}

    public StringProperty getEmail() {return email;}

    public PersonaDto toDto() {
        return new PersonaDto(
                getDni(),
                getNombre(),
                getApellido(),
                getEmail()
        );
    }

    public static Persona fromDto(PersonaDto dto){
        return new Persona(
                dto.getDni(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getEmail()
        );
    }
}
