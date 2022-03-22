package com.asebas.api.domain;

public class SaleDetail {

    private String product;
    private Integer quantity;
    private Double sellPrice;
    private Double discount;

    public SaleDetail(String product, Integer quantity, Double sellPrice, Double discount) {
        this.setProduct(product);
        this.setQuantity(quantity);
        this.setSellPrice(sellPrice);
        this.setDiscount(discount);

    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

}
