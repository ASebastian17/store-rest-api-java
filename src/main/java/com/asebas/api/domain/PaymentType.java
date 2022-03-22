package com.asebas.api.domain;

public class PaymentType {

    private Integer paymentTypeId;
    private String paymentName;
    private String description;

    public PaymentType(Integer paymentTypeId, String paymentName, String description) {
        this.setPaymentTypeId(paymentTypeId);
        this.setPaymentName(paymentName);
        this.setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

}
