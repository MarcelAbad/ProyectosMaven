package dam.code.controller;

import dam.code.service.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegistroController {

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtEmail;
    @FXML private TextField txtContraseña;

    private RegistroService registroService;

    public void setRegistroService(RegistroService registroService) {
        this.registroService = registroService;

    }

}
