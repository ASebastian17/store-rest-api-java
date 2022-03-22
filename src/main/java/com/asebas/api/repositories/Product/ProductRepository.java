package com.asebas.api.repositories.Product;

import java.util.List;

import com.asebas.api.domain.Product;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface ProductRepository {

    Product findById(Integer productId) throws CResourceNotFoundException;

    List<Product> findAll();

    Integer create(String barcode, String productName, String description, Integer stock, Double price, String model,
            String brand, String category, String provider) throws CBadRequestException;

    void update(Integer productId, Product product) throws CBadRequestException;

    void removeById(Integer productId) throws CResourceNotFoundException;

    Integer getCount();

}
