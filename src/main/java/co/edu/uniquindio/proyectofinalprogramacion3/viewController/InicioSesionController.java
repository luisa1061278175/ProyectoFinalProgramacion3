package co.edu.uniquindio.proyectofinalprogramacion3.viewController;

import co.edu.uniquindio.proyectofinalprogramacion3.HelloApplication;
import co.edu.uniquindio.proyectofinalprogramacion3.controller.ModelFactoryController;
import co.edu.uniquindio.proyectofinalprogramacion3.mapping.dto.EventoDto;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Empleado;
import co.edu.uniquindio.proyectofinalprogramacion3.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InicioSesionController {
    private ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    private ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();

    private Usuario usuario = new Usuario();
    private Empleado empleado = new Empleado();

    //para saber que usuario inicio sesion
   public static String idUsuarioAutenticado;
    private UsuariousuarioViewController usuariousuarioViewController= new UsuariousuarioViewController();

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtContraseña;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnRegresar;


    @FXML
    void guardar(ActionEvent event) throws IOException {

        String nombre = txtNombre.getText();
        String contrasenia = txtContraseña.getText();

        iniciarSesion(nombre, contrasenia);

    }

    @FXML
    void regresar(ActionEvent event) {

    }

    public void iniciarSesion(String id, String contrasenia) throws IOException {

        Stage stage = new Stage();

        // Cargar los usuarios y empleados desde ModelFactoryController
        modelFactoryController.obtenerUsuarios();
        modelFactoryController.obtenerEmpleados();

        // Verificar si es administrador
        boolean respuesta = modelFactoryController.validarUsuarioProperties(id,contrasenia);

        if (respuesta) {
            cargarAdmin(stage);
        } else {
            // Buscar usuario
            Usuario usuario = modelFactoryController.buscarUsuario(id);
            // Buscar empleado
            Empleado empleado = modelFactoryController.buscarEmpleado(id);

            // Verificar usuario
            if (usuario != null && contrasenia.equals(usuario.getContrasenia())&& usuario.getId().equals(id)) {
                cargarUsuario(stage);
                idUsuarioAutenticado=usuario.getId();
                System.out.println(idUsuarioAutenticado);

                modelFactoryController.buscarUsuario(idUsuarioAutenticado);
            }
            // Verificar  empleado
            else if (empleado != null && contrasenia.equals(empleado.getContrasenia())) {
                cargarEmpleado(stage);
            }

            else {
                System.out.println("Datos inválidos");
            }
        }
    }




    // Método para buscar un empleado por su ID


    public void cargarAdmin(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("PrincipalAdministrativos.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BienvenidoAdministrador");
        stage.setScene(scene);
        stage.show();
    }

    public void cargarUsuario(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("PrincipalUsuarios.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BienvenidoUsuario");
        stage.setScene(scene);
        stage.show();
    }

    public void cargarEmpleado(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("PrincipalEmpleados.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BienvenidoEmpleado");
        stage.setScene(scene);
        stage.show();
    }

}
