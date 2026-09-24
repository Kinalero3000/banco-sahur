package main.java.com.jgunzalesindustries.banco.sahur.repository;

import main.java.com.jgunzalesindustries.banco.sahur.config.DataBaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.RolDTOResponse;
import main.java.com.jgunzalesindustries.banco.sahur.model.Rol;
import main.java.com.jgunzalesindustries.banco.sahur.model.User;


public class UserRepository {
     public boolean existsByEmail(String email) {
        String sql = "SELECT id_usuario FROM usuarios WHERE email = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {

            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Error al validar el email.", e);
        }
    }

    public boolean save(User user) {
        String sql = "INSERT INTO usuarios (id_usuario, nombre, apellido, email, contrasena_hash, id_rol) VALUES (?, ?, ?, ?, ?, ?);";

        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {

            pstm.setString(1, user.getUserID());
            pstm.setString(2, user.getName());
            pstm.setString(3, user.getLastName());
            pstm.setString(4, user.getEmail());
            pstm.setString(5, user.getPasswordHash());
            pstm.setInt(6, user.getRolID());

            int filasAfectadas = pstm.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar el usuario en la base de datos.", e);
        }
    }

    public String generarIdUsuario() {
        return UUID.randomUUID().toString();
    }

    // Alias en inglés para UserService, sin tocar generarIdUsuario()
    // porque RegisterService ya depende de ese nombre.
    public String generateUserId() {
        return generarIdUsuario();
    }

    public ObservableList<User> findAll() {
        String sql = "SELECT id_usuario, nombre, apellido, email, contrasena_hash, id_rol FROM usuarios;";
        ObservableList<User> lista = FXCollections.observableArrayList();

        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                lista.add(new User(
                        rs.getString("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("contrasena_hash"),
                        rs.getInt("id_rol")
                ));
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar los usuarios.", e);
        }
    }

    public boolean update(User user) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, contrasena_hash = ?, id_rol = ? WHERE id_usuario = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {

            pstm.setString(1, user.getName());
            pstm.setString(2, user.getLastName());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getPasswordHash());
            pstm.setInt(5, user.getRolID());
            pstm.setString(6, user.getUserID());

            int filasAfectadas = pstm.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario en la base de datos.", e);
        }
    }

    public boolean delete(String userId) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {

            pstm.setString(1, userId);
            int filasAfectadas = pstm.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el usuario de la base de datos.", e);
        }
    }

 
    public ObservableList<RolDTOResponse> findAllRoles() {
    String sql = "SELECT id_rol, rol FROM roles;";

    try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {

        ResultSet rs = pstm.executeQuery();
        ObservableList<RolDTOResponse> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            lista.add(new RolDTOResponse(
                    rs.getInt("id_rol"),
                    rs.getString("rol")
            ));
        }

        return lista;

    } catch (SQLException e) {
        throw new RuntimeException("Error al consultar los roles.", e);
    }
}
}
 