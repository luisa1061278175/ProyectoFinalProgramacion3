package co.edu.uniquindio.proyectofinalprogramacion3.viewController;

import co.edu.uniquindio.proyectofinalprogramacion3.controller.ModelFactoryController;
import co.edu.uniquindio.proyectofinalprogramacion3.exception.EmpleadoException;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.mappers.AgenciaMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class EmpleadoViewController {

    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    private ObservableList<EmpleadoDto> listaEmpleadosDto = FXCollections.observableArrayList();
    private EmpleadoDto empleadoSeleccionado;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableColumn<EmpleadoDto, String> colCorreoEmpleado;

    @FXML
    private TableColumn<EmpleadoDto, String> colIdentificacionEmpleado;

    @FXML
    private TableColumn<EmpleadoDto, String> colNombreEmpleado;

    @FXML
    private TableColumn<EmpleadoDto, String> colRolEmpleado;

    @FXML
    private TableColumn<EmpleadoDto, String> colContrasenia;

    @FXML
    private TableView<EmpleadoDto> tabla;

    @FXML
    private TextField txtCorreoEmpleados;

    @FXML
    private TextField txtIdentificacionEmpleados;

    @FXML
    private TextField txtNombreEmpleados;

    @FXML
    private TextField txtRolEmpleados;

    @FXML
    private TextField txtContraseniaEmpleados;



    @FXML
    void initialize() {
        initView();
    }

    @FXML
    private void initView() {
        initDataBindig();
        obtenerEmpleado();
        tabla.getItems().clear();
        tabla.setItems(listaEmpleadosDto);
        listenerSelection();
    }

    private void initDataBindig() {
        colCorreoEmpleado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().correoElectronico()));
        colIdentificacionEmpleado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().id()));
        colNombreEmpleado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().nombre()));
        colRolEmpleado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().rolEmpleado()));
        colContrasenia.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().contrasenia()));
    }

    private void obtenerEmpleado() {
        listaEmpleadosDto.addAll(AgenciaMapper.INSTANCE.getEmpleadosDto(modelFactoryController.obtenerEmpleados()));
    }

    private void listenerSelection() {
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            empleadoSeleccionado = newSelection;
            mostrarInformacionEmpleado(empleadoSeleccionado);
        });
    }

    private void mostrarInformacionEmpleado(EmpleadoDto empleadoSeleccionado) {
        if (empleadoSeleccionado != null) {
            txtNombreEmpleados.setText(empleadoSeleccionado.nombre());
            txtRolEmpleados.setText(empleadoSeleccionado.rolEmpleado());
            txtIdentificacionEmpleados.setText(empleadoSeleccionado.id());
            txtCorreoEmpleados.setText(empleadoSeleccionado.correoElectronico());
            txtContraseniaEmpleados.setText(empleadoSeleccionado.contrasenia());
        }
    }

    @FXML
    void nuevoEmpleadoAction(ActionEvent event) {
        txtNombreEmpleados.setText("Ingrese el nombre");
        txtIdentificacionEmpleados.setText("Ingrese la identificación");
        txtCorreoEmpleados.setText("Ingrese el correo");
        txtRolEmpleados.setText("Ingrese el rol");
        txtContraseniaEmpleados.setText("Ingrese la contraseña");
    }

    @FXML
    void agregarEmpleadoAction(ActionEvent event) throws EmpleadoException {
        crearEmpleado();
    }

    @FXML
    void eliminarEmpleadoAction(ActionEvent event) throws EmpleadoException {
        eliminarEmpleado();
    }

    @FXML
    void actualizarEmpleadoAction(ActionEvent event) throws EmpleadoException {
        actualizarEmpleado();
    }

    private void crearEmpleado() throws EmpleadoException {
        EmpleadoDto empleadoDto = construirEmpleadoDto();
        if (datosValidos(empleadoDto)) {
            if (modelFactoryController.crearEmpleado(empleadoDto.nombre(), empleadoDto.id(), empleadoDto.correoElectronico(), empleadoDto.rolEmpleado(), empleadoDto.contrasenia()) != null) {
                listaEmpleadosDto.add(empleadoDto);
                mostrarMensaje("Notificación empleado", "Empleado creado", "El empleado se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposEmpleado();
            } else {
                mostrarMensaje("Notificación empleado", "Empleado no creado", "El empleado no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son inválidos", Alert.AlertType.ERROR);
        }
    }

    private void eliminarEmpleado() throws EmpleadoException {
        boolean empleadoEliminado = false;
        if (empleadoSeleccionado != null) {
            if (mostrarMensajeConfirmacion("¿Estás seguro de eliminar al empleado?")) {
                empleadoEliminado = modelFactoryController.eliminarEmpleado(empleadoSeleccionado.id());
                if (empleadoEliminado) {
                    listaEmpleadosDto.remove(empleadoSeleccionado);
                    empleadoSeleccionado = null;
                    tabla.getSelectionModel().clearSelection();
                    limpiarCamposEmpleado();
                    mostrarMensaje("Notificación empleado", "Empleado eliminado", "El empleado se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje("Notificación empleado", "Empleado no eliminado", "El empleado no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje("Notificación empleado", "Empleado no seleccionado", "Selecciona un empleado de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarEmpleado() throws EmpleadoException {
        boolean clienteActualizado = false;
        String cedulaActual = empleadoSeleccionado.id();
        EmpleadoDto empleadoDto = construirEmpleadoDto();
        if (empleadoSeleccionado != null) {
            if (datosValidos(empleadoSeleccionado)) {
                clienteActualizado = modelFactoryController.actualizarEmpleado(cedulaActual, empleadoDto.nombre(), empleadoDto.correoElectronico(), empleadoDto.rolEmpleado(), empleadoDto.contrasenia());
                if (clienteActualizado) {
                    listaEmpleadosDto.remove(empleadoSeleccionado);
                    listaEmpleadosDto.add(empleadoDto);
                    tabla.refresh();
                    mostrarMensaje("Notificación empleado", "Empleado actualizado", "El empleado se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposEmpleado();
                } else {
                    mostrarMensaje("Notificación empleado", "Empleado no actualizado", "El empleado no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            } else {
                mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son inválidos", Alert.AlertType.ERROR);
            }
        }
    }

    private EmpleadoDto construirEmpleadoDto() {
        return new EmpleadoDto(
                txtNombreEmpleados.getText(),
                txtIdentificacionEmpleados.getText(),
                txtCorreoEmpleados.getText(),
                txtRolEmpleados.getText(),
                txtContraseniaEmpleados.getText()
        );
    }

    private void limpiarCamposEmpleado() {
        txtNombreEmpleados.setText("");
        txtCorreoEmpleados.setText("");
        txtIdentificacionEmpleados.setText("");
        txtRolEmpleados.setText("");
        txtContraseniaEmpleados.setText("");
    }

    private boolean datosValidos(EmpleadoDto empleadoDto) {
        String mensaje = "";
        if (empleadoDto.nombre() == null || empleadoDto.nombre().equals(""))
            mensaje += "El nombre es inválido \n";
        if (empleadoDto.id() == null || empleadoDto.id().equals(""))
            mensaje += "El documento es inválido \n";
        if (empleadoDto.correoElectronico() == null || empleadoDto.correoElectronico().equals(""))
            mensaje += "El correo es inválido \n";
        if (empleadoDto.contrasenia() == null || empleadoDto.contrasenia().equals(""))
            mensaje += "La contraseña es inválida \n";

        if (mensaje.equals("")) {
            return true;
        } else {
            mostrarMensaje("Notificación cliente", "Datos inválidos", mensaje, Alert.AlertType.WARNING);
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
        return action.isPresent() && action.get() == ButtonType.OK;
    }
}
