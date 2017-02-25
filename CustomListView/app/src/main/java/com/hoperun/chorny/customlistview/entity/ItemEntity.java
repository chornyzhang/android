package com.hoperun.chorny.customlistview.entity;

/**
 * Created by Chorny on 2017/2/25.
 */
public class ItemEntity {
    private String tittle;
    private String description;
    private String imageHref;

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public ItemEntity() {

    }

    public ItemEntity(String tittle, String description, String imageHref) {

        this.tittle = tittle;
        this.description = description;
        this.imageHref = imageHref;
    }
}
