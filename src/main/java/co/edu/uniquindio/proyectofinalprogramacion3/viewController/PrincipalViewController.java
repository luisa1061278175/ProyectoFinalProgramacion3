package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PrincipalViewController {

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrar;

    @FXML
    void iniciarSesion(ActionEvent event) throws IOException {
        Stage stage= new Stage();
        cargarInicioSesion(stage);
    }

    @FXML
    void registrar(ActionEvent event) throws IOException {
        Stage stage= new Stage();
        cargarRegistro(stage);
    }

    public void cargarInicioSesion(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("InicioSesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("INICIAR SESIÓN");
        stage.setScene(scene);
        stage.show();
    }
    public void cargarRegistro(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RegistrarUsuario.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("INICIAR SESIÓN");
        stage.setScene(scene);
        stage.show();
    }

}
