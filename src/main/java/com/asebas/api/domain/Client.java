package com.asebas.api.domain;

public class Client {

    private Integer clientId;
    private String firstName;
    private String lastName;
    private Integer dni;
    private String address;
    private String address2;
    private String phone;
    private String email;

    public Client(Integer clientId, String firstName, String lastName, Integer dni, String address, String address2,
            String phone, String email) {
        this.setClientId(clientId);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setDni(dni);
        this.setAddress(address);
        this.setAddress2(address2);
        this.setPhone(phone);
        this.setEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

}
