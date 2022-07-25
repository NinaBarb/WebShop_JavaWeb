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
public class Product implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String title;
    private String description;
    private String picturePath;
    private double price;
    private int categoryId;

    public Product() {
    }

    public Product(int id, String title, String description, String picturePath, double price, int categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.picturePath = picturePath;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Product(String title, String description, String picturePath, double price, int categoryId) {
        this.title = title;
        this.description = description;
        this.picturePath = picturePath;
        this.price = price;
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
