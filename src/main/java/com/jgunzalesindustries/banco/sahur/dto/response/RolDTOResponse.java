package main.java.com.jgunzalesindustries.banco.sahur.dto.response;
 
public class RolDTOResponse {
    private int idRol;
    private String nombreRol;
 
    public RolDTOResponse(int idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }
 
    public int getIdRol() {
        return idRol;
    }
 
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
 
    public String getNombreRol() {
        return nombreRol;
    }
 
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
 
    @Override
    public String toString() {
        return nombreRol;
    }
}