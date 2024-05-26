package co.edu.uniquindio.proyectofinalprogramacion3.viewController;

import co.edu.uniquindio.proyectofinalprogramacion3.controller.ControllerManager;
import co.edu.uniquindio.proyectofinalprogramacion3.controller.ModelFactoryController;
import co.edu.uniquindio.proyectofinalprogramacion3.exception.EventoException;
import co.edu.uniquindio.proyectofinalprogramacion3.exception.ReservaException;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.EventoDto;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.ReservaDto;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Eventos;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.UUID;

import static co.edu.uniquindio.proyectofinalprogramacion3.utils.Constantes.QUEUE_NUEVA_PUBLICACION;

public class ReservaViewController extends Application {

    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    private ControllerManager controllerManager = ControllerManager.getInstance();

    public ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();
    private ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList();
    private EventoDto eventoSeleccionado;
    private InicioSesionController inicioSesionController = new InicioSesionController();

    @FXML
    private Button btnReservar;

    @FXML
    private TableColumn<EventoDto, String> colCantidadMaximaEventos;

    @FXML
    private TableColumn<EventoDto, String> colDescripcionEventos;

    @FXML
    private TableColumn<EventoDto, String> colFechaEventos;

    @FXML
    private TableColumn<EventoDto, String> colHoraEventos;

    @FXML
    private TableColumn<EventoDto, String> colUbicacionEventos;

    @FXML
    private TableView<EventoDto> tabla;

    @FXML
    private TextField txtCantidadReservas;

    @FXML
    void initialize() {
        initView();
    }

    @FXML
    private void initView() {
        initDataBinding();
        obtenerEventos();
        tabla.getItems().clear();
        tabla.setItems(listaEventosDto);
        listenerSelection();
    }

    private void initDataBinding() {
        colCantidadMaximaEventos.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().capacidadMaximaEvento())));
        colDescripcionEventos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().descripcionEvento()));
        colFechaEventos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().fechaEvento()));
        colHoraEventos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().horaEvento()));
        colUbicacionEventos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().ubicacionEvento()));
    }

    private void obtenerEventos() {
        listaEventosDto.clear();
        listaEventosDto.addAll(AgenciaMapper.INSTANCE.getEventosDto(modelFactoryController.obtenerEventos()));
    }

    private void listenerSelection() {
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            eventoSeleccionado = newSelection;
        });
    }

    @FXML
    private void reservar(ActionEvent event) throws ReservaException, EventoException {
        crearReserva();
    }

    private void crearReserva() throws ReservaException, EventoException {
        if (eventoSeleccionado == null) {
            mostrarMensaje("Error", "No se ha seleccionado ningún evento.", "Por favor, seleccione un evento para reservar.", Alert.AlertType.ERROR);
            return;
        }

        String cantidadReservasStr = txtCantidadReservas.getText();
        if (cantidadReservasStr.isEmpty()) {
            mostrarMensaje("Error", "Cantidad de reservas vacía.", "Por favor, ingrese la cantidad de reservas que desea realizar.", Alert.AlertType.ERROR);
            return;
        }

        int cantidadReservas;
        try {
            cantidadReservas = Integer.parseInt(cantidadReservasStr);
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Cantidad de reservas inválida.", "Por favor, ingrese un número válido.", Alert.AlertType.ERROR);
            return;
        }

        if (cantidadReservas <= 0) {
            mostrarMensaje("Error", "Cantidad de reservas inválida.", "La cantidad de reservas debe ser mayor a cero.", Alert.AlertType.ERROR);
            return;
        }

        if (cantidadReservas > eventoSeleccionado.capacidadMaximaEvento()) {
            mostrarMensaje("Error", "No hay suficientes reservas disponibles.", "La cantidad de reservas disponibles es: " + eventoSeleccionado.capacidadMaximaEvento(), Alert.AlertType.ERROR);
            return;
        }


        String idReserva = UUID.randomUUID().toString();
        String usuarioId = inicioSesionController.idUsuarioAutenticado;

        if (usuarioId == null) {
            mostrarMensaje("Error", "Usuario no autenticado.", "Por favor, inicie sesión para realizar una reserva.", Alert.AlertType.ERROR);
            return;
        }


        Eventos evento = AgenciaMapper.INSTANCE.eventoDtoToEvento(eventoSeleccionado);

        modelFactoryController.agregarReserva(idReserva, modelFactoryController.obtenerUsuario(usuarioId), evento, LocalDate.now(), mensajeReserva(eventoSeleccionado.capacidadMaximaEvento(), cantidadReservas));

        ReservaDto reservaDto = construirReservaDto(idReserva, usuarioId, evento, cantidadReservas);


        controllerManager.addReserva(reservaDto);

        listaReservasDto.add(reservaDto);


        modelFactoryController.eliminarEvento(evento.getNombreEvento());


        evento.setCapacidadMaximaEvento(evento.getCapacidadMaximaEvento() - cantidadReservas);
        modelFactoryController.crearEvento(evento.getNombreEvento(), evento.getDescripcionEvento(), evento.getFechaEvento(), evento.getHoraEvento(), evento.getUbicacionEvento(), evento.getCapacidadMaximaEvento());


        actualizarListaEventosConNuevoEvento(evento);
        txtCantidadReservas.clear();

        mostrarMensaje("Éxito", "Reserva realizada.", "Su reserva ha sido realizada con éxito.", Alert.AlertType.INFORMATION);
        ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
        String mensaje = "";
        mensaje += "100;";
        mensaje += "LUISA";
        modelFactoryController.producirMensaje(QUEUE_NUEVA_PUBLICACION, mensaje);
    }

    private void actualizarListaEventosConNuevoEvento(Eventos evento) {

        EventoDto nuevoEventoDto = AgenciaMapper.INSTANCE.eventoToEventoDto(evento);

        listaEventosDto.removeIf(eventoDto -> eventoDto.nombreEvento().equals(nuevoEventoDto.nombreEvento()));

        listaEventosDto.add(nuevoEventoDto);

        tabla.refresh();
    }

    private String mensajeReserva(int capacidadMaxima, int cantidadReservas) {
        return cantidadReservas == 1 ? "Se ha realizado 1 reserva." : "Se han realizado " + cantidadReservas + " reservas.";
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private ReservaDto construirReservaDto(String idReserva, String usuarioId, Eventos evento, int cantidadReservas) {
        return new ReservaDto(
                idReserva, modelFactoryController.obtenerUsuario(usuarioId), evento, LocalDate.now(), mensajeReserva(eventoSeleccionado.capacidadMaximaEvento(), cantidadReservas)
        );
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
