package main.java.com.jgunzalesindustries.banco.sahur.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.RolDTOResponse;

import main.java.com.jgunzalesindustries.banco.sahur.model.Rol;
import main.java.com.jgunzalesindustries.banco.sahur.model.User;
import main.java.com.jgunzalesindustries.banco.sahur.service.UserService;
import main.java.com.jgunzalesindustries.banco.sahur.util.SceneManager;


public class UserController implements Initializable {

    private final UserService userService;
    private final SceneManager sceneManager;

    // ----- Campos del formulario -----
    @FXML private TextField txtFieldName;
    @FXML private TextField txtFieldLastName;
    @FXML private TextField txtFieldEmail;
    @FXML private PasswordField txtFieldPassword;
    @FXML private PasswordField txtFieldConfirmPassword;
    @FXML private ComboBox<RolDTOResponse> comboBoxRol;

    // ----- Botones -----
    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnMostrarTodos;

    // ----- Tabla -----
    @FXML private TableView<User> tableUsers;
    @FXML private TableColumn<User, String> colUserID;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, String> colLastName;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, Integer> colRolID;

    public UserController(UserService userService, SceneManager sceneManager) {
        this.userService = userService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarRoles();
        cargarTabla();

        tableUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
            if (newUser != null) {
                cargarFormulario(newUser);
            }
        });
    }

    private void configurarColumnas() {
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRolID.setCellValueFactory(new PropertyValueFactory<>("rolID"));
    }

    private void cargarRoles() {
    try {
        ObservableList<RolDTOResponse> roles = userService.getAllRoles();
        comboBoxRol.setItems(roles);
        comboBoxRol.setConverter(new StringConverter<RolDTOResponse>() {
            @Override
            public String toString(RolDTOResponse rol) {
                return rol == null ? "" : rol.getNombreRol();
            }

            @Override
            public RolDTOResponse fromString(String string) {
                return null;
            }
        });
    } catch (Exception e) {
        sceneManager.showAlertInfo("Error", "No se pudieron cargar los roles",
                e.getMessage(), AlertType.ERROR);
    }
}

    private void cargarTabla() {
        try {
            ObservableList<User> users = userService.getAllUsers();
            tableUsers.setItems(users);
        } catch (Exception e) {
            sceneManager.showAlertInfo("Error", "No se pudieron cargar los usuarios",
                    e.getMessage(), AlertType.ERROR);
        }
    }

    // ---------- READ (mostrar todos) ----------

    @FXML
    public void handleMostrarTodos(ActionEvent event) {
        cargarTabla();
    }

    // ---------- CREATE ----------

    @FXML
    public void handleAgregar(ActionEvent event) {
        try {
            User user = construirUserDesdeFormulario(null, "");
            userService.registerUser(user, txtFieldPassword.getText(), txtFieldConfirmPassword.getText());

            sceneManager.showAlertInfo("Éxito", "Usuario registrado",
                    "El usuario se registró correctamente.", AlertType.INFORMATION);
            handleLimpiar(event);
            cargarTabla();

        } catch (IllegalArgumentException e) {
            sceneManager.showAlertInfo("Datos inválidos", "Verifica los campos", e.getMessage(), AlertType.WARNING);
        } catch (Exception e) {
            sceneManager.showAlertInfo("Error", "No se pudo registrar el usuario",
                    "Ocurrió un error inesperado.", AlertType.ERROR);
        }
    }

    // ---------- UPDATE ----------

    @FXML
    public void handleActualizar(ActionEvent event) {
        try {
            User seleccionado = tableUsers.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                sceneManager.showAlertInfo("Atención", "Selecciona un usuario",
                        "Debes seleccionar un usuario de la tabla para actualizar.", AlertType.WARNING);
                return;
            }

            User user = construirUserDesdeFormulario(seleccionado.getUserID(), seleccionado.getPasswordHash());
            userService.updateUser(user, txtFieldPassword.getText());

            sceneManager.showAlertInfo("Éxito", "Usuario actualizado",
                    "Los datos del usuario se actualizaron correctamente.", AlertType.INFORMATION);
            handleLimpiar(event);
            cargarTabla();

        } catch (IllegalArgumentException e) {
            sceneManager.showAlertInfo("Datos inválidos", "Verifica los campos", e.getMessage(), AlertType.WARNING);
        } catch (Exception e) {
            sceneManager.showAlertInfo("Error", "No se pudo actualizar el usuario",
                    "Ocurrió un error inesperado.", AlertType.ERROR);
        }
    }

    // ---------- DELETE ----------

    @FXML
    public void handleEliminar(ActionEvent event) {
        User seleccionado = tableUsers.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            sceneManager.showAlertInfo("Atención", "Selecciona un usuario",
                    "Debes seleccionar un usuario de la tabla para eliminar.", AlertType.WARNING);
            return;
        }

        try {
            userService.deleteUser(seleccionado.getUserID());

            sceneManager.showAlertInfo("Éxito", "Usuario eliminado",
                    "El usuario fue eliminado correctamente.", AlertType.INFORMATION);
            handleLimpiar(event);
            cargarTabla();

        } catch (IllegalArgumentException e) {
            sceneManager.showAlertInfo("Datos inválidos", "Verifica el usuario", e.getMessage(), AlertType.WARNING);
        } catch (Exception e) {
            sceneManager.showAlertInfo("Error", "No se pudo eliminar el usuario",
                    "Ocurrió un error inesperado.", AlertType.ERROR);
        }
    }

    // ---------- Utilidades de formulario ----------

    @FXML
    public void handleLimpiar(ActionEvent event) {
        txtFieldName.clear();
        txtFieldLastName.clear();
        txtFieldEmail.clear();
        txtFieldPassword.clear();
        txtFieldConfirmPassword.clear();
        comboBoxRol.setValue(null);
        tableUsers.getSelectionModel().clearSelection();
    }

    private void cargarFormulario(User user) {
        txtFieldName.setText(user.getName());
        txtFieldLastName.setText(user.getLastName());
        txtFieldEmail.setText(user.getEmail());
        txtFieldPassword.clear();
        txtFieldConfirmPassword.clear();

        for (RolDTOResponse rol : comboBoxRol.getItems()) {
            if (rol.getIdRol() == user.getRolID()) {
                comboBoxRol.setValue(rol);
                break;
            }
        }
    }

    private User construirUserDesdeFormulario(String userIdExistente, String passwordHashActual) {
        String name = txtFieldName.getText();
        String lastName = txtFieldLastName.getText();
        String email = txtFieldEmail.getText();

        if (comboBoxRol.getValue() == null) {
            throw new IllegalArgumentException("Debes seleccionar un rol.");
        }
        int rolID = comboBoxRol.getValue().getIdRol();

        return new User(userIdExistente, name, lastName, email, passwordHashActual, rolID);
    }
}
