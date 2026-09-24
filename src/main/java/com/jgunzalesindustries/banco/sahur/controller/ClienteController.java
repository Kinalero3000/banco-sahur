package main.java.com.jgunzalesindustries.banco.sahur.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import main.java.com.jgunzalesindustries.banco.sahur.dto.request.ClienteDTORequest;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.ClienteDTOResponse;
import main.java.com.jgunzalesindustries.banco.sahur.service.ClienteService;

public class ClienteController {

    @FXML
    private TextField dpiField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;

    @FXML
    private TableView<ClienteDTOResponse> clienteTable;
    @FXML
    private TableColumn<ClienteDTOResponse, String> dpiColumn;
    @FXML
    private TableColumn<ClienteDTOResponse, String> firstNameColumn;
    @FXML
    private TableColumn<ClienteDTOResponse, String> lastNameColumn;
    @FXML
    private TableColumn<ClienteDTOResponse, String> phoneColumn;
    @FXML
    private TableColumn<ClienteDTOResponse, String> emailColumn;

    @FXML
    private Label statusLabel;

    private final ClienteService clienteService = new ClienteService();
    private ClienteDTOResponse selectedCliente;

    @FXML
    public void initialize() {
        dpiColumn.setCellValueFactory(new PropertyValueFactory<>("dpi"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        clienteTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            selectedCliente = newValue;
            if (newValue != null) {
                fillForm(newValue);
            }
        });

        refreshTable();
    }

    @FXML
    private void onSave() {
        try {
            ClienteDTORequest request = buildRequestFromForm();
            if (selectedCliente == null) {
                clienteService.registerCliente(request);
                statusLabel.setText("Cliente registrado correctamente.");
            } else {
                clienteService.updateCliente(selectedCliente.getId(), request);
                statusLabel.setText("Cliente actualizado correctamente.");
            }
            clearForm();
            refreshTable();
        } catch (IllegalArgumentException e) {
            statusLabel.setText(e.getMessage());
        } catch (RuntimeException e) {
            statusLabel.setText("Ocurrió un error al guardar. Revisa la conexión a la base de datos.");
        }
    }

    @FXML
    private void onDelete() {
        if (selectedCliente == null) {
            statusLabel.setText("Selecciona un cliente de la tabla para eliminar.");
            return;
        }
        try {
            clienteService.deleteCliente(selectedCliente.getId());
            clearForm();
            refreshTable();
            statusLabel.setText("Cliente eliminado.");
        } catch (IllegalArgumentException e) {
            statusLabel.setText(e.getMessage());
        } catch (RuntimeException e) {
            statusLabel.setText("No se pudo eliminar el cliente. Verifica que no tenga préstamos asociados.");
        }
    }

    @FXML
    private void onClear() {
        clearForm();
    }

    private void refreshTable() {
        try {
            ObservableList<ClienteDTOResponse> data =
                    FXCollections.observableArrayList(clienteService.listClientes());
            clienteTable.setItems(data);
        } catch (RuntimeException e) {
            statusLabel.setText("No se pudo cargar la lista de clientes. Revisa la conexión a la base de datos.");
        }
    }

    private void fillForm(ClienteDTOResponse cliente) {
        dpiField.setText(cliente.getDpi());
        firstNameField.setText(cliente.getFirstName());
        lastNameField.setText(cliente.getLastName());
        phoneField.setText(cliente.getPhone());
        emailField.setText(cliente.getEmail());
        addressField.setText(cliente.getAddress());
    }

    private void clearForm() {
        selectedCliente = null;
        clienteTable.getSelectionModel().clearSelection();
        dpiField.clear();
        firstNameField.clear();
        lastNameField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
    }

    private ClienteDTORequest buildRequestFromForm() {
        ClienteDTORequest request = new ClienteDTORequest();
        request.setDpi(dpiField.getText() == null ? "" : dpiField.getText().trim());
        request.setFirstName(firstNameField.getText() == null ? "" : firstNameField.getText().trim());
        request.setLastName(lastNameField.getText() == null ? "" : lastNameField.getText().trim());
        request.setPhone(phoneField.getText() == null ? "" : phoneField.getText().trim());
        request.setEmail(emailField.getText() == null ? "" : emailField.getText().trim());
        request.setAddress(addressField.getText() == null ? "" : addressField.getText().trim());
        return request;
    }
}