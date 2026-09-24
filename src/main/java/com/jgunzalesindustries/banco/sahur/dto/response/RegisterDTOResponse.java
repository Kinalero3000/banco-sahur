package main.java.com.jgunzalesindustries.banco.sahur.dto.response;


public class RegisterDTOResponse {
    private String idUser;
    private String name;
    private String lastName;
    private String email;

    public RegisterDTOResponse(String idUsuario, String nombre, String apellido, String email) {
        this.idUser = idUsuario;
        this.name = nombre;
        this.lastName = apellido;
        this.email = email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
