package main.java.com.jgunzalesindustries.banco.sahur.repository;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.com.jgunzalesindustries.banco.sahur.config.DataBaseConnection;
import main.java.com.jgunzalesindustries.banco.sahur.dto.request.LoginDTORequest;
import main.java.com.jgunzalesindustries.banco.sahur.dto.response.LoginDTOResponse;

public class AuthRepository{
    public LoginDTOResponse findUserByEmail(LoginDTORequest request){
        
        String sql = "select u.nombre, u.apellido, u.contrasena_hash, r.rol from usuarios as u " +
                            "inner join roles as r " +
                            "on u.id_rol = r.id_rol " +
                            "where u.email = ?";
        
        try(PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)){
            
            pstm.setString(1, request.getEmail());
            ResultSet rs = pstm.executeQuery();
            
            if(rs.next()){
                
                return new LoginDTOResponse(
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("contrasena_hash"),
                rs.getString("rol")
                );
                
            }
            
        }catch(SQLException e){
            throw new RuntimeException("Error al consultar el usuario en la base de datos.", e);
        }
        return null;
    }
}

