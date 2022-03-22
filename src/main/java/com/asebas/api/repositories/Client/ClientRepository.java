package com.asebas.api.repositories.Client;

import java.util.List;

import com.asebas.api.domain.Client;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface ClientRepository {

    Client findById(Integer clientId) throws CResourceNotFoundException;

    List<Client> findAll();

    Integer create(String firstName, String lastName, Integer dni, String address, String address2, String phone,
            String email) throws CBadRequestException;

    void update(Integer clientId, Client client) throws CBadRequestException;

    void removeById(Integer clientId) throws CResourceNotFoundException;

}
