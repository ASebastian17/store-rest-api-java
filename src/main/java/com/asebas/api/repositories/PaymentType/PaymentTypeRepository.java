package com.asebas.api.repositories.PaymentType;

import java.util.List;

import com.asebas.api.domain.PaymentType;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface PaymentTypeRepository {

    PaymentType findById(Integer paymentTypeId) throws CResourceNotFoundException;

    List<PaymentType> findAll();

    Integer create(String paymentName, String description) throws CBadRequestException;

    void update(Integer paymentTypeId, PaymentType paymentType) throws CBadRequestException;

    void removeById(Integer paymentTypeId) throws CResourceNotFoundException;

}
