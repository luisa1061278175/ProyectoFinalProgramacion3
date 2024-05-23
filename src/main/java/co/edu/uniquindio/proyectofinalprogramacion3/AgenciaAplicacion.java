package co.edu.uniquindio.proyectofinalprogramacion3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AgenciaAplicacion extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Verifica que la ruta sea correcta
        FXMLLoader fxmlLoader = new FXMLLoader(AgenciaAplicacion.class.getResource("/co/edu/uniquindio/proyectofinalprogramacion3/PantallaPrincipal.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Sección usuario");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
