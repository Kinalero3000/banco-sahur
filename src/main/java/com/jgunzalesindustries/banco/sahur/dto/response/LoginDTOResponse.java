package main.java.com.jgunzalesindustries.banco.sahur.dto.response;

public class LoginDTOResponse {
      private String name;
    private String lastName;
    private String passwordHash;
    private String rolName;

    public LoginDTOResponse(String nombre, String apellido, String contrasenaHash, String nombreRol) {
        this.name = nombre;
        this.lastName = apellido;
        this.passwordHash = contrasenaHash;
        this.rolName = nombreRol;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public String getApellido() {
        return lastName;
    }

    public void setApellido(String apellido) {
        this.lastName = apellido;
    }

    public String getContrasenaHash() {
        return passwordHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.passwordHash = contrasenaHash;
    }

    public String getNombreRol() {
        return rolName;
    }

    public void setNombreRol(String nombreRol) {
        this.rolName = nombreRol;
    }
}
