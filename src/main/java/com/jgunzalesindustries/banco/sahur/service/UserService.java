package main.java.com.jgunzalesindustries.banco.sahur.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.collections.ObservableList;

import main.java.com.jgunzalesindustries.banco.sahur.dto.response.RolDTOResponse;
import main.java.com.jgunzalesindustries.banco.sahur.model.User;
import main.java.com.jgunzalesindustries.banco.sahur.repository.UserRepository;

/**
 * Capa de servicio: reglas de negocio para usuarios (registro, edición, eliminación, roles).
 * No conoce nada de JavaFX/UI ni de SQL.
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ---------- READ ----------

    public ObservableList<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ObservableList<RolDTOResponse> getAllRoles() {
        return userRepository.findAllRoles();
    }

    // ---------- CREATE ----------

    public boolean registerUser(User user, String plainPassword, String confirmPassword) {
        validateDatosBasicos(user);

        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (!plainPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con ese correo.");
        }

        user.setUserID(userRepository.generateUserId());
        user.setPasswordHash(hashPassword(plainPassword));

        return userRepository.save(user);
    }

    // ---------- UPDATE ----------

    public boolean updateUser(User user, String plainPassword) {
        if (user.getUserID() == null || user.getUserID().isBlank()) {
            throw new IllegalArgumentException("El ID de usuario es obligatorio para actualizar.");
        }
        validateDatosBasicos(user);

        // Si mandan una contraseña nueva, se re-hashea; si viene vacía, se conserva la que ya tenía.
        if (plainPassword != null && !plainPassword.isBlank()) {
            user.setPasswordHash(hashPassword(plainPassword));
        }

        return userRepository.update(user);
    }

    // ---------- DELETE ----------

    public boolean deleteUser(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("El ID de usuario es obligatorio.");
        }
        return userRepository.delete(userId);
    }

    // ---------- Helpers privados ----------

    private void validateDatosBasicos(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        if (user.getEmail() == null || !user.getEmail().matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El correo no es válido.");
        }
        if (user.getRolID() <= 0) {
            throw new IllegalArgumentException("Debes seleccionar un rol.");
        }
    }

    /**
     * Hash simple con SHA-256 en hexadecimal. Suficiente para un proyecto de curso;
     * para producción real se recomienda usar BCrypt (con salt) en vez de un hash plano.
     */
    private String hashPassword(String plainPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(plainPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generando el hash de la contraseña.", e);
        }
    }
}
