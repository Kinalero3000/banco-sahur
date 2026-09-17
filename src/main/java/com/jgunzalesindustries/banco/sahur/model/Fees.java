/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.jgunzalesindustries.banco.sahur.model;

import java.time.LocalTime;

/**
 *
 * @author angel
 */
public class Fees {
private String idFee;
private String idLoan;
private int feelsNum;
private LocalTime LimitTime;
private Double amountFee;
private Double totalAmaunt;
private Double interest;
private Double calanceDue;
        private String status;

    public Fees(String idCuota, String idLoan, int feelsNum, LocalTime LimitTime, Double amountFee, Double totalAmaunt, Double interest, Double calanceDue, String status) {
        this.idFee= idCuota;
        this.idLoan = idLoan;
        this.feelsNum = feelsNum;
        this.LimitTime = LimitTime;
        this.amountFee = amountFee;
        this.totalAmaunt = totalAmaunt;
        this.interest = interest;
        this.calanceDue = calanceDue;
        this.status = status;
    }

    public String getIdCuota() {
        return idFee;
    }

    public String getIdLoan() {
        return idLoan;
    }

    public int getFeelsNum() {
        return feelsNum;
    }

    public LocalTime getLimitTime() {
        return LimitTime;
    }

    public Double getAmountFee() {
        return amountFee;
    }

    public Double getTotalAmaunt() {
        return totalAmaunt;
    }

    public Double getInterest() {
        return interest;
    }

    public Double getCalanceDue() {
        return calanceDue;
    }

    public String getStatus() {
        return status;
    }

    public void setIdCuota(String idCuota) {
        this.idFee = idFee;
    }

    public void setIdLoan(String idLoan) {
        this.idLoan = idLoan;
    }

    public void setFeelsNum(int feelsNum) {
        this.feelsNum = feelsNum;
    }

    public void setLimitTime(LocalTime LimitTime) {
        this.LimitTime = LimitTime;
    }

    public void setAmountFee(Double amountFee) {
        this.amountFee = amountFee;
    }

    public void setTotalAmaunt(Double totalAmaunt) {
        this.totalAmaunt = totalAmaunt;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public void setCalanceDue(Double calanceDue) {
        this.calanceDue = calanceDue;
    }

    public void setStatus(String status) {
        this.status = status;
    }
        
}
