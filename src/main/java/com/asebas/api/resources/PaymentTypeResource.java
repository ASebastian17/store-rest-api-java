package com.asebas.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asebas.api.domain.PaymentType;
import com.asebas.api.services.PaymentType.PaymentTypeService;

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
@RequestMapping("/api/payments")
public class PaymentTypeResource {

    @Autowired
    PaymentTypeService paymentTypeService;

    @GetMapping("")
    public ResponseEntity<List<PaymentType>> getAllPaymentTypes() {
        List<PaymentType> paymentTypes = paymentTypeService.fetchAllPaymentTypes();

        return new ResponseEntity<>(paymentTypes, HttpStatus.OK);
    }

    @GetMapping("/{paymentTypeId}")
    public ResponseEntity<PaymentType> getPaymentTypeById(@PathVariable("paymentTypeId") Integer paymentTypeId) {
        PaymentType paymentType = paymentTypeService.fetchPaymentTypeById(paymentTypeId);

        return new ResponseEntity<>(paymentType, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PaymentType> addPaymnetType(@RequestBody Map<String, Object> paymentTypeMap) {
        String paymentName = (String) paymentTypeMap.get("paymentName");
        String description = (String) paymentTypeMap.get("description");

        PaymentType paymentType = paymentTypeService.addPaymentType(paymentName, description);

        return new ResponseEntity<>(paymentType, HttpStatus.OK);
    }

    @PutMapping("/{paymentTypeId}")
    public ResponseEntity<Map<String, Boolean>> updatePaymentType(@PathVariable("paymentTypeId") Integer paymentTypeId,
            @RequestBody PaymentType paymentType) {
        paymentTypeService.updatePaymentType(paymentTypeId, paymentType);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{paymentTypeId}")
    public ResponseEntity<Map<String, Boolean>> deletePaymentType(
            @PathVariable("paymentTypeId") Integer paymentTypeId) {
        paymentTypeService.deletePaymentType(paymentTypeId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
