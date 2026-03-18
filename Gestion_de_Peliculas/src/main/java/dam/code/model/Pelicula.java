package dam.code.model;

import dam.code.dto.PeliculaDto;
import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDate;

public class Pelicula {

    private final StringProperty id;
    private final StringProperty titulo;
    private final StringProperty director;
    private final IntegerProperty duracion;
    private final ObjectProperty<LocalDate> fechaPublicacion;

    public Pelicula(String id, String titulo, String director, Integer duracion, LocalDate fechaPublicacion) {
        this.id = new SimpleStringProperty(id);
        this.titulo = new SimpleStringProperty(titulo);
        this.director = new SimpleStringProperty(director);
        this.duracion = new SimpleIntegerProperty(duracion);
        this.fechaPublicacion = new SimpleObjectProperty<>(fechaPublicacion);
    }

    public String getId() { return id.get(); }
    public String getTitulo() { return titulo.get(); }
    public String getDirector() { return director.get(); }
    public Integer getDuracion() { return duracion.get(); }
    public LocalDate getFechaPublicacion() { return fechaPublicacion.get(); }

    public StringProperty idProperty() { return id; }
    public StringProperty tituloProperty() { return titulo; }
    public StringProperty directorProperty() { return director; }
    public IntegerProperty duracionProperty() { return duracion; }
    public ObjectProperty<LocalDate> fechaPublicacionProperty() { return fechaPublicacion; }

    public void setTitulo(String titulo) { this.titulo.set(titulo); }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion.set(fechaPublicacion); }

    public PeliculaDto toDto() {
        return new PeliculaDto(
                getId(),
                getTitulo(),
                getDirector(),
                getDuracion(),
                getFechaPublicacion()
        );
    }

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