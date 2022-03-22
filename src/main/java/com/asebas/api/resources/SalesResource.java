package com.asebas.api.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.asebas.api.domain.Sale;
import com.asebas.api.domain.SaleDetail;
import com.asebas.api.services.Sale.SaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sales")
public class SalesResource {

    @Autowired
    SaleService saleService;

    @GetMapping("")
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = saleService.fetchAllSales();

        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<Sale> getSaleById(@PathVariable("saleId") Integer saleId) {
        Sale sale = saleService.fetchSaleById(saleId);

        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @GetMapping("/{saleId}/details")
    public ResponseEntity<List<SaleDetail>> getSaleDetails(@PathVariable("saleId") Integer saleId) {
        List<SaleDetail> saleDetails = saleService.fetchSaleDetails(saleId);

        return new ResponseEntity<>(saleDetails, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Sale> addSale(HttpServletRequest request, @RequestBody Map<String, Object> saleMap) {
        int userId = (Integer) request.getAttribute("userId");
        int clientDni = (Integer) saleMap.get("clientDni");
        String payment = (String) saleMap.get("payment");

        @SuppressWarnings("unchecked")
        ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) saleMap.get("items");

        Sale sale = saleService.addSale(userId, clientDni, payment, items);

        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @DeleteMapping("/{saleId}")
    public ResponseEntity<Map<String, Boolean>> deleteSale(@PathVariable("saleId") Integer saleId) {
        saleService.deleteSale(saleId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
