package com.asebas.api.services.Provider;

import java.util.List;

import com.asebas.api.domain.Provider;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface ProviderService {

    List<Provider> fetchAllProviders();

    Provider fetchProviderById(Integer providerId) throws CResourceNotFoundException;

    Provider addProvider(String ruc, String provName, String email, String phone, String web)
            throws CBadRequestException;

    void updateProvider(Integer providerId, Provider provider) throws CBadRequestException;

    void deleteProvider(Integer providerId) throws CResourceNotFoundException;

}
