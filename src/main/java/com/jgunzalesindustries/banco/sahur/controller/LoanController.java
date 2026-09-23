/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.jgunzalesindustries.banco.sahur.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.jgunzalesindustries.banco.sahur.model.Loan;
import main.java.com.jgunzalesindustries.banco.sahur.service.LoanService;

/**
 *
 * @author angel
 */


public class LoanController implements Initializable {

    // Componentes del FXML (deben coincidir con el fx:id del archivo FXML)
    @FXML private TableView<Loan> tblLoans;
    @FXML private TableColumn<Loan, String> colIdLoan;
    @FXML private TableColumn<Loan, String> colIdClient;
    @FXML private TableColumn<Loan, Double> colAmount;
    @FXML private TableColumn<Loan, Integer> colInterestRate;
    @FXML private TableColumn<Loan, Integer> colTermMonths;
    @FXML private TableColumn<Loan, LocalDate> colRequestDate;
    @FXML private TableColumn<Loan, LocalDate> colApprovalDate;
    @FXML private TableColumn<Loan, String> colStatus;

    @FXML private Button btnApprove;
    @FXML private Button btnReject;

    private LoanService loanService = new LoanService ();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Instanciar el servicio
        this.loanService = new LoanService();

        // 2. Mapear las columnas con las propiedades de la clase Loan (coincidiendo con getters)
        colIdLoan.setCellValueFactory(new PropertyValueFactory<>("idLoan"));
        colIdClient.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colInterestRate.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
        colTermMonths.setCellValueFactory(new PropertyValueFactory<>("termMonths"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        colApprovalDate.setCellValueFactory(new PropertyValueFactory<>("approvalDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // 3. Cargar la lista de préstamos en la tabla
        loadLoanData();
    }

    private void loadLoanData() {
        try {
            tblLoans.setItems(loanService.getA);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error de Carga", "No se pudieron obtener los registros: " + e.getMessage());
        }
    }

    @FXML
    private void handleApprove() {
        Loan selectedLoan = tblLoans.getSelectionModel().getSelectedItem();
        
        if (selectedLoan == null) {
            showAlert(Alert.AlertType.WARNING, "Atención", "Selecciona un préstamo de la tabla para aprobar.");
            return;
        }

        try {
            boolean success = loanService.approveLoan(selectedLoan.getIdLoan());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "El préstamo ha sido APROBADO.");
                tblLoans.refresh(); // Refresca la tabla para reflejar los cambios de estado y fecha
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            showAlert(Alert.AlertType.ERROR, "Error de Negocio", e.getMessage());
        }
    }

    @FXML
    private void handleReject() {
        Loan selectedLoan = tblLoans.getSelectionModel().getSelectedItem();

        if (selectedLoan == null) {
            showAlert(Alert.AlertType.WARNING, "Atención", "Selecciona un préstamo de la tabla para rechazar.");
            return;
        }

        try {
            boolean success = loanService.rejectLoan(selectedLoan.getIdLoan());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "El préstamo ha sido RECHAZADO.");
                tblLoans.refresh();
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            showAlert(Alert.AlertType.ERROR, "Error de Negocio", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

