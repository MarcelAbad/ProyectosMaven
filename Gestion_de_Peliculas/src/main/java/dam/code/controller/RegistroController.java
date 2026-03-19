package dam.code.controller;

import dam.code.exceptions.PersonaException;
import dam.code.model.Persona;
import dam.code.service.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador de la pantalla de registro de usuarios.
 * Gestiona el formulario de registro y la navegacion al inicio.
 * @author Marcel Abad
 * @version 1.0
 */
public class RegistroController {

    private RegistroService registroService;
    private Runnable onRegistroExitoso;
    private Runnable irInicio;

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtContrasena;

    /**
     * Asigna el servicio de registro al controlador.
     * @param registroService servicio a usar
     */
    public void setRegistroService(RegistroService registroService) {
        this.registroService = registroService;
    }

    /**
     * Asigna la accion a ejecutar cuando el registro es exitoso.
     * @param onRegistroExitoso accion a ejecutar
     */
    public void setOnRegistroExitoso(Runnable onRegistroExitoso) {
        this.onRegistroExitoso = onRegistroExitoso;
    }

    /**
     * Asigna la accion para navegar a la pantalla de inicio.
     * @param irInicio accion a ejecutar
     */
    public void setIrIncio(Runnable irInicio) { this.irInicio = irInicio; }

    /**
     * Lee los campos del formulario, crea una Persona y la registra.
     * Muestra un mensaje de exito o error segun el resultado.
     */
    @FXML
    private void registrar() {
        try {
            Persona persona = new Persona(
                    txtDni.getText().toUpperCase(),
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtEmail.getText()
            );

            registroService.registrar(persona, txtContrasena.getText());
            mostrarInfo("Usuario registrado correctamente");
            limpiarCampos();

            if (onRegistroExitoso != null) {
                onRegistroExitoso.run();
            }

        } catch (PersonaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Navega a la pantalla de inicio si la accion esta definida.
     */
    @FXML
    private void irInicio() {
        if (irInicio != null) {
            irInicio.run();
        }
    }

    /**
     * Muestra una alerta de error con el mensaje indicado.
     * @param mensaje texto a mostrar en la alerta
     */
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error al registrar usuario");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Muestra una alerta informativa con el mensaje indicado.
     * @param mensaje texto a mostrar en la alerta
     */
    private void mostrarInfo(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Registro");
        alerta.setHeaderText("Registro exitoso");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Vacia todos los campos del formulario.
     */
    private void limpiarCampos() {
        txtDni.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtContrasena.clear();
    }
}