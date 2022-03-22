package com.asebas.api.services.Product;

import java.util.List;

import com.asebas.api.domain.Product;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;
import com.asebas.api.repositories.Product.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> fetchAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product fetchProductById(Integer productId) throws CResourceNotFoundException {
        return productRepository.findById(productId);
    }

    @Override
    public Product addProduct(String barcode, String productName, String description, Integer stock, Double price,
            String model, String brand, String category, String provider) throws CBadRequestException {

        Integer productId = productRepository.create(barcode, productName, description, stock, price, model, brand,
                category, provider);

        return productRepository.findById(productId);
    }

    @Override
    public void updateProduct(Integer productId, Product product) throws CBadRequestException {
        productRepository.update(productId, product);
    }

    @Override
    public void deleteProduct(Integer productId) throws CResourceNotFoundException {
        productRepository.removeById(productId);
    }

    @Override
    public Integer getCount() {
        return productRepository.getCount();
    }

}
