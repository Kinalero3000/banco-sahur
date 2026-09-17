/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.jgunzalesindustries.banco.sahur.model;

/**
 *
 * @author angel
 */
public class CreditType {
    private String idCreditType;
    private String name;
    private String desc;
    private int interestRate;
    private int limitMonth;

    public CreditType(String idCreditType, String name, String desc, int interestRate, int limitMonth) {
        this.idCreditType = idCreditType;
        this.name = name;
        this.desc = desc;
        this.interestRate = interestRate;
        this.limitMonth = limitMonth;
    }

    public String getIdCreditType() {
        return idCreditType;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getInterestRate() {
        return interestRate;
    }

    public int getLimitMonth() {
        return limitMonth;
    }

    public void setIdCreditType(String idCreditType) {
        this.idCreditType = idCreditType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    public void setLimitMonth(int limitMonth) {
        this.limitMonth = limitMonth;
    }
    
}
