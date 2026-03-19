package dam.code.model;

import dam.code.dto.PeliculaDto;
import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Clase que representa una pelicula.
 * @author Marcel Abad
 * @version 1.0
 */
public class Pelicula {

    private final StringProperty id;
    private final StringProperty titulo;
    private final StringProperty director;
    private final IntegerProperty duracion;
    private final ObjectProperty<LocalDate> fechaPublicacion;

    /**
     * Crea una pelicula con todos sus datos.
     * @param id identificador unico
     * @param titulo titulo de la pelicula
     * @param director director de la pelicula
     * @param duracion duracion en minutos
     * @param fechaPublicacion fecha de publicacion
     */
    public Pelicula(String id, String titulo, String director, Integer duracion, LocalDate fechaPublicacion) {
        this.id = new SimpleStringProperty(id);
        this.titulo = new SimpleStringProperty(titulo);
        this.director = new SimpleStringProperty(director);
        this.duracion = new SimpleIntegerProperty(duracion);
        this.fechaPublicacion = new SimpleObjectProperty<>(fechaPublicacion);
    }

    /**
     * Devuelve el id de la pelicula.
     * @return id
     */
    public String getId() { return id.get(); }

    /**
     * Devuelve el titulo de la pelicula.
     * @return titulo
     */
    public String getTitulo() { return titulo.get(); }

    /**
     * Devuelve el director de la pelicula.
     * @return director
     */
    public String getDirector() { return director.get(); }

    /**
     * Devuelve la duracion en minutos.
     * @return duracion
     */
    public Integer getDuracion() { return duracion.get(); }

    /**
     * Devuelve la fecha de publicacion.
     * @return fechaPublicacion
     */
    public LocalDate getFechaPublicacion() { return fechaPublicacion.get(); }

    /**
     * Devuelve el id como Property para usarlo en la interfaz.
     * @return idProperty
     */
    public StringProperty idProperty() { return id; }

    /**
     * Devuelve el titulo como Property para usarlo en la interfaz.
     * @return tituloProperty
     */
    public StringProperty tituloProperty() { return titulo; }

    /**
     * Devuelve el director como Property para usarlo en la interfaz.
     * @return directorProperty
     */
    public StringProperty directorProperty() { return director; }

    /**
     * Devuelve la duracion como Property para usarlo en la interfaz.
     * @return duracionProperty
     */
    public IntegerProperty duracionProperty() { return duracion; }

    /**
     * Devuelve la fecha como Property para usarlo en la interfaz.
     * @return fechaPublicacionProperty
     */
    public ObjectProperty<LocalDate> fechaPublicacionProperty() { return fechaPublicacion; }

    /**
     * Cambia el titulo de la pelicula.
     * @param titulo nuevo titulo
     */
    public void setTitulo(String titulo) { this.titulo.set(titulo); }

    /**
     * Cambia la fecha de publicacion.
     * @param fechaPublicacion nueva fecha
     */
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion.set(fechaPublicacion); }

    /**
     * Convierte la pelicula a un objeto DTO.
     * @return PeliculaDto con los mismos datos
     */
    public PeliculaDto toDto() {
        return new PeliculaDto(
                getId(),
                getTitulo(),
                getDirector(),
                getDuracion(),
                getFechaPublicacion()
        );
    }

    /**
     * Crea una Pelicula a partir de un DTO.
     * @param dto datos de la pelicula
     * @return nueva instancia de Pelicula
     */
    public static Pelicula fromDto(PeliculaDto dto) {
        return new Pelicula(
                dto.getId(),
                dto.getTitulo(),
                dto.getDirector(),
                dto.getDuracion(),
                dto.getFechaPublicacion()
        );
    }
}