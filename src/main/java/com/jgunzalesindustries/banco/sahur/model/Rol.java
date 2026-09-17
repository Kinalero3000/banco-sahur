/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.jgunzalesindustries.banco.sahur.model;

/**
 *
 * @author angel
 */
public class Rol {
    private String idRol;
    private int rol;

    public Rol(String idRol, int rol) {
        this.idRol = idRol;
        this.rol = rol;
    }

    public String getIdRol() {
        return idRol;
    }

    public int getRol() {
        return rol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
    
}
