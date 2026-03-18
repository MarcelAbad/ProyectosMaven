package dam.code.repository;

import dam.code.dto.PeliculaDto;
import dam.code.exceptions.PeliculaException;
import dam.code.persistence.JsonManager;
import dam.code.model.Pelicula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class PeliculaRepository {

    private final ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();
    private final JsonManager jsonManager;
    private final String usuario;

    public PeliculaRepository(JsonManager jsonManager, String usuario) {
        this.jsonManager = jsonManager;
        this.usuario = usuario;
        cargarDesdeJson();
    }

    private void cargarDesdeJson() {
        List<PeliculaDto> dtos = jsonManager.cargar(usuario);
        for (PeliculaDto dto : dtos) {
            peliculas.add(Pelicula.fromDto(dto));
        }
    }

    private void guardarEnJson() throws PeliculaException {
        List<PeliculaDto> dtos = peliculas.stream()
                .map(Pelicula::toDto)
                .toList();
        jsonManager.guardar(usuario, dtos);
    }

    public ObservableList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void agregar(Pelicula pelicula) throws PeliculaException {
        if (existeId(pelicula.getId())) {
            throw new PeliculaException("Ya existe una película con el id: " + pelicula.getId());
        }
        if (!pelicula.getId().matches("[a-zA-Z]{3}\\d{2}")) {
            throw new PeliculaException("El id debe tener 3 letras y 2 números");
        }
        peliculas.add(pelicula);
        guardarEnJson();
    }

    public void eliminar(Pelicula pelicula) throws PeliculaException {
        if (!peliculas.contains(pelicula)) {
            throw new PeliculaException("La película no existe");
        }
        peliculas.remove(pelicula);
        guardarEnJson();
    }

    public void actualizarTitulo(Pelicula pelicula, String nuevoTitulo) throws PeliculaException {
        if (nuevoTitulo == null || nuevoTitulo.isBlank()) {
            throw new PeliculaException("El título no puede estar vacío");
        }
        pelicula.setTitulo(nuevoTitulo);
        guardarEnJson();
    }

    public void actualizarFechaPublicacion(Pelicula pelicula, java.time.LocalDate fecha) throws PeliculaException {
        if (fecha == null) {
            throw new PeliculaException("La fecha no puede ser nula");
        }
        pelicula.setFechaPublicacion(fecha);
        guardarEnJson();
    }

    public void agregarVisualizacion(Pelicula pelicula) throws PeliculaException {
        if (!peliculas.contains(pelicula)) {
            throw new PeliculaException("La película no existe");
        }
        guardarEnJson();
    }

    private boolean existeId(String id) {
        return peliculas.stream().anyMatch(p -> p.getId().equals(id));
    }
}