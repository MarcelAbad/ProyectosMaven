package dam.code.controller;

import dam.code.exceptions.PeliculaException;
import dam.code.model.Pelicula;
import dam.code.service.PeliculaService;
import dam.code.service.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PeliculaController {

    @FXML
    private TableView<Pelicula> tablaPeliculas;
    @FXML
    private TableColumn<Pelicula, String> colId;
    @FXML
    private TableColumn<Pelicula, String> colTitulo;
    @FXML
    private TableColumn<Pelicula, String> colDirector;
    @FXML
    private TableColumn<Pelicula, Integer> colDuracion;
    @FXML
    private TableColumn<Pelicula, LocalDate> colFechaPublicacion;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtDirector;
    @FXML
    private TextField txtDuracion;
    @FXML
    private TextField txtFechaPublicacion;

    public void setPeliculaService(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
        tablaPeliculas.setItems(peliculaService.getPeliculas());
    }

    @FXML
    private void initialize() {
        prefWidthColumns();

        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        colDirector.setCellValueFactory(cellData -> cellData.getValue().directorProperty());
        colDuracion.setCellValueFactory(cellData -> cellData.getValue().duracionProperty().asObject());
        colFechaPublicacion.setCellValueFactory(cellData -> cellData.getValue().fehaPublicaionProperty());

        tablaPeliculas.setEditable(true);

        //editar nombre y fecha

        colTitulo.setCellValueFactory(TextFieldTableCell.forTableColumn(new StringConverter()));
        colTitulo.setOnEditCommit(event -> {
            Pelicula pelicula = (Pelicula) event.getRowValue();
            String nuevoTitulo = event.getNewValue();
            try {
                peliculaService.actualizarTitulo(pelicula, nuevoTitulo);
            } catch (PeliculaException e) {
                mostrarError(e.getMessage());
            }
        });

        colFechaPublicacion.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        colFechaPublicacion.setOnEditCommit(event -> {
            Pelicula pelicula = event.getRowValue();
            LocalDate fechaPublicacion = event.getNewValue();
            try {
                peliculaService.actualizarFechaPublicacion(pelicula, fechaPublicacion);
            } catch (PeliculaException e) {
                mostrarError(e.getMessage());
            }
        });
    }

    private void prefWidthColumns() {
        tablaPeliculas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colId.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.2));
        colTitulo.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.2));
        colDirector.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.2));
        colDuracion.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.2));
        colFechaPublicacion.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.2));
    }

    @FXML
    private void addPelicula() {
        try {
            Pelicula pelicula = new Pelicula(
                    txtId.getText(),
                    txtTitulo.getText(),
                    txtDirector.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    LocalDate.parse(txtFechaPublicacion.getText())
            );

            peliculaService.agregarPelicula(pelicula);
            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarError("Precio y Stock deben ser numeros validos");
        } catch (PeliculaException | DateTimeParseException e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Datos incorrectos");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtTitulo.clear();
        txtDirector.clear();
        txtDuracion.clear();
        txtFechaPublicacion.clear();
    }

    @FXML
    private void eliminarPelicula() {

        Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {
            try{
                peliculaService.eliminarPelicula(seleccionada);
            } catch (PeliculaException e) {
                mostrarError(e.getMessage());
            }
        }
    }
}
