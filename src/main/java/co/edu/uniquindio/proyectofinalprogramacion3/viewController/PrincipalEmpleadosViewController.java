package co.edu.uniquindio.agencia20241.viewController;

import co.edu.uniquindio.agencia20241.AgenciaAplicacion;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class PrincipalEmpleadosViewController implements Initializable  {



    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(AgenciaAplicacion.class.getResource("PrincipalEmpleados.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 700, 550);
        stage = new Stage();
        URL url = getClass().getResource("\\src\\main\\resources\\co\\edu\\uniquindio\\reservasevento");


        stage.setScene(scene);
        stage.setTitle("Empleados");
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

