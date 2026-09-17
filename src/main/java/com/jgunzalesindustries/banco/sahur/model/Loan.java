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
public class Loan {
    private String idPay;
    private String idFee;
    private String PayDay;
   private Double amount;
   private int interestRate;
   private int thermMonths;
private LocalDate requestDate;
private LocalDate approvalDate;
private String status;

    public Loan(String idPay, String idFee, String PayDay, Double amount, int interestRate, int thermMonths, LocalDate requestDate, LocalDate approvalDate, String status) {
        this.idPay = idPay;
        this.idFee = idFee;
        this.PayDay = PayDay;
        this.amount = amount;
        this.interestRate = interestRate;
        this.thermMonths = thermMonths;
        this.requestDate = requestDate;
        this.approvalDate = approvalDate;
        this.status = status;
    }

    public String getIdPay() {
        return idPay;
    }

    public String getIdFee() {
        return idFee;
    }

    public String getPayDay() {
        return PayDay;
    }

    public Double getAmount() {
        return amount;
    }

    public int getInterestRate() {
        return interestRate;
    }

    public int getThermMonths() {
        return thermMonths;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setIdPay(String idPay) {
        this.idPay = idPay;
    }

    public void setIdFee(String idFee) {
        this.idFee = idFee;
    }

    public void setPayDay(String PayDay) {
        this.PayDay = PayDay;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    public void setThermMonths(int thermMonths) {
        this.thermMonths = thermMonths;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
