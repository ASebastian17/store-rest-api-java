package com.asebas.api.domain;

public class Category {

    private Integer categoryId;
    private String categoryName;
    private String description;

    public Category(Integer categoryId, String categoryName, String description) {
        this.setCategoryId(categoryId);
        this.setCategoryName(categoryName);
        this.setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
