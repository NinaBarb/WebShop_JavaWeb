/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;


/**
 *
 * @author Nina
 */
public class LoginHistory extends UserAccount{
    
    private int idLoginHistory;
    private String ipAddress;
    private Date dateTime;

    public LoginHistory(int idLoginHistory, String ipAddress, Date dateTime, String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        this.idLoginHistory = idLoginHistory;
        this.ipAddress = ipAddress;
        this.dateTime = dateTime;
    }

    public LoginHistory(Date dateTime, String ipAddress, String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        this.dateTime = dateTime;
        this.ipAddress = ipAddress;
    }

    public int getIdLoginHistory() {
        return idLoginHistory;
    }

    public void setIdLoginHistory(int idLoginHistory) {
        this.idLoginHistory = idLoginHistory;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    
    
}
