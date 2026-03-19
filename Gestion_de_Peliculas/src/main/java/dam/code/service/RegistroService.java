package dam.code.service;

import dam.code.persistence.RegistroDAO;
import dam.code.exceptions.PersonaException;
import dam.code.model.Persona;

import java.util.Map;

/**
 * Servicio que gestiona el registro e inicio de sesion de usuarios.
 * Valida los datos y delega la persistencia al RegistroDAO.
 * @author Marcel Abad
 * @version 1.0
 */
public class RegistroService {

    private final RegistroDAO registroDAO;
    private Map<Persona, String> registros;
    private Persona usuarioActual;

    /**
     * Crea el servicio y carga los registros existentes desde el DAO.
     * @param registroDAO DAO a usar para la persistencia
     */
    public RegistroService(RegistroDAO registroDAO) {
        this.registroDAO = registroDAO;
        this.registros = registroDAO.cargar();
    }

    /**
     * Comprueba si hay usuarios registrados en el sistema.
     * @return true si hay usuarios, false si no
     */
    public boolean existenUsuarios() {
        return registroDAO.existenRegistros();
    }

    /**
     * Registra un nuevo usuario validando sus datos y contrasena.
     * Lanza excepcion si el DNI o email ya estan en uso.
     * @param persona persona a registrar
     * @param contrasena contrasena del usuario
     * @throws PersonaException si los datos no son validos o ya existen
     */
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

    /**
     * Inicia sesion buscando el DNI y comprobando la contrasena.
     * Si es correcto guarda el usuario como usuarioActual.
     * @param dni DNI del usuario
     * @param contrasena contrasena del usuario
     * @throws PersonaException si el DNI no existe o la contrasena es incorrecta
     */
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

    /**
     * Devuelve el usuario que ha iniciado sesion.
     * @return usuarioActual
     */
    public Persona getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Cierra la sesion poniendo usuarioActual a null.
     */
    public void cerrarSesion() {
        usuarioActual = null;
    }

    /**
     * Valida todos los campos de una persona antes de registrarla.
     * @param persona persona a validar
     * @throws PersonaException si algún campo es invalido
     */
    private void validarPersona(Persona persona) throws PersonaException {
        if (persona.getDni() == null || persona.getDni().isBlank()) {
            throw new PersonaException("El DNI no puede estar vacío");
        }
        if (!persona.getDni().matches("\\d{8}[A-Z]")) {
            throw new PersonaException("El DNI debe tener 8 numeros y 1 letra");
        }
        if (persona.getNombre() == null || persona.getNombre().isBlank()) {
            throw new PersonaException("El nombre no puede estar vacio");
        }
        if (persona.getApellido() == null || persona.getApellido().isBlank()) {
            throw new PersonaException("El apellido no puede estar vacio");
        }
        if (persona.getEmail() == null || persona.getEmail().isBlank()) {
            throw new PersonaException("El email no puede estar vacio");
        }
        if (!persona.getEmail().matches(".*[@.].*")) {
            throw new PersonaException("El email no tiene un formato valido");
        }
    }

    /**
     * Valida que la contrasena no sea nula y tenga al menos 6 caracteres.
     * @param contrasena contrasena a validar
     * @throws PersonaException si la contrasena no cumple los requisitos
     */
    private void validarContrasena(String contrasena) throws PersonaException {
        if (contrasena == null || contrasena.isBlank()) {
            throw new PersonaException("La contraseña no puede estar vacia");
        }
        if (contrasena.length() < 6) {
            throw new PersonaException("La contraseña debe tener al menos 6 caracteres");
        }
    }
}