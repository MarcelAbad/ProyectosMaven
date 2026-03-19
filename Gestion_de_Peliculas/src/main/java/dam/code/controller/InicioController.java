package dam.code.controller;

import dam.code.exceptions.PersonaException;
import dam.code.service.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InicioController {

    private RegistroService registroService;
    private Runnable InicioExitoso;
    private Runnable irRegistro;

    @FXML private TextField txtDni;
    @FXML private PasswordField txtContrasena;

    public void setRegistroService(RegistroService registroService) {
        this.registroService = registroService;
    }

    public void setInicioExitoso(Runnable InicioExitoso) {
        this.InicioExitoso = InicioExitoso;
    }

    public void setIrRegistro(Runnable irRegistro) {
        this.irRegistro = irRegistro;
    }

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

    @FXML
    private void irRegistro() {
        if (irRegistro != null) {
            irRegistro.run();
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