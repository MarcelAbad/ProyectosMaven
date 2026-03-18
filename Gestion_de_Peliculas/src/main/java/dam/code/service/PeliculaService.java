package dam.code.service;

import dam.code.exceptions.PeliculaException;
import dam.code.model.Pelicula;
import dam.code.repository.PeliculaRepository;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private Runnable onCerrarSesion;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public void setOnCerrarSesion(Runnable onCerrarSesion) {
        this.onCerrarSesion = onCerrarSesion;
    }

    public ObservableList<Pelicula> getPeliculas() {
        return peliculaRepository.getPeliculas();
    }

    public void agregarPelicula(Pelicula pelicula) throws PeliculaException {
        validarPelicula(pelicula);
        peliculaRepository.agregar(pelicula);
    }

    public void eliminarPelicula(Pelicula pelicula) throws PeliculaException {
        if (pelicula == null) {
            throw new PeliculaException("Debes seleccionar una película");
        }
        peliculaRepository.eliminar(pelicula);
    }

    public void actualizarTitulo(Pelicula pelicula, String nuevoTitulo) throws PeliculaException {
        if (nuevoTitulo == null || nuevoTitulo.isBlank()) {
            throw new PeliculaException("El título no puede estar vacío");
        }
        peliculaRepository.actualizarTitulo(pelicula, nuevoTitulo);
    }

    public void actualizarFechaPublicacion(Pelicula pelicula, LocalDate fecha) throws PeliculaException {
        if (fecha == null) {
            throw new PeliculaException("La fecha no puede ser nula");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new PeliculaException("La fecha de publicación no puede ser futura");
        }
        peliculaRepository.actualizarFechaPublicacion(pelicula, fecha);
    }

    public void agregarVisualizacion(Pelicula pelicula) throws PeliculaException {
        if (pelicula == null) {
            throw new PeliculaException("Debes seleccionar una película");
        }
        peliculaRepository.agregarVisualizacion(pelicula);
    }

    public void cerrarSesion() {
        if (onCerrarSesion != null) {
            onCerrarSesion.run();
        }
    }

    private void validarPelicula(Pelicula pelicula) throws PeliculaException {
        if (pelicula.getId() == null || pelicula.getId().isBlank()) {
            throw new PeliculaException("El id no puede estar vacío");
        }
        if (!pelicula.getId().matches("[a-zA-Z]{3}\\d{2}")) {
            throw new PeliculaException("El id debe tener 3 letras y 2 números");
        }
        if (pelicula.getTitulo() == null || pelicula.getTitulo().isBlank()) {
            throw new PeliculaException("El título no puede estar vacío");
        }
        if (pelicula.getDirector() == null || pelicula.getDirector().isBlank()) {
            throw new PeliculaException("El director no puede estar vacío");
        }
        if (pelicula.getDuracion() == null || pelicula.getDuracion() < 30) {
            throw new PeliculaException("La duración mínima de una película es 30 minutos");
        }
        if (pelicula.getFechaPublicacion() == null) {
            throw new PeliculaException("La fecha de publicación no puede estar vacía");
        }
        if (pelicula.getFechaPublicacion().isAfter(LocalDate.now())) {
            throw new PeliculaException("La fecha de publicación no puede ser futura");
        }
    }
}