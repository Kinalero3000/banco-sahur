package main.java.com.jgunzalesindustries.banco.sahur.model;

import java.time.LocalDate;


    public class Clients {
        private String IdCliente;
        private String DPI;
        private String name;
        private String lastName;
        private String phone;
        private String email;
        private String address;
        private LocalDate registerDate;

    public Clients(String IdCliente, String DPI, String name, String lastName, String phone, String email, String address, LocalDate registerDate) {
        this.IdCliente = IdCliente;
        this.DPI = DPI;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.registerDate = registerDate;
    }

    public String getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(String IdCliente) {
        this.IdCliente = IdCliente;
    }

    public String getDPI() {
        return DPI;
    }

    public void setDPI(String DPI) {
        this.DPI = DPI;
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

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }
        
        
    }
