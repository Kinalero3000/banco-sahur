package main.java.com.jgunzalesindustries.banco.sahur.service;

import main.java.com.jgunzalesindustries.banco.sahur.security.jbcrypt.BCrypt;
import main.java.com.jgunzalesindustries.banco.sahur.dto.request.LoginDTORequest;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.LoginDTOResponse;
import main.java.com.jgunzalesindustries.banco.sahur.repository.AuthRepository;


public class AuthService {
    
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    
    public LoginDTOResponse login(LoginDTORequest request){
        
        if(request == null){
            
            throw new RuntimeException("Los datos están vacios");
        }else if(request.getEmail() == null || request.getPassword() == null){
            
            throw new RuntimeException("Uno o los dos campos están vacios");           
        }else if(request.getEmail().isEmpty() || request.getPassword().isEmpty()){
            
            throw new RuntimeException("No puedes dejar campos en blanco");
        }
        
        LoginDTOResponse response = authRepository.findUserByEmail(request);
        
        if(response == null){
            throw new RuntimeException("Correo o contraseña incorrectos.");
        }
        
        if(response.getContrasenaHash() == null){
            
            throw new RuntimeException("No se ha podido concretar la operación.");
        }
        
        boolean coincide;
        try{
            coincide = BCrypt.checkpw(request.getPassword(), response.getContrasenaHash());
        }catch(IllegalArgumentException | StringIndexOutOfBoundsException e){
            throw new RuntimeException("La contraseña de este usuario no es válida. Pide que la restablezcan.");
        }
        
        if(!coincide){
            throw new RuntimeException("Correo o contraseña incorrectos.");
        }
        return response;
    }
    
}

        