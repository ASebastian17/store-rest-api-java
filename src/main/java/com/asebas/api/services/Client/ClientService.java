package com.asebas.api.services.Client;

import java.util.List;

import com.asebas.api.domain.Client;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface ClientService {

    List<Client> fetchAllClients();

    Client fetchClientById(Integer clientId) throws CResourceNotFoundException;

    Client addClient(String firstName, String lastName, Integer dni, String address, String address2, String phone,
            String email) throws CBadRequestException;

    void updateClient(Integer clientId, Client client) throws CBadRequestException;

    void removeClient(Integer clientId) throws CResourceNotFoundException;

}
