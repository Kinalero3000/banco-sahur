package main.java.com.jgunzalesindustries.banco.sahur.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.java.com.jgunzalesindustries.banco.sahur.util.SceneManager;

/**
 * Dashboard principal.
 *
 * Al iniciar sesión, el usuario llega aquí y ve solo los botones que le
 * corresponden según su rol (columna "rol" de la tabla roles en la BD):
 *
 *  - Cliente: solo puede ver SUS préstamos (vista de solo lectura, sin
 *    poder ir a Clientes ni a Usuarios).
 *  - Trabajador: puede ver Clientes y Préstamos (todos), pero NO Usuarios.
 *  - Admin: puede ver todo (Clientes, Préstamos y Usuarios).
 *
 * NOTA: el nombre del rol viene tal cual lo guarda la BD (LoginDTOResponse
 * -> getNombreRol()). Como este proyecto no trae el script SQL de la tabla
 * "roles", la comparación se hace de forma flexible (sin distinguir
 * mayúsculas/minúsculas y buscando coincidencias parciales como "admin",
 * "trabaj"/"emplead" o "client"). Si tus roles en la BD usan otras
 * palabras, ajusta el método resolveRole(...).
 */
public class DashboardController implements Initializable {

    private enum Role {
        CLIENTE, TRABAJADOR, ADMIN, DESCONOCIDO
    }

    private final SceneManager sceneManager;
    private final String rolName;
    private final String userEmail;
    private final Role role;

    @FXML
    private Label lblRolActual;
    @FXML
    private Button btnVerClientes;
    @FXML
    private Button btnVerPrestamos;
    @FXML
    private Button btnVerUsuarios;

    public DashboardController(SceneManager sceneManager, String rolName, String userEmail) {
        this.sceneManager = sceneManager;
        this.rolName = rolName;
        this.userEmail = userEmail;
        this.role = resolveRole(rolName);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (lblRolActual != null) {
            lblRolActual.setText(rolName == null || rolName.isBlank() ? "Sin rol" : rolName);
        }
        aplicarPermisosPorRol();
    }

    private void aplicarPermisosPorRol() {
        boolean puedeVerClientes = role == Role.TRABAJADOR || role == Role.ADMIN;
        boolean puedeVerUsuarios = role == Role.ADMIN;
        boolean puedeVerPrestamos = role != Role.DESCONOCIDO;

        mostrar(btnVerClientes, puedeVerClientes);
        mostrar(btnVerUsuarios, puedeVerUsuarios);
        mostrar(btnVerPrestamos, puedeVerPrestamos);

        if (btnVerPrestamos != null) {
            btnVerPrestamos.setText(role == Role.CLIENTE ? "Mis préstamos" : "Préstamos");
        }
    }

    private void mostrar(Button boton, boolean visible) {
        if (boton == null) {
            return;
        }
        boton.setVisible(visible);
        boton.setManaged(visible);
    }

    // ================== NAVEGACIÓN ==================

    @FXML
    private void handleVerClientes() throws IOException {
        if (role != Role.TRABAJADOR && role != Role.ADMIN) {
            denegarAcceso("Tu rol no tiene acceso a la gestión de clientes.");
            return;
        }
        sceneManager.showClienteView();
    }

    @FXML
    private void handleVerPrestamos() throws IOException {
        if (role == Role.DESCONOCIDO) {
            denegarAcceso("No se pudo determinar tu rol; no es posible mostrar los préstamos.");
            return;
        }
        // El cliente solo ve sus propios préstamos (modo solo-lectura);
        // trabajador y admin ven y gestionan todos los préstamos.
        sceneManager.showLoanView(role == Role.CLIENTE ? userEmail : null);
    }

    @FXML
    private void handleVerUsuarios() throws IOException {
        if (role != Role.ADMIN) {
            denegarAcceso("Solo un administrador puede gestionar usuarios.");
            return;
        }
        sceneManager.showUserView();
    }

    @FXML
    private void handleCerrarSesion() throws IOException {
        sceneManager.showLoginView();
    }

    private void denegarAcceso(String mensaje) {
        sceneManager.showAlertInfo("Acceso denegado", "Sin permisos", mensaje, Alert.AlertType.WARNING);
    }

    // ================== RESOLUCIÓN DE ROL ==================

    private Role resolveRole(String rolName) {
        if (rolName == null || rolName.isBlank()) {
            return Role.DESCONOCIDO;
        }
        String normalizado = rolName.trim().toLowerCase(Locale.ROOT);
        if (normalizado.contains("admin")) {
            return Role.ADMIN;
        }
        if (normalizado.contains("trabaj") || normalizado.contains("emplead")) {
            return Role.TRABAJADOR;
        }
        if (normalizado.contains("client")) {
            return Role.CLIENTE;
        }
        return Role.DESCONOCIDO;
    }
}
