package dam.code.controller;

import dam.code.exceptions.PeliculaException;
import dam.code.model.Pelicula;
import dam.code.service.PeliculaService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Controlador de la pantalla principal de peliculas.
 * Lo que hace es gestionar la tabla, el formulario y las acciones del usuario.
 * @author Marcel Abad
 * @version 1.0
 */
public class PeliculaController {

    private PeliculaService peliculaService;

    @FXML private TableView<Pelicula> tablaPeliculas;
    @FXML private TableColumn<Pelicula, String> colId;
    @FXML private TableColumn<Pelicula, String> colTitulo;
    @FXML private TableColumn<Pelicula, String> colDirector;
    @FXML private TableColumn<Pelicula, Integer> colDuracion;
    @FXML private TableColumn<Pelicula, LocalDate> colFechaPublicacion;

    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDirector;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtFechaPublicacion;

    /**
     * Asigna el servicio y carga la lista de peliculas en la tabla.
     * @param peliculaService servicio a usar
     */
    public void setPeliculaService(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
        tablaPeliculas.setItems(peliculaService.getPeliculas());
    }

    /**
     * Se ejecuta automaticamente al cargar la pantalla.
     * Configura las columnas, la edicion inline y el doble clic para visualizaciones.
     */
    @FXML
    private void initialize() {
        prefWidthColumns();

        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        colDirector.setCellValueFactory(cellData -> cellData.getValue().directorProperty());
        colDuracion.setCellValueFactory(cellData -> cellData.getValue().duracionProperty().asObject());
        colFechaPublicacion.setCellValueFactory(cellData -> cellData.getValue().fechaPublicacionProperty());

        tablaPeliculas.setEditable(true);

        colTitulo.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        colTitulo.setOnEditCommit(event -> {
            Pelicula pelicula = event.getRowValue();
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

        tablaPeliculas.setRowFactory(tv -> {
            TableRow<Pelicula> fila = new TableRow<>();
            fila.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !fila.isEmpty()) {
                    Pelicula pelicula = fila.getItem();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Visualizacion");
                    alert.setHeaderText("Añadir visualizacion");
                    alert.setContentText("¿Deseas añadir una visualizacion a: " + pelicula.getTitulo() + "?");
                    alert.showAndWait().ifPresent(respuesta -> {
                        if (respuesta == ButtonType.OK) {
                            try {
                                peliculaService.agregarVisualizacion(pelicula);
                            } catch (PeliculaException e) {
                                mostrarError(e.getMessage());
                            }
                        }
                    });
                }
            });
            return fila;
        });
    }

    /**
     * Ajusta el ancho de cada columna al 20% del ancho total de la tabla.
     */
    private void prefWidthColumns() {
        tablaPeliculas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        colId.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.2));
        colTitulo.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.2));
        colDirector.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.2));
        colDuracion.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.2));
        colFechaPublicacion.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.2));
    }

    /**
     * Lee los campos del formulario y añade una nueva pelicula.
     * Muestra error si los datos son incorrectos.
     */
    @FXML
    private void addPelicula() {
        try {
            Pelicula pelicula = new Pelicula(
                    txtId.getText().toUpperCase(),
                    txtTitulo.getText(),
                    txtDirector.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    LocalDate.parse(txtFechaPublicacion.getText())
            );
            peliculaService.agregarPelicula(pelicula);
            limpiarCampos();
        } catch (NumberFormatException e) {
            mostrarError("La duracion debe ser un numero valido");
        } catch (PeliculaException | DateTimeParseException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Elimina la pelicula seleccionada en la tabla.
     * Muestra error si no se puede eliminar.
     */
    @FXML
    private void eliminarPelicula() {
        Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            try {
                peliculaService.eliminarPelicula(seleccionada);
            } catch (PeliculaException e) {
                mostrarError(e.getMessage());
            }
        }
    }

    /**
     * Cierra la sesion del usuario actual.
     */
    @FXML
    private void cerrarSesion() {
        peliculaService.cerrarSesion();
    }

    /**
     * Muestra una alerta de error con el mensaje indicado.
     * @param mensaje texto a mostrar en la alerta
     */
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Datos incorrectos");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Vacia todos los campos del formulario.
     */
    private void limpiarCampos() {
        txtId.clear();
        txtTitulo.clear();
        txtDirector.clear();
        txtDuracion.clear();
        txtFechaPublicacion.clear();
    }
}