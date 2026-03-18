package dam.code.service;

import dam.code.persistence.RegistroDAO;
import dam.code.exceptions.PersonaException;
import dam.code.model.Persona;

import java.util.Map;

public class RegistroService {

    private final RegistroDAO registroDAO;
    private Map<Persona, String> registros;
    private Persona usuarioActual;

    public RegistroService(RegistroDAO registroDAO) {
        this.registroDAO = registroDAO;
        this.registros = registroDAO.cargar();
    }

    public boolean existenUsuarios() {
        return registroDAO.existenRegistros();
    }

    public void registrar(Persona persona, String contrasena) throws PersonaException {
        validarPersona(persona);
        validarContrasena(contrasena);

        boolean dniExiste = registros.keySet().stream()
                .anyMatch(p -> p.getDni().equals(persona.getDni()));
        if (dniExiste) {
            throw new PersonaException("Ya existe un usuario con ese DNI");
        }

        boolean emailExiste = registros.keySet().stream()
                .anyMatch(p -> p.getEmail().equals(persona.getEmail()));
        if (emailExiste) {
            throw new PersonaException("Ya existe un usuario con ese email");
        }

        registros.put(persona, contrasena);
        registroDAO.guardar(registros);
    }

    public void iniciarSesion(String dni, String contrasena) throws PersonaException {
        if (dni == null || dni.isBlank()) {
            throw new PersonaException("El DNI no puede estar vacío");
        }
        if (contrasena == null || contrasena.isBlank()) {
            throw new PersonaException("La contraseña no puede estar vacía");
        }

        Persona persona = registros.keySet().stream()
                .filter(p -> p.getDni().equals(dni))
                .findFirst()
                .orElseThrow(() -> new PersonaException("No existe ningún usuario con ese DNI"));

        if (!registros.get(persona).equals(contrasena)) {
            throw new PersonaException("Contraseña incorrecta");
        }

        usuarioActual = persona;
    }

    public Persona getUsuarioActual() {
        return usuarioActual;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

    private void validarPersona(Persona persona) throws PersonaException {
        if (persona.getDni() == null || persona.getDni().isBlank()) {
            throw new PersonaException("El DNI no puede estar vacío");
        }
        if (!persona.getDni().matches("\\d{8}[a-zA-Z]")) {
            throw new PersonaException("El DNI debe tener 8 números y 1 letra");
        }
        if (persona.getNombre() == null || persona.getNombre().isBlank()) {
            throw new PersonaException("El nombre no puede estar vacío");
        }
        if (persona.getApellido() == null || persona.getApellido().isBlank()) {
            throw new PersonaException("El apellido no puede estar vacío");
        }
        if (persona.getEmail() == null || persona.getEmail().isBlank()) {
            throw new PersonaException("El email no puede estar vacío");
        }
        if (!persona.getEmail().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new PersonaException("El email no tiene un formato válido");
        }
    }

    private void validarContrasena(String contrasena) throws PersonaException {
        if (contrasena == null || contrasena.isBlank()) {
            throw new PersonaException("La contraseña no puede estar vacía");
        }
        if (contrasena.length() < 6) {
            throw new PersonaException("La contraseña debe tener al menos 6 caracteres");
        }
    }
}

