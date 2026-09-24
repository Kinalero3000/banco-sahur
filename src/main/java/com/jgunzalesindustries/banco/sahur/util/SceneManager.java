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
import main.java.com.jgunzalesindustries.banco.sahur.controller.LoanController;
import main.java.com.jgunzalesindustries.banco.sahur.repository.AuthRepository;
import main.java.com.jgunzalesindustries.banco.sahur.repository.UserRepository;
import main.java.com.jgunzalesindustries.banco.sahur.service.AuthService;
import main.java.com.jgunzalesindustries.banco.sahur.service.RegisterService;


public class SceneManager {

    // Carpeta donde viven los .fxml: src/main/resources/view/
    private static final String VIEW_PATH = "/view/";

    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    // ================== VISTAS ==================

    public void showLoginView() throws IOException {
        AuthService authService = new AuthService(new AuthRepository());
        LoginController controller = new LoginController(authService, this);
        loadScene("login-view.fxml", controller, "Banco Sahur - Iniciar sesión");
    }

    public void showRegistroView() throws IOException {
        UserRepository userRepository = new UserRepository();
        RegisterService registerService = new RegisterService(userRepository);
        RegisterController controller = new RegisterController(registerService, this, userRepository);
        loadScene("register-view.fxml", controller, "Banco Sahur - Registro");
    }

    public void showDashBoardView() throws IOException {
        DashboardController controller = new DashboardController(this);
        loadScene("dashboard-view.fxml", controller, "Banco Sahur - Dashboard");
    }

    public void showClienteView() throws IOException {
        // cliente-view.fxml ya trae su propio fx:controller declarado,
        // así que se carga sin inyectar controller manualmente.
        loadSelfControlledScene("cliente-view.fxml", "Banco Sahur - Clientes");
    }

    public void showLoanView() throws IOException {
        // loan-view.fxml no trae fx:controller, así que se instancia
        // LoanController manualmente (tiene constructor sin argumentos).
        LoanController controller = new LoanController();
        loadScene("loan-view.fxml", controller, "Banco Sahur - Préstamos");
    }

    // ================== UTILIDADES ==================

    private void loadScene(String fxmlFile, Object controller, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_PATH + fxmlFile));
        loader.setController(controller);
        Parent root = loader.load();
        renderScene(root, title);
    }

    private void loadSelfControlledScene(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_PATH + fxmlFile));
        Parent root = loader.load();
        renderScene(root, title);
    }

    private void renderScene(Parent root, String title) {
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
