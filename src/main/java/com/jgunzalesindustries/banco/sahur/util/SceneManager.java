package main.java.com.jgunzalesindustries.banco.sahur.util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import main.java.com.jgunzalesindustries.banco.sahur.controller.LoginController;
import main.java.com.jgunzalesindustries.banco.sahur.controller.RegisterController;
import main.java.com.jgunzalesindustries.banco.sahur.controller.DashboardController;
import main.java.com.jgunzalesindustries.banco.sahur.repository.AuthRepository;
import main.java.com.jgunzalesindustries.banco.sahur.repository.UserRepository;
import main.java.com.jgunzalesindustries.banco.sahur.service.AuthService;
import main.java.com.jgunzalesindustries.banco.sahur.service.RegisterService;

/**
 * Encargado de cargar los FXML, inyectar sus controllers y controlar
 * la navegación entre vistas dentro del Stage principal de la aplicación.
 *
 * por ahora solo resuelve login / registro / dashboard (placeholder),
 * que es lo necesario para poder trabajar el diseño de las vistas de
 * login y registro sin errores de compilación. Se puede ir ampliando
 * a futuro con las demás vistas del proyecto (préstamos, clientes, etc).
 */
public class SceneManager {

    // Carpeta donde viven los .fxml, dentro del mismo paquete "view"
    private static final String VIEW_PATH = "/main/java/com/jgunzalesindustries/banco/sahur/view/";

    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    //vistas 
    public void showLoginView() throws IOException {
        AuthService authService = new AuthService(new AuthRepository());
        LoginController controller = new LoginController(authService, this);
        loadScene("login.fxml", controller, "Banco Sahur - Iniciar sesión");
    }

    public void showRegistroView() throws IOException {
    UserRepository userRepository = new UserRepository();
    RegisterService registerService = new RegisterService(userRepository);
    RegisterController controller = new RegisterController(registerService, this, userRepository);
    loadScene("registro.fxml", controller, "Banco Sahur - Registro");
}

    public void showDashBoardView() throws IOException {
        DashboardController controller = new DashboardController();
        loadScene("dashboard.fxml", controller, "Banco Sahur - Dashboard");
    }

    // utilidades y metodos reutilizables 

    private void loadScene(String fxmlFile, Object controller, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_PATH + fxmlFile));
        loader.setController(controller);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public void showAlertInfo(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
