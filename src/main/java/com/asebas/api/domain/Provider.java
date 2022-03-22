package com.asebas.api.domain;

public class Provider {

    private Integer providerId;
    private String ruc;
    private String provName;
    private String email;
    private String phone;
    private String web;

    public Provider(Integer providerId, String ruc, String provName, String email, String phone, String web) {
        this.setProviderId(providerId);
        this.setRuc(ruc);
        this.setProvName(provName);
        this.setEmail(email);
        this.setPhone(phone);
        this.setWeb(web);
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

}
