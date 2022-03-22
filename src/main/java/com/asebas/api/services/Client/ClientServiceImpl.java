package com.asebas.api.services.Client;

import java.util.List;
import java.util.regex.Pattern;

import com.asebas.api.domain.Client;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;
import com.asebas.api.repositories.Client.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> fetchAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client fetchClientById(Integer clientId) throws CResourceNotFoundException {
        return clientRepository.findById(clientId);
    }

    @Override
    public Client addClient(String firstName, String lastName, Integer dni, String address, String address2,
            String phone, String email) throws CBadRequestException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");

        if (email != null)
            email = email.toLowerCase();

        if (!pattern.matcher(email).matches())
            throw new CBadRequestException("Invalid email format");

        Integer clientId = clientRepository.create(firstName, lastName, dni, address, address2, phone, email);
        return clientRepository.findById(clientId);
    }

    @Override
    public void updateClient(Integer clientId, Client client) throws CBadRequestException {
        clientRepository.update(clientId, client);
    }

    @Override
    public void removeClient(Integer clientId) throws CResourceNotFoundException {
        clientRepository.removeById(clientId);
    }

}
