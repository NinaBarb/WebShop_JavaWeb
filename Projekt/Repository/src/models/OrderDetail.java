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

    public OrderDetail(String productName, String quantity, String subtotal) {
        this.productName = productName;
        this.quantity = Integer.parseInt(quantity);
        this.subtotal = Float.parseFloat(subtotal);
    }

    public String getSubtotal() {
        return String.format("%.3f", subtotal);
    }
    
    public float getSubtotalFloat() {
        return subtotal;
    }
    
    public String getQuantity() {
        return String.format("%o", quantity);
    }

    public String getProductName() {
        return productName;
    }
}
