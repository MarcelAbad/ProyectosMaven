package dam.code.controller;

import dam.code.exceptions.PersonaException;
import dam.code.model.Persona;
import dam.code.service.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController {

    private RegistroService registroService;
    private Runnable onRegistroExitoso;
    private Runnable onIrALogin;

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtContrasena;

    public void setRegistroService(RegistroService registroService) {
        this.registroService = registroService;
    }

    public void setOnRegistroExitoso(Runnable onRegistroExitoso) {
        this.onRegistroExitoso = onRegistroExitoso;
    }

    public void setOnIrALogin(Runnable onIrALogin) {
        this.onIrALogin = onIrALogin;
    }

    @FXML
    private void registrar() {
        try {
            Persona persona = new Persona(
                    txtDni.getText(),
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

    @FXML
    private void irALogin() {
        if (onIrALogin != null) {
            onIrALogin.run();
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error al registrar usuario");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Registro");
        alerta.setHeaderText("Registro exitoso");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtDni.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtContrasena.clear();
    }
}