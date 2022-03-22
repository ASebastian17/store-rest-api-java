package com.asebas.api.services.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asebas.api.domain.Sale;
import com.asebas.api.domain.SaleDetail;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface SaleService {

    List<Sale> fetchAllSales();

    List<SaleDetail> fetchSaleDetails(Integer saleId);

    Sale fetchSaleById(Integer saleId) throws CResourceNotFoundException;

    Sale addSale(Integer userId, Integer clientDni, String payment, ArrayList<Map<String, Object>> items) throws CBadRequestException;

    void deleteSale(Integer saleId) throws CResourceNotFoundException;

}
