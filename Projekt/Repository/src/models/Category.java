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
public class Category {
    private int id;
    private String title;
    private String picturePath;

    public Category(int id, String title, String picturePath) {
        this.id = id;
        this.title = title;
        this.picturePath = picturePath;
    }

    public Category(String title, String picturePath) {
        this.title = title;
        this.picturePath = picturePath;
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

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
