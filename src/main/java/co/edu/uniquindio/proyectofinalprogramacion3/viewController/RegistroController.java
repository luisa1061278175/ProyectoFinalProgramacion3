package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.HelloApplication;
import co.edu.uniquindio.agencia20241.controller.ModelFactoryController;
import co.edu.uniquindio.agencia20241.exception.UsuarioException;
import co.edu.uniquindio.agencia20241.mapping.dto.EventoDto;
import co.edu.uniquindio.agencia20241.model.Empleado;
import co.edu.uniquindio.agencia20241.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistroController {
    private final ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    private final ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();

    private final Usuario usuario = new Usuario();
    private final Empleado empleado = new Empleado();

    // para saber que usuario inicio sesion
    public static String idUsuarioAutenticado;
    private final UsuarioViewController usuarioViewController = new UsuarioViewController();

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnRegresar;

    @FXML
    private TextField txtContraseña;

    @FXML
    private TextField txtContraseñaVerificacion;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    void initialize() {
        // Aquí puedes inicializar cualquier cosa que necesites al cargar la vista
    }

    @FXML
    void guardar(ActionEvent event) throws IOException, UsuarioException {
        Stage stage = new Stage();
        String nombre = txtNombre.getText();
        String contrasenia = txtContraseña.getText();
        String correo = txtCorreo.getText();
        String id = txtId.getText();

        // Suponiendo que aquí se debe crear un usuario, llama al método correspondiente
        modelFactoryController.crearUsuario(id, nombre, correo, null, contrasenia);

        // Generar un código de verificación
        String codigo = generarCodigo();

        // Enviar el correo con el código
        EnvioCorreos envioCorreos = new EnvioCorreos();
        envioCorreos.setEmailTo(correo);
        envioCorreos.setSubject("Código de Verificación");
        envioCorreos.setContent("Su código de verificación es: " + codigo);
        envioCorreos.createEmail();
        envioCorreos.sendEmail();

        cargarInicioSesion(stage);
    }

    @FXML
    void regresar(ActionEvent event) {
        // Implementar la lógica para regresar, por ejemplo, cerrar la ventana actual
        Stage stage = (Stage) btnRegresar.getScene().getWindow();
        stage.close();
    }

    public void crearUsuario(String id, String contrasenia, String correo, String nombre) throws IOException, UsuarioException {
        modelFactoryController.crearUsuario(nombre, id, correo, null, contrasenia);
    }

    public void cargarInicioSesion(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("InicioSesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("INICIAR SESIÓN");
        stage.setScene(scene);
        stage.show();
    }

    private String generarCodigo() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000); // Código de 6 dígitos
        return String.valueOf(codigo);
    }
}

// Clase EnvioCorreos para enviar correos electrónicos
