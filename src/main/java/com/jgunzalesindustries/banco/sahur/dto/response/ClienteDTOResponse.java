/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.jgunzalesindustries.banco.sahur.dto.response;

import java.time.LocalDate;

/**
 *
 * @author Principal
 */
public class ClienteDTOResponse {
    
    private String id;
    private String dpi;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private java.time.LocalDate registrationDate;
    
    public ClienteDTOResponse(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    public ClienteDTOResponse(String id, String dpi, String firstName, String lastName,
                               String phone, String email, String address, java.time.LocalDate registrationDate){
        this.id = id;
        this.dpi = dpi;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.registrationDate = registrationDate;
    }
    

}
