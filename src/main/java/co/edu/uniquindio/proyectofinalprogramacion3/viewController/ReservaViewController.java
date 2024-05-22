package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.controller.ControllerManager;
import co.edu.uniquindio.agencia20241.controller.ModelFactoryController;
import co.edu.uniquindio.agencia20241.controller.service.ActionObserver;
import co.edu.uniquindio.agencia20241.exception.*;
import co.edu.uniquindio.agencia20241.mapping.dto.EventoDto;
import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.agencia20241.model.Eventos;
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

public class ReservaViewController extends Application implements ActionObserver {
    private ControllerManager controllerManager;

    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
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
        controllerManager = ControllerManager.getInstance();
        controllerManager.addObserver(this);
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
        try {
            crearReserva();
        } catch (NoEventoSeleccionadoException | CantidadReservasVaciaException | CantidadReservasInvalidaException |
                 UsuarioNoAutenticadoException | CapacidadMaximaExcedidaException e) {
            mostrarMensaje("Error", e.getMessage(), "", Alert.AlertType.ERROR);
        }
    }

    private void crearReserva() throws ReservaException, EventoException, NoEventoSeleccionadoException,
            CantidadReservasVaciaException, CantidadReservasInvalidaException, UsuarioNoAutenticadoException,
            CapacidadMaximaExcedidaException {

        if (eventoSeleccionado == null) {
            throw new NoEventoSeleccionadoException("No se ha seleccionado ningún evento. Por favor, seleccione un evento para reservar.");
        }

        String cantidadReservasStr = txtCantidadReservas.getText();
        if (cantidadReservasStr.isEmpty()) {
            throw new CantidadReservasVaciaException("Cantidad de reservas vacía. Por favor, ingrese la cantidad de reservas que desea realizar.");
        }

        int cantidadReservas;
        try {
            cantidadReservas = Integer.parseInt(cantidadReservasStr);
        } catch (NumberFormatException e) {
            throw new CantidadReservasInvalidaException("Cantidad de reservas inválida. Por favor, ingrese un número válido.");
        }

        if (cantidadReservas <= 0) {
            throw new CantidadReservasInvalidaException("Cantidad de reservas inválida. La cantidad de reservas debe ser mayor a cero.");
        }

        if (cantidadReservas > eventoSeleccionado.capacidadMaximaEvento()) {
            throw new CapacidadMaximaExcedidaException("No hay suficientes reservas disponibles. La cantidad de reservas disponibles es: " + eventoSeleccionado.capacidadMaximaEvento());
        }

        String idReserva = UUID.randomUUID().toString();
        String usuarioId = inicioSesionController.idUsuarioAutenticado;

        if (usuarioId == null) {
            throw new UsuarioNoAutenticadoException("Usuario no autenticado. Por favor, inicie sesión para realizar una reserva.");
        }

        Eventos evento = AgenciaMapper.INSTANCE.eventoDtoToEvento(eventoSeleccionado);

        modelFactoryController.agregarReserva(idReserva, modelFactoryController.obtenerUsuario(usuarioId), evento, LocalDate.now(), mensajeReserva(eventoSeleccionado.capacidadMaximaEvento(), cantidadReservas));

        ReservaDto reservaDto = construirReservaDto(idReserva, usuarioId, evento, cantidadReservas);

        listaReservasDto.add(reservaDto);
        modelFactoryController.eliminarEvento(evento.getNombreEvento());

        evento.setCapacidadMaximaEvento(evento.getCapacidadMaximaEvento() - cantidadReservas);
        modelFactoryController.crearEvento(evento.getNombreEvento(), evento.getDescripcionEvento(), evento.getFechaEvento(), evento.getHoraEvento(), evento.getUbicacionEvento(), evento.getCapacidadMaximaEvento());

        actualizarListaEventosConNuevoEvento(evento);
        txtCantidadReservas.clear();

        mostrarMensaje("Éxito", "Reserva realizada.", "Su reserva ha sido realizada con éxito.", Alert.AlertType.INFORMATION);

        controllerManager.addReserva(reservaDto);
    }

    private void actualizarListaEventosConNuevoEvento(Eventos evento) {
        EventoDto nuevoEventoDto = AgenciaMapper.INSTANCE.eventoToEventoDto(evento);
        listaEventosDto.removeIf(eventoDto -> eventoDto.nombreEvento().equals(nuevoEventoDto.nombreEvento()));
        listaEventosDto.add(nuevoEventoDto);
        tabla.refresh();
    }

    private String mensajeReserva(int capacidadMaxima, int cantidadReservas) {
        String estado = "";
        if (verificarDisponibilidadReservas(cantidadReservas, capacidadMaxima)) {
            estado = "Aceptado";
            return estado + cantidadReservas + " reservas " ;
        }
        estado = "Rechazado";

        return estado + " :reservas: " + cantidadReservas;
    }

    public boolean verificarDisponibilidadReservas(int reservas, int maximoReservas) {
        if (reservas > maximoReservas) {
            return false;
        }

        return true;
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private ReservaDto construirReservaDto(String idReserva, String usuarioId, Eventos evento, int cantidadReservas) {
        return new ReservaDto(idReserva, modelFactoryController.obtenerUsuario(usuarioId), evento, LocalDate.now(), mensajeReserva(eventoSeleccionado.capacidadMaximaEvento(), cantidadReservas));
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Implementar según sea necesario
    }

    @Override
    public void onActionPerformed() {
        // Implementar si es necesario
    }
}
