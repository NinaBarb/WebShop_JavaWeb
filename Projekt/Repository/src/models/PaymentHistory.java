/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import enums.PaymentType;

/**
 *
 * @author Nina
 */
public class PaymentHistory {
    
    private int idPaymentHistory;
    private PaymentType paymentType;
    private int idPayer;
    private String items;

    public PaymentHistory(int idPaymentHistory, PaymentType paymentType, int idPayer, String items) {
        this.idPaymentHistory = idPaymentHistory;
        this.paymentType = paymentType;
        this.idPayer = idPayer;
        this.items = items;
    }

    public PaymentHistory(PaymentType paymentType, int idPayer, String items) {
        this.paymentType = paymentType;
        this.idPayer = idPayer;
        this.items = items;
    }

    public int getIdPaymentHistory() {
        return idPaymentHistory;
    }

    public void setIdPaymentHistory(int idPaymentHistory) {
        this.idPaymentHistory = idPaymentHistory;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public int getPayer() {
        return idPayer;
    }

    public void setPayer(int idPayer) {
        this.idPayer = idPayer;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
    
    
}
