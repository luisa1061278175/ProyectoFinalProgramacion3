package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.controller.ControllerManager;
import co.edu.uniquindio.agencia20241.controller.service.ActionObserver;
import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class EstadoReservaAdminViewController implements ActionObserver {

    private ControllerManager controllerManager = ControllerManager.getInstance();

    @FXML
    private TableView<ReservaDto> tabla;

    @FXML
    private TableColumn<ReservaDto, String> colEstado;

    @FXML
    private TableColumn<ReservaDto, String> colNombreEvento;

    @FXML
    private TableColumn<ReservaDto, String> colIdUsuario;

    @FXML
    private TableColumn<ReservaDto, String> colIdReserva;

    @FXML
    private TableColumn<ReservaDto, String> colFechaSolicitud;

    @FXML
    void initialize() {
        controllerManager.addObserver(this);
        initView();
    }

    private void initView() {
        initDataBinding();
        cargarReservasEnTabla();
    }

    private void initDataBinding() {
        colEstado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().estadoReserva()));
        colNombreEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().evento().getNombreEvento()));
        colIdReserva.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().id()));
        colFechaSolicitud.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().fechaSolicitud().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        colIdUsuario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().usuario().getId()));
    }

    private void cargarReservasEnTabla() {
        List<ReservaDto> reservas = controllerManager.getReservas();
        ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList(reservas);
        tabla.setItems(listaReservasDto);
        System.out.println("Reservas cargadas: " + listaReservasDto);
    }

    @Override
    public void onActionPerformed() {
        cargarReservasEnTabla();
        tabla.refresh();
    }
}
