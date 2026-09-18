package main.java.com.jgunzalesindustries.banco.sahur.service;

import main.java.com.jgunzalesindustries.abarroteria.kinal.security.jbcrypt.BCrypt;
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
            
            throw new RuntimeException("No puedes dejar compos en blanco");
        }
        
        LoginDTOResponse response = authRepository.findUserByEmail(request);
        
        if(response == null){
            System.out.println("No se encontro el usuario ");
        }
        
        if(response.getContrasenaHash() == null){
            
            throw new RuntimeException("No se ha podido concretar la operación.");
        }else{
            
            if(BCrypt.checkpw(request.getPassword(), response.getContrasenaHash())){
                
                return response; 
            }
        }
        return null;
    }
    
}

        