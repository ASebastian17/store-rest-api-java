package com.asebas.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asebas.api.domain.Client;
import com.asebas.api.services.Client.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientResource {

    @Autowired
    ClientService clientService;

    @GetMapping("")
    public ResponseEntity<List<Client>> getAllCLients() {
        List<Client> clients = clientService.fetchAllClients();

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable("clientId") Integer clientId) {
        Client client = clientService.fetchClientById(clientId);

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Client> addClient(@RequestBody Map<String, Object> clientMap) {
        String firstName = (String) clientMap.get("firstName");
        String lastName = (String) clientMap.get("lastName");
        int dni = (Integer) clientMap.get("dni");
        String address = (String) clientMap.get("address");
        String address2 = (String) clientMap.get("address2");
        String phone = (String) clientMap.get("phone");
        String email = (String) clientMap.get("email");

        Client client = clientService.addClient(firstName, lastName, dni, address, address2, phone, email);

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Map<String, Boolean>> updateClient(@PathVariable("clientId") Integer clientId,
            @RequestBody Client client) {
        clientService.updateClient(clientId, client);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Map<String, Boolean>> deleteClient(@PathVariable("clientId") Integer clientId) {
        clientService.removeClient(clientId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
