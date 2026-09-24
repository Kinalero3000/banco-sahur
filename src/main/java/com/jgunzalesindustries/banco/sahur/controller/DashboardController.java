package main.java.com.jgunzalesindustries.banco.sahur.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.java.com.jgunzalesindustries.banco.sahur.util.SceneManager;

/**
 * Dashboard principal. Por ahora solo enruta a Clientes.
 * El botón/enlace a Préstamos se agrega cuando se resuelva loan-view.fxml
 * junto con LoanController.
 */
public class DashboardController implements Initializable {

    private final SceneManager sceneManager;

    public DashboardController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: cargar info general del dashboard
    }

    @FXML
    private void handleVerClientes() throws IOException {
        sceneManager.showClienteView();
    }

    @FXML
    private void handleVerPrestamos() throws IOException {
        sceneManager.showLoanView();
    }
}
