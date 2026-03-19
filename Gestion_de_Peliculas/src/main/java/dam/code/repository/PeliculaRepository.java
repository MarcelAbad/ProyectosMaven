package dam.code.repository;

import dam.code.dto.PeliculaDto;
import dam.code.exceptions.PeliculaException;
import dam.code.persistence.JsonManager;
import dam.code.model.Pelicula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Repositorio que gestiona la lista de peliculas en memoria y su persistencia en JSON.
 * @author Marcel Abad
 * @version 1.0
 */
public class PeliculaRepository {

    private final ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();
    private final JsonManager jsonManager;
    private final String usuario;

    /**
     * Crea el repositorio y carga las peliculas del JSON del usuario.
     * @param jsonManager gestor de archivos JSON
     * @param usuario nombre del usuario
     */
    public PeliculaRepository(JsonManager jsonManager, String usuario) {
        this.jsonManager = jsonManager;
        this.usuario = usuario;
        cargarDesdeJson();
    }

    /**
     * Carga las peliculas desde el JSON y las añade a la lista.
     */
    private void cargarDesdeJson() {
        List<PeliculaDto> dtos = jsonManager.cargar(usuario);
        for (PeliculaDto dto : dtos) {
            peliculas.add(Pelicula.fromDto(dto));
        }
    }

    /**
     * Convierte la lista a DTOs y la guarda en el JSON del usuario.
     * @throws PeliculaException si ocurre un error al guardar
     */
    private void guardarEnJson() throws PeliculaException {
        List<PeliculaDto> dtos = peliculas.stream()
                .map(Pelicula::toDto)
                .toList();
        jsonManager.guardar(usuario, dtos);
    }

    /**
     * Devuelve la lista observable de peliculas.
     * @return lista de peliculas
     */
    public ObservableList<Pelicula> getPeliculas() {
        return peliculas;
    }

    /**
     * Añade una pelicula a la lista si el id es unico y tiene formato valido.
     * @param pelicula pelicula a agregar
     * @throws PeliculaException si el id ya existe o el formato es incorrecto
     */
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

    /**
     * Elimina una pelicula de la lista.
     * @param pelicula pelicula a eliminar
     * @throws PeliculaException si la pelicula no existe
     */
    public void eliminar(Pelicula pelicula) throws PeliculaException {
        if (!peliculas.contains(pelicula)) {
            throw new PeliculaException("La película no existe");
        }
        peliculas.remove(pelicula);
        guardarEnJson();
    }

    /**
     * Actualiza el titulo de una pelicula.
     * @param pelicula pelicula a modificar
     * @param nuevoTitulo nuevo titulo a asignar
     * @throws PeliculaException si el titulo es nulo o vacio
     */
    public void actualizarTitulo(Pelicula pelicula, String nuevoTitulo) throws PeliculaException {
        if (nuevoTitulo == null || nuevoTitulo.isBlank()) {
            throw new PeliculaException("El título no puede estar vacío");
        }
        pelicula.setTitulo(nuevoTitulo);
        guardarEnJson();
    }

    /**
     * Actualiza la fecha de publicacion de una pelicula.
     * @param pelicula pelicula a modificar
     * @param fecha nueva fecha a asignar
     * @throws PeliculaException si la fecha es nula
     */
    public void actualizarFechaPublicacion(Pelicula pelicula, java.time.LocalDate fecha) throws PeliculaException {
        if (fecha == null) {
            throw new PeliculaException("La fecha no puede ser nula");
        }
        pelicula.setFechaPublicacion(fecha);
        guardarEnJson();
    }

    /**
     * Registra una visualizacion de la pelicula y guarda los cambios.
     * @param pelicula pelicula a la que añadir la visualizacion
     * @throws PeliculaException si la pelicula no existe
     */
    public void agregarVisualizacion(Pelicula pelicula) throws PeliculaException {
        if (!peliculas.contains(pelicula)) {
            throw new PeliculaException("La película no existe");
        }
        guardarEnJson();
    }

    /**
     * Comprueba si ya existe una pelicula con el id indicado.
     * @param id id a comprobar
     * @return true si existe, false si no
     */
    private boolean existeId(String id) {
        return peliculas.stream().anyMatch(p -> p.getId().equals(id));
    }
}