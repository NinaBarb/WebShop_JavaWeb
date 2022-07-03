/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author Nina
 */
public class OrderDetail implements Serializable {

    private final String productName;
    private final int quantity;
    private final float subtotal;
    private final float shipping;
    private final float tax;
    private final float total;

    public OrderDetail(String productName, String quantity, String subtotal,
            String shipping, String tax, String total) {
        this.productName = productName;
        this.quantity = Integer.parseInt(quantity);
        this.subtotal = Float.parseFloat(subtotal);
        this.shipping = Float.parseFloat(shipping);
        this.tax = Float.parseFloat(tax);
        this.total = Float.parseFloat(total);
    }

    /*public OrderDetail(String productName, int quantity, float subtotal,
    float shipping, float tax, float total) {
    this.productName = productName;
    this.quantity = quantity;
    this.subtotal = subtotal;
    this.shipping = shipping;
    this.tax = tax;
    this.total = total;
    }*/
    public String getQuantity() {
        return String.format("%o", quantity);
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
