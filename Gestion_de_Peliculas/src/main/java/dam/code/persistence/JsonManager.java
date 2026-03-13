package dam.code.persistence;

import dam.code.repository.PeliculaRepository;

public class JsonManager implements PeliculaRepository {
    private static final String FILENAME = "peliculas.json";
    private static final Gson GSON = new Gson();

    @Override
    public void guardar
}
