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

/**
 * Clase principal de la aplicacion. Gestiona la navegacion entre pantallas.
 * Decide si mostrar el registro o el inicio segun si ya hay usuarios.
 * @author Marcel Abad
 * @version 1.0
 */
public class AppPeliculas extends Application {

    private Stage stage;
    private RegistroService registroService;
    private JsonManager jsonManager;

    /**
     * Punto de entrada de JavaFX. Inicializa los servicios y muestra la primera pantalla.
     * @param stage ventana principal de la aplicacion
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.jsonManager = new JsonManager();
        this.registroService = new RegistroService(new RegistroDAO());

        stage.setTitle("Gestion de Películas");
        stage.setResizable(false);

        if (registroService.existenUsuarios()) {
            mostrarInicio();
        } else {
            mostrarRegistro();
        }

        stage.show();
    }

    /**
     * Carga y muestra la pantalla de registro de usuarios.
     */
    private void mostrarRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registro.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            RegistroController controller = loader.getController();
            controller.setRegistroService(registroService);
            controller.setOnRegistroExitoso(this::mostrarInicio);
            controller.setIrIncio(this::mostrarInicio);

            stage.setScene(scene);

        } catch (IOException e) {
            mostrarErrorFatal("Error al cargar la vista de registro: " + e.getMessage());
        }
    }

    /**
     * Carga y muestra la pantalla de inicio de sesion.
     */
    private void mostrarInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inicio.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            InicioController controller = loader.getController();
            controller.setRegistroService(registroService);
            controller.setInicioExitoso(this::mostrarGestion);
            controller.setIrRegistro(this::mostrarRegistro);

            stage.setScene(scene);

        } catch (IOException e) {
            mostrarErrorFatal("Error al cargar la vista de inicio de sesión: " + e.getMessage());
        }
    }

    /**
     * Carga y muestra la pantalla principal de gestion de peliculas.
     * Crea el repositorio y servicio con el usuario que ha iniciado sesion.
     */
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
                mostrarInicio();
            });

            PeliculaController controller = loader.getController();
            controller.setPeliculaService(peliculaService);

            stage.setScene(scene);

        } catch (IOException e) {
            mostrarErrorFatal("Error al cargar la vista de gestión: " + e.getMessage());
        }
    }

    /**
     * Muestra una alerta de error grave y cierra la aplicacion.
     * @param mensaje descripcion del error
     */
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

    /**
     * Metodo main que lanza la aplicacion JavaFX.
     * @param args argumentos de la linea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}