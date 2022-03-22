package com.asebas.api.services.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asebas.api.domain.Sale;
import com.asebas.api.domain.SaleDetail;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;
import com.asebas.api.repositories.Sale.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    @Autowired
    SaleRepository saleRepository;

    @Override
    public List<Sale> fetchAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public List<SaleDetail> fetchSaleDetails(Integer saleId) {
        return saleRepository.findAndGetDetails(saleId);
    }

    @Override
    public Sale fetchSaleById(Integer saleId) throws CResourceNotFoundException {
        return saleRepository.findById(saleId);
    }

    @Override
    public Sale addSale(Integer userId, Integer clientDni, String payment, ArrayList<Map<String, Object>> items)
            throws CBadRequestException {
        Integer saleId = saleRepository.create(userId, clientDni, payment, items);
        return saleRepository.findById(saleId);
    }

    @Override
    public void deleteSale(Integer saleId) throws CResourceNotFoundException {
        saleRepository.removeById(saleId);
    }

}
