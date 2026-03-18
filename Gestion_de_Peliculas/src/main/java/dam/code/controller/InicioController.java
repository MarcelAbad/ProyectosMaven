package dam.code.controller;

import dam.code.exceptions.PersonaException;
import dam.code.service.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InicioController {

    private RegistroService registroService;
    private Runnable onLoginExitoso;
    private Runnable onIrARegistro;

    @FXML private TextField txtDni;
    @FXML private PasswordField txtContrasena;

    public void setRegistroService(RegistroService registroService) {
        this.registroService = registroService;
    }

    public void setOnLoginExitoso(Runnable onLoginExitoso) {
        this.onLoginExitoso = onLoginExitoso;
    }

    public void setOnIrARegistro(Runnable onIrARegistro) {
        this.onIrARegistro = onIrARegistro;
    }

    @FXML
    private void iniciarSesion() {
        String dni = txtDni.getText();
        String contrasena = txtContrasena.getText();

        try {
            registroService.iniciarSesion(dni, contrasena);
            if (onLoginExitoso != null) {
                onLoginExitoso.run();
            }
        } catch (PersonaException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void irARegistro() {
        if (onIrARegistro != null) {
            onIrARegistro.run();
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error al iniciar sesion");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}