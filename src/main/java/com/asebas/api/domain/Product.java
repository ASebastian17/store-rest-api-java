package com.asebas.api.domain;

public class Product {

    private Integer productId;
    private String barcode;
    private String productName;
    private String description;
    private Integer stock;
    private Double price;
    private String model;
    private String brand;
    private String category;
    private String provider;

    public Product(Integer productId, String barcode, String productName, String description, Integer stock,
            Double price, String model, String brand, String category, String provider) {
        this.setProductId(productId);
        this.setBarcode(barcode);
        this.setProductName(productName);
        this.setDescription(description);
        this.setStock(stock);
        this.setPrice(price);
        this.setModel(model);
        this.setBrand(brand);
        this.setCategory(category);
        this.setProvider(provider);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

}
