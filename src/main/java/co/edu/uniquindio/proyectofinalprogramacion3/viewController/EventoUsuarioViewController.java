package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.controller.ModelFactoryController;
import co.edu.uniquindio.agencia20241.mapping.dto.EventoDto;
import co.edu.uniquindio.agencia20241.mapping.dto.ReservaDto;
import co.edu.uniquindio.agencia20241.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.agencia20241.model.Eventos;
import co.edu.uniquindio.agencia20241.model.Reserva;
import co.edu.uniquindio.agencia20241.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.UUID;

public class EventoUsuarioViewController {

    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    private ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();
    private ObservableList<EventoDto> listaEventosReservas = FXCollections.observableArrayList();

    private InicioSesionController inicioSesionController = new InicioSesionController();
    public EventoDto eventoSeleccionado;

    @FXML
    private Button btnReservar;

    @FXML
    private TableColumn<EventoDto, String> colCantidadMaximaEvento;

    @FXML
    private TableColumn<EventoDto, String> colDescripcionEvento;

    @FXML
    private TableColumn<EventoDto, String> colFechaEvento;

    @FXML
    private TableColumn<EventoDto, String> colHoraEvento;

    @FXML
    private TableColumn<EventoDto, String> colNombreEvento;

    @FXML
    private TableColumn<EventoDto, String> colReservas;

    @FXML
    private TableColumn<EventoDto, String> colUbicacionEvento;

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
        tabla.setItems(listaEventosReservas);
        listenerSelection();
    }

    private void initDataBinding() {
        colNombreEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().nombreEvento()));
        colDescripcionEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().descripcionEvento()));
        colFechaEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().fechaEvento()));
        colHoraEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().horaEvento()));
        colUbicacionEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().ubicacionEvento()));
        colCantidadMaximaEvento.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(getCapacidadMaxima(cell.getValue().nombreEvento()))));
        colReservas.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().capacidadMaximaEvento())));
    }

    private void obtenerEventos() {
        listaEventosDto.addAll(AgenciaMapper.INSTANCE.getEventosDto(modelFactoryController.obtenerEventos()));
        listaEventosReservas.addAll(listaEventosDto);
    }

    private int getCapacidadMaxima(String nombreEvento) {
        for (EventoDto evento : listaEventosDto) {
            if (evento.nombreEvento().equals(nombreEvento)) {
                return evento.capacidadMaximaEvento();
            }
        }
        return 0; // Valor predeterminado en caso de que no se encuentre el evento
    }

    private void listenerSelection() {
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            eventoSeleccionado = newSelection;
        });
    }

    @FXML
    public void reservar(ActionEvent event) {
        EventoDto eventoSeleccionado = tabla.getSelectionModel().getSelectedItem();
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

        int reservasDisponibles = eventoSeleccionado.capacidadMaximaEvento();
        if (cantidadReservas > reservasDisponibles) {
            mostrarMensaje("Error", "No hay suficientes reservas disponibles.", "La cantidad de reservas disponibles es: " + reservasDisponibles, Alert.AlertType.ERROR);
            return;
        }

        // Actualizar las reservas del evento
        int nuevasReservas = reservasDisponibles - cantidadReservas;
        EventoDto eventoActualizado = new EventoDto(
                eventoSeleccionado.nombreEvento(),
                eventoSeleccionado.descripcionEvento(),
                eventoSeleccionado.fechaEvento(),
                eventoSeleccionado.horaEvento(),
                nuevasReservas,
                eventoSeleccionado.ubicacionEvento()
        );

        // Actualizar la lista observable de reservas
        listaEventosReservas.set(listaEventosReservas.indexOf(eventoSeleccionado), eventoActualizado);

        // Actualizar el objeto Evento correspondiente en el modelo
        Eventos eventoOriginal = modelFactoryController.obtenerEvento(eventoSeleccionado.nombreEvento());
        if (eventoOriginal != null) {
            eventoOriginal.setCapacidadMaximaEvento(nuevasReservas);
        }

        tabla.refresh();

        // Generar ID para la reserva
        String idReserva = UUID.randomUUID().toString();

        // Obtener el ID del usuario autenticado
        String usuarioId = inicioSesionController.idUsuarioAutenticado;
        Usuario usuario = modelFactoryController.buscarUsuario(usuarioId);

        // Crear nueva Reserva y agregarla al modelo
        String estado = "p";

        // Crear ReservaDto
        ReservaDto reservaDto = new ReservaDto(idReserva, usuario, eventoOriginal, LocalDate.now(), estado);

       // Reserva reserva = AgenciaMapper.INSTANCE.ReservaDtoToreserva(reservaDto);

        // Añadir reserva a la lista del usuario
//usuario.getListaReservas().add(reserva);

        modelFactoryController.agregarReserva(idReserva, usuario, eventoOriginal, LocalDate.now(), estado);

        mostrarMensaje("Reserva Realizada", "Reserva realizada con éxito.", "Se han reservado " + cantidadReservas + " espacios para el evento " + eventoSeleccionado.nombreEvento() + ".", Alert.AlertType.CONFIRMATION);
        txtCantidadReservas.setText("");
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
