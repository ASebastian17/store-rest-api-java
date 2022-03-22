package com.asebas.api.services.PaymentType;

import java.util.List;

import com.asebas.api.domain.PaymentType;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;
import com.asebas.api.repositories.PaymentType.PaymentTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService {

    @Autowired
    PaymentTypeRepository paymentTypeRepository;

    @Override
    public List<PaymentType> fetchAllPaymentTypes() {
        return paymentTypeRepository.findAll();
    }

    @Override
    public PaymentType fetchPaymentTypeById(Integer paymentTypeId) throws CResourceNotFoundException {
        return paymentTypeRepository.findById(paymentTypeId);
    }

    @Override
    public PaymentType addPaymentType(String paymentTypeName, String description) throws CBadRequestException {
        Integer PaymentTypeId = paymentTypeRepository.create(paymentTypeName, description);
        return paymentTypeRepository.findById(PaymentTypeId);
    }

    @Override
    public void updatePaymentType(Integer paymentTypeId, PaymentType paymentType) throws CBadRequestException {
        paymentTypeRepository.update(paymentTypeId, paymentType);
    }

    @Override
    public void deletePaymentType(Integer paymentTypeId) throws CResourceNotFoundException {
        paymentTypeRepository.removeById(paymentTypeId);
    }

}
