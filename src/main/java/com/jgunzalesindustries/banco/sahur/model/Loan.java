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
    private String idLoan;
    private String idClient;
    private int idTypeCredit;
   private Double amount;
   private int interestRate;
   private int thermMonths;
private LocalDate requestDate;
private LocalDate approvalDate;
private String status;

    public Loan(String idLoan, String idClient, int idTypeCredit, Double amount, int interestRate, int thermMonths, LocalDate requestDate, LocalDate approvalDate, String status) {
        this.idLoan = idLoan;
        this.idClient = idClient;
        this.idTypeCredit = idTypeCredit;
        this.amount = amount;
        this.interestRate = interestRate;
        this.thermMonths = thermMonths;
        this.requestDate = requestDate;
        this.approvalDate = approvalDate;
        this.status = status;
    }

    public String getIdLoan() {
        return idLoan;
    }

    public String getIdClient() {
        return idClient;
    }

    public int getidTypeCredit() {
        return idTypeCredit;
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

    public void setIdLoan(String idLoan) {
        this.idLoan = idLoan;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public void setidTypeCredit(int idTypeCredit) {
        this.idTypeCredit = idTypeCredit;
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
