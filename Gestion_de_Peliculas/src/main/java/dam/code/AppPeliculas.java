package dam.code;

import dam.code.controller.InicioController;
import dam.code.controller.PeliculaController;
import dam.code.controller.RegistroController;
import dam.code.persistence.RegistroDAO;
import dam.code.persistence.JsonManager;
import dam.code.repository.PeliculaRepository;
import dam.code.service.PeliculaService;
import dam.code.service.RegistroService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppPeliculas extends Application {

    private Stage stage;
    private RegistroService registroService;
    private JsonManager jsonManager;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.jsonManager = new JsonManager();
        this.registroService = new RegistroService(new RegistroDAO());

        stage.setTitle("Gestión de Películas");
        stage.setResizable(false);

        if (registroService.existenUsuarios()) {
            mostrarLogin();
        } else {
            mostrarRegistro();
        }

        stage.show();
    }

    private void mostrarRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registro.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            RegistroController controller = loader.getController();
            controller.setRegistroService(registroService);
            controller.setOnRegistroExitoso(this::mostrarLogin);
            controller.setOnIrALogin(this::mostrarLogin);

            stage.setScene(scene);

        } catch (IOException e) {
            mostrarErrorFatal("Error al cargar la vista de registro: " + e.getMessage());
        }
    }

    private void mostrarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inicio.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            InicioController controller = loader.getController();
            controller.setRegistroService(registroService);
            controller.setOnLoginExitoso(this::mostrarGestion);
            controller.setOnIrARegistro(this::mostrarRegistro);

            stage.setScene(scene);

        } catch (IOException e) {
            mostrarErrorFatal("Error al cargar la vista de inicio de sesión: " + e.getMessage());
        }
    }

    private void mostrarGestion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gestion.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            String usuario = registroService.getUsuarioActual().getDni();
            PeliculaRepository peliculaRepository = new PeliculaRepository(jsonManager, usuario);
            PeliculaService peliculaService = new PeliculaService(peliculaRepository);

            peliculaService.setOnCerrarSesion(() -> {
                registroService.cerrarSesion();
                mostrarLogin();
            });

            PeliculaController controller = loader.getController();
            controller.setPeliculaService(peliculaService);

            stage.setScene(scene);

        } catch (IOException e) {
            mostrarErrorFatal("Error al cargar la vista de gestión: " + e.getMessage());
        }
    }

    private void mostrarErrorFatal(String mensaje) {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR
        );
        alerta.setTitle("Error fatal");
        alerta.setHeaderText("Error al cargar la aplicación");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}