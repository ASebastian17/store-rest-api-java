package com.asebas.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asebas.api.domain.Product;
import com.asebas.api.services.Product.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductResource {

    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.fetchAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Integer productId) {
        Product product = productService.fetchProductById(productId);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody Map<String, Object> productMap) {
        String barcode = (String) productMap.get("barcode");
        String productName = (String) productMap.get("productName");
        String description = (String) productMap.get("description");
        Integer stock = (Integer) productMap.get("stock");
        Double price = (Double) productMap.get("price");
        String model = (String) productMap.get("model");
        String brand = (String) productMap.get("brand");
        String category = (String) productMap.get("category");
        String provider = (String) productMap.get("provider");

        Product product = productService.addProduct(barcode, productName, description, stock, price, model, brand,
                category, provider);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Boolean>> updateProduct(@PathVariable("productId") Integer productId,
            @RequestBody Product product) {
        productService.updateProduct(productId, product);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable("productId") Integer productId) {
        productService.deleteProduct(productId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getCountOfProduct() {
        int count = productService.getCount();

        Map<String, Integer> map = new HashMap<>();
        map.put("totalProducts:", count);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
