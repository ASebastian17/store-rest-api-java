package com.asebas.api.repositories.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asebas.api.domain.Sale;
import com.asebas.api.domain.SaleDetail;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface SaleRepository {

    Sale findById(Integer saleId) throws CResourceNotFoundException;

    List<SaleDetail> findAndGetDetails(Integer saleId) throws CResourceNotFoundException;

    List<Sale> findAll();

    Integer create(Integer userId, Integer clientDni, String payment, ArrayList<Map<String, Object>> items)
            throws CBadRequestException;

    void removeById(Integer saleId) throws CResourceNotFoundException;

}
