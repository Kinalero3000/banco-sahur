package main.java.com.jgunzalesindustries.banco.sahur.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    //atributos
    private static Connection connection;
    
    /*constructor
    El constructor tiene que ser privado, porque NO permite que la clase sea instanciada
    */
    
    private DataBaseConnection(){
    
    };
    
    //método 
    public static Connection getDataBaseConnection() throws SQLException{
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(Credentials.URL_DB, Credentials.USER_DB, Credentials.PASS_DB);
        }
        return connection; 
    }
}
