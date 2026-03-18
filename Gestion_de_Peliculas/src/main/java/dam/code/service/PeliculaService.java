package dam.code.service;

import dam.code.dto.PeliculaDto;
import dam.code.exceptions.PeliculaException;
import dam.code.model.Pelicula;
import dam.code.repository.PeliculaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeliculaService {

    private final ObservableList<Pelicula> peliculas;
    private final PeliculaRepository repository;
    private final Map<PeliculaDto, Integer> visualizaciones;

    public PeliculaService (PeliculaRepository repository){
        this.repository = repository;
        visualizaciones = repository.cargar();
        peliculas = FXCollections.observableArrayList(cargar());
    }

    public ObservableList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public ObservableList<Pelicula> cargar(){
        List<Pelicula> listaPeliculas = new ArrayList<>();

        if (!visualizaciones.isEmpty()) {
            for (Map.Entry<PeliculaDto, Integer> entry : visualizaciones.entrySet()) {
                listaPeliculas.add(Pelicula.fromDto(entry.getKey()));
            }
        }
        return listaPeliculas;
    }

    public void agregarPelicula(Pelicula pelicula) throws PeliculaException {
        validarPelicula(pelicula);
        peliculas.add(pelicula);
        visualizaciones.put(pelicula.toDto(), 0);
        guardar();
    }

    public void eliminarPelicula(Pelicula pelicula) throws PeliculaException {
        if (pelicula == null) {
            throw new PeliculaException("Debes selecionar una pelicula");
        }
        peliculas.remove(pelicula);
        guardar();
    }

    private void validarPelicula(Pelicula pelicula) {

        if (pelicula.getId() == null || pelicula.getId().isBlank()) {
            throw new PeliculaException("El ID es obligatorio");
        }
        if (pelicula.getTitulo() == null || pelicula.getTitulo().isBlank()) {
            throw new PeliculaException("El Titulo es obligatorio");
        }
        if (pelicula.getDirector() == null || pelicula.getDirector().isBlank()) {
            throw new PeliculaException("El Director es obligatorio");
        }
        if (pelicula.getDuracion() <= 30) {
            throw new PeliculaException("La duracion no puede ser menor que 30");
        }
        if (pelicula.getFechaPublicacion().isAfter(LocalDate.now())) {
            throw new PeliculaException("La fecha no puede ser superior a la de hoy");
        }

        boolean existe = peliculas.stream()
                .anyMatch(pelicula1 -> pelicula1.getId().equals(pelicula.getId()));

        if (existe) {
            throw new PeliculaException("El pelicula ya existe");
        }
    }

    private void guardar() { repository.guardar(visualizaciones);}

    public void actualizarTitulo(Pelicula pelicula, )


}
