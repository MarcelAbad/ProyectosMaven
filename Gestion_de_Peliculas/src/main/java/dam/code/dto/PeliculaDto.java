package dam.code.dto;

import java.time.LocalDate;

/**
 * DTO de la clase Pelicula.
 * Se usa para transferir datos sin propiedades JavaFX.
 * @author Marcel Abad
 * @version 1.0
 */
public class PeliculaDto {

    private String id;
    private String titulo;
    private String director;
    private Integer duracion;
    private LocalDate fechaPublicacion;

    /**
     * Crea un DTO con todos los datos de una pelicula.
     * @param id identificador unico
     * @param titulo titulo de la pelicula
     * @param director director de la pelicula
     * @param duracion duracion en minutos
     * @param fechaPublicacion fecha de publicacion
     */
    public PeliculaDto(String id, String titulo, String director, Integer duracion, LocalDate fechaPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.duracion = duracion;
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Devuelve el id de la pelicula.
     * @return id
     */
    public String getId() { return id; }

    /**
     * Devuelve el titulo de la pelicula.
     * @return titulo
     */
    public String getTitulo() { return titulo; }

    /**
     * Devuelve el director de la pelicula.
     * @return director
     */
    public String getDirector() { return director; }

    /**
     * Devuelve la duracion en minutos.
     * @return duracion
     */
    public Integer getDuracion() { return duracion; }

    /**
     * Devuelve la fecha de publicacion.
     * @return fechaPublicacion
     */
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
}