package com.asebas.api.domain;

public class Sale {

    private Integer saleId;
    private String date;
    private String client;
    private String user;
    private Double subtotal;
    private Double discount;
    private Double total;
    private String payment;

    public Sale(Integer saleId, String date, String client, String user, Double subtotal, Double discount,
            Double total, String payment) {
        this.setSaleId(saleId);
        this.setDate(date);
        this.setClient(client);
        this.setUser(user);
        this.setSubtotal(subtotal);
        this.setDiscount(discount);
        this.setTotal(total);
        this.setPayment(payment);
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

}
