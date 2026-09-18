package main.java.com.jgunzalesindustries.banco.sahur.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.java.com.jgunzalesindustries.banco.sahur.dto.request.RegisterDTORequest;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.RegisterDTOResponse;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.RolDTOResponse;
import main.java.com.jgunzalesindustries.banco.sahur.repository.UserRepository;
import main.java.com.jgunzalesindustries.banco.sahur.service.RegisterService;
import main.java.com.jgunzalesindustries.banco.sahur.util.SceneManager;


public class RegisterController {
    
public class RegistroController implements Initializable{
     private final RegisterService registerService;
    private final SceneManager sceneManager;
    private final UserRepository userRepository;
    @FXML
    private TextField txtFieldName;
    @FXML
    private TextField txtFieldLastName;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private TextField txtFieldPassword;
    @FXML
    private TextField txtFieldConfirmPassword;
    @FXML
    private ComboBox<RolDTOResponse> comboBoxRol;

     public RegistroController(RegisterService registerService, SceneManager sceneManager, UserRepository usuarioRepository) {
        this.registerService = registerService;
        this.sceneManager = sceneManager;
        this.userRepository = usuarioRepository;
    }

  @Override
public void initialize(URL url, ResourceBundle rb) {
    comboBoxRol.setItems(userRepository.findAllRoles());
}

    public void handleRegistro(ActionEvent event) {
        try {
            RegisterDTORequest request = new RegisterDTORequest(
                    txtFieldName.getText(),
                    txtFieldLastName.getText(),
                    txtFieldEmail.getText(),
                    txtFieldPassword.getText(),
                    txtFieldConfirmPassword.getText(),
                    comboBoxRol.getValue() == null ? 0 : comboBoxRol.getValue().getIdRol()
            );

            RegisterDTOResponse response = registerService.registrar(request);

            sceneManager.showAlertInfo("Registro exitoso", "Bienvenido " + response.getNombre(),
                    "Tu cuenta fue creada correctamente.", AlertType.INFORMATION);
            handleLimpiarCampos();
            sceneManager.showLoginView();

        } catch (IllegalArgumentException e) {
            sceneManager.showAlertInfo("Datos inválidos", "Verifica los campos", e.getMessage(), AlertType.WARNING);
        } catch (Exception e) {
            sceneManager.showAlertInfo("Error", "Error al registrar", "No se pudo completar el registro.", AlertType.ERROR);
        }
    }
    
    public void handleIrALogin() throws Exception{

             sceneManager.showLoginView();
       
    
    };

    public void handleLimpiarCampos() {
        txtFieldName.clear();
        txtFieldLastName.clear();
        txtFieldEmail.clear();
        txtFieldPassword.clear();
        txtFieldConfirmPassword.clear();
        comboBoxRol.setValue(null);
    }
}
}
