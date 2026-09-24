package main.java.com.jgunzalesindustries.banco.sahur.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.ClienteDTOResponse;
import main.java.com.jgunzalesindustries.banco.sahur.model.Loan;
import main.java.com.jgunzalesindustries.banco.sahur.service.ClienteService;
import main.java.com.jgunzalesindustries.banco.sahur.service.LoanService;


public class LoanController implements Initializable {

    // ---- Título / contenedor del formulario (se oculta en modo solo-lectura) ----
    @FXML private Label lblTitulo;
    @FXML private VBox formBox;

    // ---- Formulario ----
    @FXML private TextField txtIdLoan;
    @FXML private TextField txtIdClient;
    @FXML private TextField txtIdTypeCredit;
    @FXML private TextField txtAmount;
    @FXML private TextField txtInterestRate;
    @FXML private TextField txtThermMonths;
    @FXML private Label lblStatus;

    // ---- Botones ----
    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnAprobar;
    @FXML private Button btnRechazar;
    @FXML private Button btnMostrarTodos;

    // ---- Tabla ----
    @FXML private TableView<Loan> tableLoans;
    @FXML private TableColumn<Loan, String> colIdLoan;
    @FXML private TableColumn<Loan, String> colIdClient;
    @FXML private TableColumn<Loan, Integer> colIdTypeCredit;
    @FXML private TableColumn<Loan, Double> colAmount;
    @FXML private TableColumn<Loan, Integer> colInterestRate;
    @FXML private TableColumn<Loan, Integer> colThermMonths;
    @FXML private TableColumn<Loan, LocalDate> colRequestDate;
    @FXML private TableColumn<Loan, LocalDate> colApprovalDate;
    @FXML private TableColumn<Loan, String> colStatus;

    private final LoanService loanService = new LoanService();
    private final ClienteService clienteService = new ClienteService();

    /**
     * Si no es null, la vista se abre en modo solo-lectura, mostrando
     * únicamente los préstamos del cliente asociado a este correo
     * (usado cuando un usuario con rol Cliente entra desde el dashboard).
     */
    private final String restrictedClientEmail;

    private Loan selectedLoan;

    /** Constructor por defecto: vista completa (trabajador/admin), sin restricciones. */
    public LoanController() {
        this(null);
    }

    /**
     * @param restrictedClientEmail correo del usuario logeado cuando debe verse
     *                               solo-lectura y filtrado a sus propios préstamos;
     *                               null para la vista completa de trabajador/admin.
     */
    public LoanController(String restrictedClientEmail) {
        this.restrictedClientEmail = restrictedClientEmail;
    }

    private boolean isReadOnly() {
        return restrictedClientEmail != null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colIdLoan.setCellValueFactory(new PropertyValueFactory<>("idLoan"));
        colIdClient.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        // Loan.getidTypeCredit() no sigue la convención de PropertyValueFactory
        // (debería llamarse getIdTypeCredit), por eso se resuelve a mano.
        colIdTypeCredit.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getidTypeCredit()).asObject());
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colInterestRate.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
        colThermMonths.setCellValueFactory(new PropertyValueFactory<>("thermMonths"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        colApprovalDate.setCellValueFactory(new PropertyValueFactory<>("approvalDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableLoans.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            selectedLoan = newValue;
            if (newValue != null) {
                fillForm(newValue);
            }
        });

        if (isReadOnly()) {
            aplicarModoSoloLectura();
        }

        loadLoanData();
    }

    /**
     * Oculta el formulario y todos los botones de alta/edición/aprobación
     * (Agregar, Actualizar, Eliminar, Aprobar, Rechazar, Mostrar Todos):
     * un cliente solo puede consultar sus préstamos, no modificarlos.
     */
    private void aplicarModoSoloLectura() {
        if (lblTitulo != null) {
            lblTitulo.setText("Mis Préstamos");
        }
        if (formBox != null) {
            formBox.setVisible(false);
            formBox.setManaged(false);
        }
    }

    private void loadLoanData() {
        try {
            if (isReadOnly()) {
                tableLoans.setItems(loadPropiosPrestamos());
            } else {
                tableLoans.setItems(loanService.getAllLoans());
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error de Carga", "No se pudieron obtener los registros: " + e.getMessage());
        }
    }

    /**
     * Resuelve el cliente asociado al correo del usuario logeado y devuelve
     * únicamente sus préstamos. Si no existe ningún cliente registrado con
     * ese correo, se muestra una tabla vacía y se avisa al usuario.
     */
    private ObservableList<Loan> loadPropiosPrestamos() {
        Optional<ClienteDTOResponse> cliente = clienteService.findByEmail(restrictedClientEmail);
        if (cliente.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Sin cliente asociado",
                    "No se encontró un cliente registrado con el correo " + restrictedClientEmail
                    + ". Pide a un trabajador que vincule tu cuenta a tu registro de cliente.");
            return FXCollections.observableArrayList();
        }
        return loanService.getLoansByClientId(cliente.get().getId());
    }

    // ---------- CRUD ----------

    @FXML
    private void handleAgregar() {
        if (isReadOnly()) {
            return;
        }
        try {
            Loan loan = buildLoanFromForm();
            loanService.applyForLoan(loan);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Préstamo agregado correctamente.");
            handleLimpiar();
            loadLoanData();
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, "Datos inválidos", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo agregar el préstamo: " + e.getMessage());
        }
    }

    @FXML
    private void handleActualizar() {
        if (isReadOnly()) {
            return;
        }
        if (selectedLoan == null) {
            showAlert(Alert.AlertType.WARNING, "Atención", "Selecciona un préstamo de la tabla para actualizar.");
            return;
        }
        try {
            Loan loan = buildLoanFromForm();
            // Se preservan fecha de solicitud, fecha de aprobación y estado actuales;
            // el formulario no controla esos valores directamente.
            loan.setRequestDate(selectedLoan.getRequestDate());
            loan.setApprovalDate(selectedLoan.getApprovalDate());
            loan.setStatus(selectedLoan.getStatus());

            loanService.updateLoan(loan);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Préstamo actualizado correctamente.");
            handleLimpiar();
            loadLoanData();
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, "Datos inválidos", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el préstamo: " + e.getMessage());
        }
    }

    @FXML
    private void handleEliminar() {
        if (isReadOnly()) {
            return;
        }
        String idLoan = selectedLoan != null ? selectedLoan.getIdLoan() : txtIdLoan.getText();
        if (idLoan == null || idLoan.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Atención", "Selecciona un préstamo o ingresa su ID para eliminar.");
            return;
        }
        try {
            loanService.deleteLoan(idLoan);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Préstamo eliminado.");
            handleLimpiar();
            loadLoanData();
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, "Atención", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el préstamo: " + e.getMessage());
        }
    }

    @FXML
    private void handleLimpiar() {
        selectedLoan = null;
        tableLoans.getSelectionModel().clearSelection();
        txtIdLoan.clear();
        txtIdClient.clear();
        txtIdTypeCredit.clear();
        txtAmount.clear();
        txtInterestRate.clear();
        txtThermMonths.clear();
        lblStatus.setText("—");
    }

    @FXML
    private void handleMostrarTodos() {
        loadLoanData();
    }

    // ---------- Aprobar / Rechazar ----------

    @FXML
    private void handleAprobar() {
        if (isReadOnly()) {
            return;
        }
        String idLoan = selectedLoan != null ? selectedLoan.getIdLoan() : txtIdLoan.getText();
        if (idLoan == null || idLoan.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Atención", "Selecciona un préstamo de la tabla para aprobar.");
            return;
        }
        try {
            loanService.approveLoan(idLoan);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "El préstamo ha sido APROBADO.");
            loadLoanData();
        } catch (IllegalArgumentException | IllegalStateException e) {
            showAlert(Alert.AlertType.ERROR, "Error de Negocio", e.getMessage());
        }
    }

    @FXML
    private void handleRechazar() {
        if (isReadOnly()) {
            return;
        }
        String idLoan = selectedLoan != null ? selectedLoan.getIdLoan() : txtIdLoan.getText();
        if (idLoan == null || idLoan.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Atención", "Selecciona un préstamo de la tabla para rechazar.");
            return;
        }
        try {
            loanService.rejectLoan(idLoan);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "El préstamo ha sido RECHAZADO.");
            loadLoanData();
        } catch (IllegalArgumentException | IllegalStateException e) {
            showAlert(Alert.AlertType.ERROR, "Error de Negocio", e.getMessage());
        }
    }

    // ---------- Helpers ----------

    private Loan buildLoanFromForm() {
        String idLoan = txtIdLoan.getText() == null ? "" : txtIdLoan.getText().trim();
        String idClient = txtIdClient.getText() == null ? "" : txtIdClient.getText().trim();

        int idTypeCredit = parseIntOrThrow(txtIdTypeCredit.getText(), "Tipo de crédito");
        double amount = parseDoubleOrThrow(txtAmount.getText(), "Monto");
        int interestRate = parseIntOrThrow(txtInterestRate.getText(), "Tasa de interés");
        int thermMonths = parseIntOrThrow(txtThermMonths.getText(), "Plazo en meses");

        return new Loan(idLoan, idClient, idTypeCredit, amount, interestRate, thermMonths, null, null, null);
    }

    private void fillForm(Loan loan) {
        txtIdLoan.setText(loan.getIdLoan());
        txtIdClient.setText(loan.getIdClient());
        txtIdTypeCredit.setText(String.valueOf(loan.getidTypeCredit()));
        txtAmount.setText(String.valueOf(loan.getAmount()));
        txtInterestRate.setText(String.valueOf(loan.getInterestRate()));
        txtThermMonths.setText(String.valueOf(loan.getThermMonths()));
        lblStatus.setText(loan.getStatus() == null ? "—" : loan.getStatus());
    }

    private int parseIntOrThrow(String value, String fieldName) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException(fieldName + " debe ser un número entero válido.");
        }
    }

    private double parseDoubleOrThrow(String value, String fieldName) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException(fieldName + " debe ser un número válido.");
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
