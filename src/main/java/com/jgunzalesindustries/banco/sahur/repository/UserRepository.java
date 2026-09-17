package main.java.com.jgunzalesindustries.banco.sahur.repository;

import main.java.com.jgunzalesindustries.banco.sahur.config.DataBaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

 
    public ObservableList<Rol> findAllRoles() {
    String sql = "SELECT id_rol, nombre_rol FROM roles;";

    try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {

        ResultSet rs = pstm.executeQuery();
        ObservableList<Rol> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            lista.add(new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("nombre_rol")
            ));
        }

        return lista;

    } catch (SQLException e) {
        throw new RuntimeException("Error al consultar los roles.", e);
    }
}
}
 