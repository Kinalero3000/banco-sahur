package main.java.com.jgunzalesindustries.banco.sahur.repository;

import main.java.com.jgunzalesindustries.banco.sahur.config.DataBaseConnection;
import main.java.com.jgunzalesindustries.banco.sahur.model.Clients;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepository {

    public void save(Clients cliente) {
        String sql = "INSERT INTO clientes (id_cliente, dpi, nombre, apellido, telefono, correo, direccion, fecha_registro) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            ps.setString(1, cliente.getIdCliente());
            ps.setString(2, cliente.getDPI());
            ps.setString(3, cliente.getName());
            ps.setString(4, cliente.getLastName());
            ps.setString(5, cliente.getPhone());
            ps.setString(6, cliente.getEmail());
            ps.setString(7, cliente.getAddress());
            ps.setDate(8, Date.valueOf(cliente.getRegisterDate()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el cliente: " + e.getMessage(), e);
        }
    }

    public void update(Clients cliente) {
        String sql = "UPDATE clientes SET dpi = ?, nombre = ?, apellido = ?, telefono = ?, correo = ?, direccion = ? "
                + "WHERE id_cliente = ?";
        try (PreparedStatement ps = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            ps.setString(1, cliente.getDPI());
            ps.setString(2, cliente.getName());
            ps.setString(3, cliente.getLastName());
            ps.setString(4, cliente.getPhone());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getAddress());
            ps.setString(7, cliente.getIdCliente());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el cliente: " + e.getMessage(), e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try (PreparedStatement ps = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el cliente: " + e.getMessage(), e);
        }
    }

    public Optional<Clients> findById(String id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (PreparedStatement ps = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el cliente: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public Optional<Clients> findByDpi(String dpi) {
        String sql = "SELECT * FROM clientes WHERE dpi = ?";
        try (PreparedStatement ps = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            ps.setString(1, dpi);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el cliente por DPI: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public Optional<Clients> findByEmail(String email) {
        String sql = "SELECT * FROM clientes WHERE correo = ?";
        try (PreparedStatement ps = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el cliente por correo: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Clients> findAll() {
        String sql = "SELECT * FROM clientes ORDER BY fecha_registro DESC";
        List<Clients> clientes = new ArrayList<>();
        try (PreparedStatement ps = DataBaseConnection.getDataBaseConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los clientes: " + e.getMessage(), e);
        }
        return clientes;
    }

    private Clients mapRow(ResultSet rs) throws SQLException {
        return new Clients(
                rs.getString("id_cliente"),
                rs.getString("dpi"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("direccion"),
                rs.getDate("fecha_registro") != null ? rs.getDate("fecha_registro").toLocalDate() : null
        );
    }
}