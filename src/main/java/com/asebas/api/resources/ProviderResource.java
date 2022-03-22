package com.asebas.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asebas.api.domain.Provider;
import com.asebas.api.services.Provider.ProviderService;

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
@RequestMapping("/api/providers")
public class ProviderResource {

    @Autowired
    ProviderService providerService;

    @GetMapping("")
    public ResponseEntity<List<Provider>> getAllProviders() {
        List<Provider> providers = providerService.fetchAllProviders();

        return new ResponseEntity<>(providers, HttpStatus.OK);
    }

    @GetMapping("/{providerId}")
    public ResponseEntity<Provider> getProviderById(@PathVariable("providerId") Integer providerId) {
        Provider provider = providerService.fetchProviderById(providerId);

        return new ResponseEntity<>(provider, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Provider> addProvider(@RequestBody Map<String, Object> providerMap) {
        String ruc = (String) providerMap.get("ruc");
        String provName = (String) providerMap.get("provName");
        String email = (String) providerMap.get("email");
        String phone = (String) providerMap.get("phone");
        String web = (String) providerMap.get("web");

        Provider provider = providerService.addProvider(ruc, provName, email, phone, web);

        return new ResponseEntity<>(provider, HttpStatus.OK);
    }

    @PutMapping("/{providerId}")
    public ResponseEntity<Map<String, Boolean>> updateProvider(@PathVariable("providerId") Integer providerId,
            @RequestBody Provider provider) {
        providerService.updateProvider(providerId, provider);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{providerId}")
    public ResponseEntity<Map<String, Boolean>> deleteProvider(@PathVariable("providerId") Integer providerId) {
        providerService.deleteProvider(providerId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
