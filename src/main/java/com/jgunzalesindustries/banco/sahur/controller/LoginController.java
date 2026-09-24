package main.java.com.jgunzalesindustries.banco.sahur.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.jgunzalesindustries.banco.sahur.dto.request.LoginDTORequest;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.LoginDTOResponse;
import main.java.com.jgunzalesindustries.banco.sahur.service.AuthService;
import main.java.com.jgunzalesindustries.banco.sahur.util.SceneManager;

public class LoginController implements Initializable {
    //atrbituos
    private final AuthService authService;
    private final SceneManager sceneManager;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private PasswordField txtFieldPassword;
    //constructor 
    public LoginController(AuthService authService, SceneManager sceneManager){
        this.authService = authService;
        this.sceneManager = sceneManager;
    
    }
   
//baten estuvo aca viva alt insert larga vida al sahursismo    
      public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
      
      public void handleIrARegistro() throws Exception{
    sceneManager.showRegistroView();
}
    
    public void handleLogin() throws Exception{
        
        String email = txtFieldEmail.getText() == null ? "" : txtFieldEmail.getText().trim();
        String password = txtFieldPassword.getText() == null ? "" : txtFieldPassword.getText();
        
        if(email.isEmpty() || password.isEmpty()){
            sceneManager.showAlertInfo("Hay campos sin llenar", "No puedes dejar espacios en blanco", "intenta de nuevo", Alert.AlertType.INFORMATION);
            return;
        }
        
        LoginDTOResponse response;
        try{
            response = authService.login(new LoginDTORequest(email, password));
        }catch (RuntimeException e){
            sceneManager.showAlertInfo("Error al iniciar sesion", "Verificar campos", e.getMessage(), Alert.AlertType.WARNING);
            return;
        }
        
        sceneManager.showAlertInfo("Bienvenido "  + response.getNombre() , "Es bueno verte ", "Inicio de sesion correcto. ", Alert.AlertType.INFORMATION);
        sceneManager.showDashBoardView(response.getNombreRol(), email);
    }
}
