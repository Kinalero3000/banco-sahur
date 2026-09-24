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

    /**
     * Muestra el dashboard principal, ya con el rol y el correo del usuario
     * que inició sesión, para que el DashboardController pueda decidir
     * qué botones/secciones mostrar.
     *
     * @param rolName   nombre del rol devuelto por el login (ej. "Cliente", "Trabajador", "Admin").
     * @param userEmail correo con el que el usuario inició sesión.
     */
    public void showDashBoardView(String rolName, String userEmail) throws IOException {
        DashboardController controller = new DashboardController(this, rolName, userEmail);
        loadScene("dashboard-view.fxml", controller, "Banco Sahur - Dashboard");
    }

    public void showClienteView() throws IOException {
        // cliente-view.fxml ya trae su propio fx:controller declarado,
        // así que se carga sin inyectar controller manualmente.
        loadSelfControlledScene("cliente-view.fxml", "Banco Sahur - Clientes");
    }

    /**
     * Muestra la vista de préstamos.
     *
     * @param restrictToClientEmail si es null, se muestra la vista completa (alta/edición/
     *                               aprobación) para trabajadores/admin. Si trae un correo,
     *                               la vista se abre en modo solo-lectura filtrada a los
     *                               préstamos del cliente asociado a ese correo.
     */
    public void showLoanView(String restrictToClientEmail) throws IOException {
        // loan-view.fxml no trae fx:controller, así que se instancia
        // LoanController manualmente.
        LoanController controller = new LoanController(restrictToClientEmail);
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
