package co.edu.uniquindio.proyectofinalprogramacion3.viewController;


import co.edu.uniquindio.proyectofinalprogramacion3.controller.ModelFactoryController;
import co.edu.uniquindio.proyectofinalprogramacion3.exception.EventoException;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.EventoDto;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.mappers.AgenciaMapper;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Agencia;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Eventos;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Reserva;
import co.edu.uniquindio.proyectofinalprogramacion3.utils.ArchivoUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventoViewController {

    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    private ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();
    private EventoDto eventoSeleccionado;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnRegresar;
    private Agencia agencia;

    @FXML
    private TableColumn<EventoDto, String> colNombreEvento;

    @FXML
    private TableColumn<EventoDto, String> colDescripcionEvento;

    @FXML
    private TableColumn<EventoDto, String> colFechaEvento;

    @FXML
    private TableColumn<EventoDto, String> colHoraEvento;

    @FXML
    private TableColumn<EventoDto, String> colUbicacionEvento;

    @FXML
    private TableColumn<EventoDto, String> colCantidadMaximaEvento;

    @FXML
    private TableView<EventoDto> tabla;

    @FXML
    private TextField txtNombreEvento;

    @FXML
    private TextField txtDescripcionEvento;

    @FXML
    private TextField txtFechaEvento;

    @FXML
    private TextField txtHoraEvento;

    @FXML
    private TextField txtUbicacionEvento;

    @FXML
    private TextField txtCapacidadMaximaEvento;

    @FXML
    void initialize() throws IOException {
        initView();
    }

    @FXML
    private void initView() throws IOException {
        cargarEventosSerializados();
        initDataBindig();
        tabla.getItems().clear();
        tabla.setItems(listaEventosDto);
        listenerSelection();
    }

    private void initDataBindig() throws IOException {
        colNombreEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().nombreEvento()));
        colDescripcionEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().descripcionEvento()));
        colFechaEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().fechaEvento().toString()));
        colHoraEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().horaEvento().toString()));
        colUbicacionEvento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().ubicacionEvento()));
        colCantidadMaximaEvento.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().capacidadMaximaEvento())));

        cargarDatosAgencia();
    }
    private void cargarDatosAgencia() throws IOException {

        String rutaArchivo = "src/main/resources/Persistencia/model.xml";
        Agencia agencia = (Agencia) ArchivoUtil.cargarRecursoSerializadoXML(rutaArchivo);

        List<Eventos> listaEventos = agencia.getListaReservas().stream()
                .map(Reserva::getEvento)
                .collect(Collectors.toList());

        listaEventosDto.clear();
        for (Eventos evento : listaEventos) {
            EventoDto eventoDto = AgenciaMapper.INSTANCE.eventoToEventoDto(evento);
            listaEventosDto.add(eventoDto);
        }


        tabla.setItems(listaEventosDto);
        System.out.println("Datos para la tabla eventos: " + listaEventosDto);
        tabla.refresh();
    }


    private void obtenerEventos() {
        listaEventosDto.addAll(AgenciaMapper.INSTANCE.getEventosDto(modelFactoryController.obtenerEventos()));
    }

    private void cargarEventosSerializados() {
        List<Eventos> eventos = modelFactoryController.obtenerEventos();
        System.out.println("Eventos obtenidos: " + eventos.size());
        for (Eventos evento : eventos) {
            System.out.println("Evento: " + evento.getNombreEvento());
        }
        listaEventosDto.clear();
        listaEventosDto.addAll(AgenciaMapper.INSTANCE.getEventosDto(eventos));
        for (EventoDto eventoDto : listaEventosDto) {
            System.out.println("EventoDto: " + eventoDto.nombreEvento());
        }
    }



    private void listenerSelection() {
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            eventoSeleccionado = newSelection;
        });
    }


    private void mostrarInformacionEvento(EventoDto eventoSeleccionado) {
        if (eventoSeleccionado != null) {
            txtNombreEvento.setText(eventoSeleccionado.nombreEvento());
            txtDescripcionEvento.setText(eventoSeleccionado.descripcionEvento());
            txtFechaEvento.setText(eventoSeleccionado.fechaEvento());
            txtHoraEvento.setText(eventoSeleccionado.horaEvento());
            txtUbicacionEvento.setText(eventoSeleccionado.ubicacionEvento());
            txtCapacidadMaximaEvento.setText(String.valueOf(eventoSeleccionado.capacidadMaximaEvento()));
        }
    }

    @FXML
    void nuevoEventoAction(ActionEvent event) {
        txtNombreEvento.setText("Ingrese el nombre");
        txtDescripcionEvento.setText("Ingrese la descripción");
        txtFechaEvento.setText("Ingrese la fecha");
        txtHoraEvento.setText("Ingrese la hora");
        txtUbicacionEvento.setText("Ingrese la ubicación");
        txtCapacidadMaximaEvento.setText("Ingrese la capacidad máxima");
    }

    @FXML
    void agregarEventoAction(ActionEvent event) throws EventoException {
        crearEvento();
    }

    @FXML
    void eliminarEventoAction(ActionEvent event) throws EventoException {
        eliminarEvento();
    }

    @FXML
    void actualizarEventoAction(ActionEvent event) throws EventoException {
        actualizarEvento();
    }

    @FXML
    private void crearEvento() throws EventoException {
        // 1. Capturar los datos
        EventoDto eventoDto = construirEventoDto();
        // 2. Validar la información
        if (datosValidos(eventoDto)) {
            if (modelFactoryController.crearEvento(eventoDto.nombreEvento(), eventoDto.descripcionEvento(), eventoDto.fechaEvento(), eventoDto.horaEvento(), eventoDto.ubicacionEvento(), eventoDto.capacidadMaximaEvento()) != null) {
                listaEventosDto.add(eventoDto);
                mostrarMensaje("Notificación evento", "Evento creado", "El evento se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposEvento();
            } else {
                mostrarMensaje("Notificación evento", "Evento no creado", "El evento no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Notificación evento", "Evento no creado", "Los datos ingresados son inválidos", Alert.AlertType.ERROR);
        }
    }

    private void eliminarEvento() throws EventoException {
        boolean eventoEliminado = false;
        if (eventoSeleccionado != null) {
            if (mostrarMensajeConfirmacion("¿Estás seguro de eliminar el evento?")) {
                eventoEliminado = modelFactoryController.eliminarEvento(eventoSeleccionado.nombreEvento());
                if (eventoEliminado) {
                    listaEventosDto.remove(eventoSeleccionado);
                    eventoSeleccionado = null;
                    tabla.getSelectionModel().clearSelection();
                    limpiarCamposEvento();
                    mostrarMensaje("Notificación evento", "Evento eliminado", "El evento se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje("Notificación evento", "Evento no eliminado", "El evento no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje("Notificación evento", "Evento no seleccionado", "Selecciona un evento de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarEvento() throws EventoException {
        boolean eventoActualizado = false;

        String nombreEventoActual = eventoSeleccionado.nombreEvento();
        EventoDto eventoDto = construirEventoDto();

        if (eventoSeleccionado != null) {

            if (datosValidos(eventoDto)) {
                eventoActualizado = modelFactoryController.actualizarEvento(nombreEventoActual, eventoDto.descripcionEvento(), eventoDto.fechaEvento(), eventoDto.horaEvento(), eventoDto.ubicacionEvento(), eventoDto.capacidadMaximaEvento());
                if (eventoActualizado) {
                    listaEventosDto.remove(eventoSeleccionado);
                    listaEventosDto.add(eventoDto);
                    tabla.refresh();
                    mostrarMensaje("Notificación evento", "Evento actualizado", "El evento se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposEvento();
                } else {
                    mostrarMensaje("Notificación evento", "Evento no actualizado", "El evento no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            } else {
                mostrarMensaje("Notificación evento", "Evento no creado", "Los datos ingresados son inválidos", Alert.AlertType.ERROR);
            }
        }
    }

    private EventoDto construirEventoDto() {


        return new EventoDto(
                txtNombreEvento.getText(),
                txtDescripcionEvento.getText(),
                txtFechaEvento.getText(),
                txtHoraEvento.getText(),
                Integer.parseInt(txtCapacidadMaximaEvento.getText()),
                txtUbicacionEvento.getText()
        );

    }


    private void limpiarCamposEvento() {
        txtNombreEvento.setText("");
        txtDescripcionEvento.setText("");
        txtFechaEvento.setText("");
        txtHoraEvento.setText("");
        txtUbicacionEvento.setText("");
        txtCapacidadMaximaEvento.setText("");
    }

    private boolean datosValidos(EventoDto eventoDto) {
        String mensaje = "";
        if (eventoDto.nombreEvento() == null || eventoDto.nombreEvento().equals(""))
            mensaje += "El nombre es inválido \n";
        if (eventoDto.descripcionEvento() == null || eventoDto.descripcionEvento().equals(""))
            mensaje += "La descripción es inválida \n";
        if (eventoDto.fechaEvento() == null || eventoDto.fechaEvento().equals(""))
            mensaje += "La fecha es inválida \n";
        if (eventoDto.horaEvento() == null || eventoDto.horaEvento().equals(""))
            mensaje += "La hora es inválida \n";
        if (eventoDto.ubicacionEvento() == null || eventoDto.ubicacionEvento().equals(""))
            mensaje += "La ubicación es inválida \n";
        if (eventoDto.capacidadMaximaEvento() <= 0)
            mensaje += "La capacidad máxima debe ser mayor a 0 \n";

        if (mensaje.equals("")) {
            return true;
        } else {
            mostrarMensaje("Notificación evento", "Datos inválidos", mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        return action.orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}
