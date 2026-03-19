package dam.code.controller;

import dam.code.exceptions.PersonaException;
import dam.code.service.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador de la pantalla de inicio de sesion.
 * Gestiona el login y la navegacion al registro.
 * @author Marcel Abad
 * @version 1.0
 */
public class InicioController {

    private RegistroService registroService;
    private Runnable InicioExitoso;
    private Runnable irRegistro;

    @FXML private TextField txtDni;
    @FXML private PasswordField txtContrasena;

    /**
     * Asigna el servicio de registro al controlador.
     * @param registroService servicio a usar
     */
    public void setRegistroService(RegistroService registroService) {
        this.registroService = registroService;
    }

    /**
     * Asigna la accion a ejecutar cuando el inicio es exitoso.
     * @param InicioExitoso accion a ejecutar
     */
    public void setInicioExitoso(Runnable InicioExitoso) {
        this.InicioExitoso = InicioExitoso;
    }

    /**
     * Asigna la accion para navegar a la pantalla de registro.
     * @param irRegistro accion a ejecutar
     */
    public void setIrRegistro(Runnable irRegistro) {
        this.irRegistro = irRegistro;
    }

    /**
     * Recoge el DNI y contrasena del formulario e intenta iniciar sesion.
     * Si falla muestra un mensaje de error.
     */
    @FXML
    private void iniciarSesion() {
        String dni = txtDni.getText().toUpperCase();
        String contrasena = txtContrasena.getText();

        try {
            registroService.iniciarSesion(dni, contrasena);
            if (InicioExitoso != null) {
                InicioExitoso.run();
            }
        } catch (PersonaException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Navega a la pantalla de registro si la accion esta definida.
     */
    @FXML
    private void irRegistro() {
        if (irRegistro != null) {
            irRegistro.run();
        }
    }

    /**
     * Muestra una alerta de error con el mensaje indicado.
     * @param mensaje texto a mostrar en la alerta
     */
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error al iniciar sesion");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}