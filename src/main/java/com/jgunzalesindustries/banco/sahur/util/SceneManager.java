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
import main.java.com.jgunzalesindustries.banco.sahur.controller.ClienteController;
import main.java.com.jgunzalesindustries.banco.sahur.controller.LoanController;
import main.java.com.jgunzalesindustries.banco.sahur.controller.UserController;
import main.java.com.jgunzalesindustries.banco.sahur.repository.AuthRepository;
import main.java.com.jgunzalesindustries.banco.sahur.repository.UserRepository;
import main.java.com.jgunzalesindustries.banco.sahur.service.AuthService;
import main.java.com.jgunzalesindustries.banco.sahur.service.RegisterService;
import main.java.com.jgunzalesindustries.banco.sahur.service.UserService;


public class SceneManager {

    // Carpeta donde viven los .fxml: src/main/resources/view/
    private static final String VIEW_PATH = "/main/resources/view/";

    private final Stage stage;

    // Rol/correo del usuario logeado en la sesión actual, para poder
    // volver al dashboard desde cualquier vista sin tener que repasarlos.
    private String currentRolName;
    private String currentUserEmail;

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

    public void showDashBoardView(String rolName, String userEmail) throws IOException {
        this.currentRolName = rolName;
        this.currentUserEmail = userEmail;
        DashboardController controller = new DashboardController(this, rolName, userEmail);
        loadScene("dashboard-view.fxml", controller, "Banco Sahur - Dashboard");
    }

    /** Vuelve al dashboard reusando el rol/correo de la sesión actual. */
    public void showDashBoardView() throws IOException {
        showDashBoardView(currentRolName, currentUserEmail);
    }

    public void showClienteView() throws IOException {
        // cliente-view.fxml ya trae su propio fx:controller declarado,
        // así que se carga sin inyectar controller manualmente. Después
        // de cargarlo, le inyectamos el SceneManager por setter para que
        // pueda volver al dashboard.
        loadSelfControlledScene("cliente-view.fxml", "Banco Sahur - Clientes", controller -> {
            if (controller instanceof ClienteController clienteController) {
                clienteController.setSceneManager(this);
            }
        });
    }

    public void showLoanView(String restrictToClientEmail) throws IOException {

        LoanController controller = new LoanController(this, restrictToClientEmail);
        String titulo = restrictToClientEmail != null
                ? "Banco Sahur - Mis Préstamos"
                : "Banco Sahur - Préstamos";
        loadScene("loan-view.fxml", controller, titulo);
    }

    public void showUserView() throws IOException {
        // user-view.fxml no trae fx:controller, así que se instancia
        // UserController manualmente. Solo debería invocarse para el rol Admin.
        UserController controller = new UserController(new UserService(), this);
        loadScene("user-view.fxml", controller, "Banco Sahur - Usuarios");
    }

    // ================== UTILIDADES ==================

    private void loadScene(String fxmlFile, Object controller, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_PATH + fxmlFile));
        loader.setController(controller);
        Parent root = loader.load();
        renderScene(root, title);
    }

    private void loadSelfControlledScene(String fxmlFile, String title) throws IOException {
        loadSelfControlledScene(fxmlFile, title, controller -> { });
    }

    private void loadSelfControlledScene(String fxmlFile, String title, java.util.function.Consumer<Object> afterLoad) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_PATH + fxmlFile));
        Parent root = loader.load();
        afterLoad.accept(loader.getController());
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