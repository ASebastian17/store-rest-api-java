package com.asebas.api.repositories.Provider;

import java.util.List;

import com.asebas.api.domain.Provider;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface ProviderRepository {

    Provider findById(Integer providerId) throws CResourceNotFoundException;

    List<Provider> findAll();

    Integer create(String ruc, String provName, String email, String phone, String web) throws CBadRequestException;

    void update(Integer providerId, Provider provider) throws CBadRequestException;

    void removeById(Integer providerId) throws CResourceNotFoundException;

}
