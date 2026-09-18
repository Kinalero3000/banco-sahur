package main.java.com.jgunzalesindustries.banco.sahur.service;

import main.java.com.jgunzalesindustries.abarroteria.kinal.security.jbcrypt.BCrypt;
import main.java.com.jgunzalesindustries.banco.sahur.dto.request.RegisterDTORequest;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.RegisterDTOResponse;
import main.java.com.jgunzalesindustries.banco.sahur.model.User;
import main.java.com.jgunzalesindustries.banco.sahur.repository.UserRepository;


public class RegisterService {
     private final UserRepository usuarioRepository;

    public RegisterService(UserRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public RegisterDTOResponse registrar(RegisterDTORequest request) {
        validarRequest(request);

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con ese email.");
        }

        String contrasenaHash = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        String idUsuario = usuarioRepository.generarIdUsuario();

        User usuario = new User(idUsuario, request.getName(), request.getLastName(),
                request.getEmail(), contrasenaHash, request.getIdRol());

        boolean guardado = usuarioRepository.save(usuario);

        if (!guardado) {
            throw new RuntimeException("No se pudo registrar el usuario.");
        }

        return new RegisterDTOResponse(usuario.getUserID(), usuario.getName(), usuario.getLastName(), usuario.getEmail());
    }

    private void validarRequest(RegisterDTORequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Los datos de registro están vacíos.");
        }
        if (isBlank(request.getName())) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (isBlank(request.getLastName())) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        if (isBlank(request.getEmail()) || !request.getEmail().contains("@")) {
            throw new IllegalArgumentException("El email no es válido.");
        }
        if (isBlank(request.getPassword()) || request.getPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }
        if (request.getIdRol() <= 0) {
            throw new IllegalArgumentException("Debes seleccionar un rol válido.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
