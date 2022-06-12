/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Nina
 */
public class OrderDetail {
    private final String productName;
    private final float subtotal;
    private final float shipping;
    private final float tax;
    private final float total;
 
    public OrderDetail(String productName, String subtotal,
            String shipping, String tax, String total) {
        this.productName = productName;
        this.subtotal = Float.parseFloat(subtotal);
        this.shipping = Float.parseFloat(shipping);
        this.tax = Float.parseFloat(tax);
        this.total = Float.parseFloat(total);
    }
 
    public String getProductName() {
        return productName;
    }
 
    public String getSubtotal() {
        return String.format("%.3f", subtotal);
    }
 
    public String getShipping() {
        return String.format("%.3f", shipping);
    }
 
    public String getTax() {
        return String.format("%.3f", tax);
    }
     
    public String getTotal() {
        return String.format("%.3f", total);
    }
}
