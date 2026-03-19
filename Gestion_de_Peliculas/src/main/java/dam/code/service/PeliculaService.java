package dam.code.service;

import dam.code.exceptions.PeliculaException;
import dam.code.model.Pelicula;
import dam.code.repository.PeliculaRepository;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Servicio que contiene la logica de negocio de las peliculas.
 * Valida los datos antes de delegarlos al repositorio.
 * @author Marcel Abad
 * @version 1.0
 */
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private Runnable onCerrarSesion;

    /**
     * Crea el servicio con el repositorio de peliculas.
     * @param peliculaRepository repositorio a usar
     */
    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    /**
     * Asigna la accion a ejecutar al cerrar sesion.
     * @param onCerrarSesion accion a ejecutar
     */
    public void setOnCerrarSesion(Runnable onCerrarSesion) {
        this.onCerrarSesion = onCerrarSesion;
    }

    /**
     * Devuelve la lista observable de peliculas.
     * @return lista de peliculas
     */
    public ObservableList<Pelicula> getPeliculas() {
        return peliculaRepository.getPeliculas();
    }

    /**
     * Valida y agrega una nueva pelicula al repositorio.
     * @param pelicula pelicula a agregar
     * @throws PeliculaException si los datos no son validos
     */
    public void agregarPelicula(Pelicula pelicula) throws PeliculaException {
        validarPelicula(pelicula);
        peliculaRepository.agregar(pelicula);
    }

    /**
     * Elimina una pelicula del repositorio.
     * @param pelicula pelicula a eliminar
     * @throws PeliculaException si la pelicula es nula
     */
    public void eliminarPelicula(Pelicula pelicula) throws PeliculaException {
        if (pelicula == null) {
            throw new PeliculaException("Debes seleccionar una película");
        }
        peliculaRepository.eliminar(pelicula);
    }

    /**
     * Actualiza el titulo de una pelicula.
     * @param pelicula pelicula a modificar
     * @param nuevoTitulo nuevo titulo
     * @throws PeliculaException si el titulo es nulo o vacio
     */
    public void actualizarTitulo(Pelicula pelicula, String nuevoTitulo) throws PeliculaException {
        if (nuevoTitulo == null || nuevoTitulo.isBlank()) {
            throw new PeliculaException("El título no puede estar vacío");
        }
        peliculaRepository.actualizarTitulo(pelicula, nuevoTitulo);
    }

    /**
     * Actualiza la fecha de publicacion de una pelicula.
     * @param pelicula pelicula a modificar
     * @param fecha nueva fecha
     * @throws PeliculaException si la fecha es nula o futura
     */
    public void actualizarFechaPublicacion(Pelicula pelicula, LocalDate fecha) throws PeliculaException {
        if (fecha == null) {
            throw new PeliculaException("La fecha no puede ser nula");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new PeliculaException("La fecha de publicacion no puede ser futura");
        }
        peliculaRepository.actualizarFechaPublicacion(pelicula, fecha);
    }

    /**
     * Registra una visualizacion de la pelicula.
     * @param pelicula pelicula a la que añadir la visualizacion
     * @throws PeliculaException si la pelicula es nula
     */
    public void agregarVisualizacion(Pelicula pelicula) throws PeliculaException {
        if (pelicula == null) {
            throw new PeliculaException("Debes seleccionar una pelicula");
        }
        peliculaRepository.agregarVisualizacion(pelicula);
    }

    /**
     * Cierra la sesion del usuario ejecutando la accion asignada.
     */
    public void cerrarSesion() {
        if (onCerrarSesion != null) {
            onCerrarSesion.run();
        }
    }

    /**
     * Valida todos los campos de una pelicula antes de guardarla.
     * Lanza una excepcion si alguno no cumple las reglas.
     * @param pelicula pelicula a validar
     * @throws PeliculaException si algún campo es invalido
     */
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