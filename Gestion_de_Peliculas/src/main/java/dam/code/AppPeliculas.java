package dam.code;

import dam.code.controller.InicioController;
import dam.code.persistence.JsonManager;
import dam.code.repository.PeliculaRepository;
import dam.code.service.PeliculaService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppPeliculas {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/libreria_view.fxml"));

        Parent root = fxmlLoader.load();
        InicioController controller = fxmlLoader.getController();

        PeliculaRepository repository = new JsonManager();
        PeliculaService service = new PeliculaService(repository);

        controller.setPeliculaService(service);

        stage.setScene(new Scene(root, 800, 600));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

}
