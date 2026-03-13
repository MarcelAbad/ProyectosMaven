package dam.code.model;

import dam.code.dto.PeliculaDto;
import javafx.beans.property.*;



public class Pelicula {

    private final StringProperty id;
    private final StringProperty titulo;
    private final StringProperty director;
    private final DoubleProperty duracion;
    private final StringProperty fecha_publicacion;

    public Pelicula(String id, String titulo, String director, Double duracion, String fecha_publicacion) {
        this.id = new SimpleStringProperty(id);
        this.titulo = new SimpleStringProperty(titulo);
        this.director = new SimpleStringProperty(director);
        this.duracion = new SimpleDoubleProperty(duracion);
        this.fecha_publicacion = new SimpleStringProperty(fecha_publicacion);
    }

    public String getId() {return id.get();}

    public String getTitulo() {return titulo.get();}

    public String getDirector() {return director.get();}

    public Double getDuracion() {return duracion.get();}

    public String getFecha_publicacion() {return fecha_publicacion.get();}



    public StringProperty IdProperty() {return id;}

    public StringProperty TituloProperty() {return titulo;}

    public StringProperty DirectorProperty() {return director;}

    public DoubleProperty DuracionProperty() {return duracion;}

    public StringProperty FechaPublicacionProperty() {return fecha_publicacion;}


    public void setTitulo(String titulo) {this.titulo.set(titulo);}

    public void setFecha_publicacion(String fecha_publicacion) {this.fecha_publicacion.set(fecha_publicacion);}


    public PeliculaDto toDto() {
        return new PeliculaDto(
                getId(),
                getTitulo(),
                getDirector(),
                getDuracion(),
                getFecha_publicacion()
        );
    }

    public static Pelicula fromDto(PeliculaDto dto) {
        return new Pelicula(
                dto.getId(),
                dto.getTitulo(),
                dto.getDirector(),
                dto.getDuracion(),
                dto.getFecha_publicacion()

        );
    }
}
