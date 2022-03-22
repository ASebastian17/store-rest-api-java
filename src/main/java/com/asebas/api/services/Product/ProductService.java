package com.asebas.api.services.Product;

import java.util.List;

import com.asebas.api.domain.Product;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface ProductService {

    List<Product> fetchAllProducts();

    Product fetchProductById(Integer productId) throws CResourceNotFoundException;

    Product addProduct(String barcode, String productName, String description, Integer stock, Double price,
            String model, String brand, String category, String provider) throws CBadRequestException;

    void updateProduct(Integer productId, Product product) throws CBadRequestException;

    void deleteProduct(Integer productId) throws CResourceNotFoundException;

    Integer getCount();

}
