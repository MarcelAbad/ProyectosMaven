package dam.code.dto;

import java.time.LocalDate;

public class PeliculaDto {

    private String id;
    private String titulo;
    private String director;
    private Integer duracion;
    private LocalDate fechaPublicacion;

    public PeliculaDto(String id, String titulo, String director, Integer duracion, LocalDate fechaPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.duracion = duracion;
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getId() {return id;}

    public String getTitulo() {return titulo;}

    public String getDirector() {return director;}

    public Integer getDuracion() {return duracion;}

    public LocalDate getFecha_publicacion() {return fechaPublicacion;}


}
