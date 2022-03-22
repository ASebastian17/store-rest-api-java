package com.asebas.api.repositories.Sale;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asebas.api.domain.Sale;
import com.asebas.api.domain.SaleDetail;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class SaleRepositoryImpl implements SaleRepository {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM sales_vw WHERE sale_id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM sales_vw";
    private static final String SQL_CREATE_SALE = "INSERT INTO sales(sale_id, client_id, user_id, payment_type_id) VALUES (NEXTVAL('sales_seq'), ?, ?, ?)";
    private static final String SQL_CREATE_SALE_DETAILS = "INSERT INTO sales_details(sale_detail_id, sale_id, product_id, quantity, sell_price, discount) VALUES(NEXTVAL('sales_details_seq'), ?, ?, ?, ?, ?)";
    private static final String SQL_GET_CLIENT_ID = "SELECT client_id FROM clients WHERE dni = ?";
    private static final String SQL_GET_PAYMENT_TYPE_ID = "SELECT payment_type_id FROM payment_types WHERE payment_name = ?";
    private static final String SQL_GET_PRODUCT_ID = "SELECT product_id FROM products WHERE product_name = ?";
    private static final String SQL_GET_PRODUCT_PRICE = "SELECT price FROM products WHERE product_name = ?";
    private static final String SQL_GET_PRODUCT_STOCK = "SELECT stock FROM products WHERE product_name = ?";
    private static final String SQL_DECREASE_PRODUCT_STOCK = "UPDATE products SET stock = stock - ? WHERE product_id = ?";
    private static final String SQL_DELETE_SALE = "DELETE FROM sales WHERE sale_id = ?";
    private static final String SQL_DELETE_ALL_DETAILS = "DELETE FROM sales_details WHERE sale_id = ?";
    private static final String SQL_GET_DETAILS = "SELECT P.product_name, quantity, sell_price, discount FROM sales_details SD JOIN products P ON P.product_id = SD.product_id WHERE sale_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Sale findById(Integer saleId) throws CResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, saleRowMapper, new Object[] { saleId });
        } catch (Exception e) {
            throw new CResourceNotFoundException("Sale not found");
        }
    }

    @Override
    public List<SaleDetail> findAndGetDetails(Integer saleId) throws CResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_GET_DETAILS, saleDetailRowMapper, new Object[] { saleId });
        } catch (Exception e) {
            throw new CResourceNotFoundException("Sale not found");
        }
    }

    @Override
    public List<Sale> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, saleRowMapper);
    }

    @Override
    public Integer create(Integer userId, Integer clientDni, String payment, ArrayList<Map<String, Object>> items)
            throws CBadRequestException {
        try {
            Integer clientId = this.getClientId(clientDni);
            Integer paymentTypeId = this.getPaymentTypeId(payment);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_SALE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, clientId);
                ps.setInt(2, userId);
                ps.setInt(3, paymentTypeId);

                return ps;
            }, keyHolder);

            int saleId = (Integer) keyHolder.getKeys().get("sale_id");

            // For loop for creating sales details
            for (int i = 0; i < items.size(); i++) {
                String productName = (String) items.get(i).get("product");
                int quantity = (Integer) items.get(i).get("quantity");
                Double discount = (Double) items.get(i).get("discount");

                Integer productId = this.getProductId(productName);
                Double productPrice = this.getProductPrice(productName);

                int stock = this.getProductStock(productName);

                if (stock - quantity < 0)
                    throw new CBadRequestException("Not enough stock");

                jdbcTemplate.update(SQL_CREATE_SALE_DETAILS,
                        new Object[] { saleId, productId, quantity, productPrice, discount });

                jdbcTemplate.update(SQL_DECREASE_PRODUCT_STOCK, new Object[] { quantity, productId });
            }

            return saleId;

        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer saleId) throws CResourceNotFoundException {
        this.removeAllDetails(saleId);
        jdbcTemplate.update(SQL_DELETE_SALE, new Object[] { saleId });
    }

    private void removeAllDetails(Integer saleId) {
        jdbcTemplate.update(SQL_DELETE_ALL_DETAILS, new Object[] { saleId });
    }

    private Integer getClientId(Integer clientDni) {
        return jdbcTemplate.queryForObject(SQL_GET_CLIENT_ID, Integer.class, new Object[] { clientDni });
    }

    private Integer getPaymentTypeId(String paymentName) {
        return jdbcTemplate.queryForObject(SQL_GET_PAYMENT_TYPE_ID, Integer.class, new Object[] { paymentName });
    }

    private Integer getProductId(String productName) {
        return jdbcTemplate.queryForObject(SQL_GET_PRODUCT_ID, Integer.class, new Object[] { productName });
    }

    private Double getProductPrice(String productName) {
        return jdbcTemplate.queryForObject(SQL_GET_PRODUCT_PRICE, Double.class, new Object[] { productName });
    }

    private Integer getProductStock(String productName) {
        return jdbcTemplate.queryForObject(SQL_GET_PRODUCT_STOCK, Integer.class, new Object[] { productName });
    }

    private RowMapper<Sale> saleRowMapper = ((rs, rowNum) -> {
        return new Sale(
                rs.getInt("sale_id"),
                rs.getString("date"),
                rs.getString("client"),
                rs.getString("user"),
                rs.getDouble("subtotal"),
                rs.getDouble("discount"),
                rs.getDouble("total"),
                rs.getString("payment"));
    });

    private RowMapper<SaleDetail> saleDetailRowMapper = ((rs, rowNum) -> {
        return new SaleDetail(
                rs.getString("product_name"),
                rs.getInt("quantity"),
                rs.getDouble("sell_price"),
                rs.getDouble("discount"));
    });

}
