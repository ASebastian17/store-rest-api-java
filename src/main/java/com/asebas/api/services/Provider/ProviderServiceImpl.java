package com.asebas.api.services.Provider;

import java.util.List;

import com.asebas.api.domain.Provider;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;
import com.asebas.api.repositories.Provider.ProviderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    ProviderRepository providerRepository;

    @Override
    public List<Provider> fetchAllProviders() {
        return providerRepository.findAll();
    }

    @Override
    public Provider fetchProviderById(Integer providerId) throws CResourceNotFoundException {
        return providerRepository.findById(providerId);
    }

    @Override
    public Provider addProvider(String ruc, String provName, String email, String phone, String web)
            throws CBadRequestException {
        Integer providerId = providerRepository.create(ruc, provName, email, phone, web);
        return providerRepository.findById(providerId);
    }

    @Override
    public void updateProvider(Integer providerId, Provider provider) throws CBadRequestException {
        providerRepository.update(providerId, provider);
    }

    @Override
    public void deleteProvider(Integer providerId) throws CResourceNotFoundException {
        providerRepository.removeById(providerId);
    }

}
