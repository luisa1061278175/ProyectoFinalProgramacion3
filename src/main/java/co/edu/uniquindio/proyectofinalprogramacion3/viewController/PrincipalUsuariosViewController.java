package co.edu.uniquindio.agencia20241.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PrincipalUsuariosViewController {
    // Aquí debes definir los elementos de la interfaz gráfica a los que deseas acceder desde el controlador
    @FXML
    private TextField textFieldUsuario;

    @FXML
    private Button buttonGuardar;

    // Método que se ejecutará al inicializar el controlador
    @FXML
    private void initialize() {
        // Puedes realizar acciones de inicialización aquí
    }

    // Aquí puedes definir métodos que se ejecutarán al interactuar con la interfaz gráfica
    @FXML
    private void handleGuardar() {
        // Por ejemplo, aquí podrías manejar el evento de clic en el botón "Guardar"
        String usuario = textFieldUsuario.getText();
        // Lógica para guardar el usuario...
    }
}
