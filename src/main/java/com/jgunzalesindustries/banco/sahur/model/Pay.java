/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.jgunzalesindustries.banco.sahur.model;

import java.time.LocalDate;

/**
 *
 * @author angel
 */
public class Pay {
    private String idPay;
    private String idFees;
    private LocalDate limitDate;
    private Double amount;
    private String payMethod;

    public Pay(String idPay, String idFees, LocalDate limitDate, Double amount, String payMethod) {
        this.idPay = idPay;
        this.idFees = idFees;
        this.limitDate = limitDate;
        this.amount = amount;
        this.payMethod = payMethod;
    }

    public String getIdPay() {
        return idPay;
    }

    public String getIdFees() {
        return idFees;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setIdPay(String idPay) {
        this.idPay = idPay;
    }

    public void setIdFees(String idFees) {
        this.idFees = idFees;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
    
}
