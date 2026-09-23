/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.jgunzalesindustries.banco.sahur.dto.request;

/**
 *
 * @author laraa
 */
public class RegisterDTORequest {
     private String name;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private int idRol;

    public RegisterDTORequest(String nombre, String apellido, String email, String password, String confirmarPassword, int idRol) {
        this.name = nombre;
        this.lastName = apellido;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmarPassword;
        this.idRol = idRol;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String apellido) {
        this.lastName = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmarPassword) {
        this.confirmPassword = confirmarPassword;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}
