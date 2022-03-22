package com.asebas.api.services.PaymentType;

import java.util.List;

import com.asebas.api.domain.PaymentType;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface PaymentTypeService {

    List<PaymentType> fetchAllPaymentTypes();

    PaymentType fetchPaymentTypeById(Integer paymentTypeId) throws CResourceNotFoundException;

    PaymentType addPaymentType(String paymentName, String description) throws CBadRequestException;

    void updatePaymentType(Integer paymentTypeId, PaymentType paymentType) throws CBadRequestException;

    void deletePaymentType(Integer paymentTypeId) throws CResourceNotFoundException;

}
