package com.asebas.api.repositories.Product;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.asebas.api.domain.Product;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private static final String SQL_FIND_BY_ID = "SELECT P.product_id, P.barcode, P.product_name, P.description, P.stock, P.price, P.model, P.brand, "
            + "C.category_name category, PR.prov_name provider FROM products P "
            + "JOIN categories C ON P.category_id = C.category_id "
            + "JOIN providers PR ON P.provider_id = PR.provider_id WHERE product_id = ?";
    private static final String SQL_FIND_ALL = "SELECT P.product_id, P.barcode, P.product_name, P.description, P.stock, P.price, P.model, P.brand, "
            + "C.category_name category, PR.prov_name provider FROM products P "
            + "JOIN categories C ON P.category_id = C.category_id "
            + "JOIN providers PR ON P.provider_id = PR.provider_id";
    private static final String SQL_CREATE = "INSERT INTO products(product_id, barcode, product_name, description, stock, price, model, brand, category_id, provider_id) VALUES (NEXTVAL('products_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE products SET barcode = ?, product_name = ?, description = ?, stock = ?, price = ?, model = ?, brand = ?, category_id = ?, provider_id = ? WHERE product_id = ?";
    private static final String SQL_DELETE = "DELETE FROM products WHERE product_id = ?";
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM products";
    private static final String SQL_GET_CATEGORY_ID = "SELECT category_id FROM categories WHERE category_name = ?";
    private static final String SQL_GET_PROVIDER_ID = "SELECT provider_id FROM providers WHERE prov_name = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Product findById(Integer productId) throws CResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, productRowMapper, new Object[] { productId });
        } catch (Exception e) {
            throw new CResourceNotFoundException("Product not found");
        }
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, productRowMapper);
    }

    @Override
    public Integer create(String barcode, String productName, String description, Integer stock, Double price,
            String model, String brand, String category, String provider) throws CBadRequestException {
        try {
            Integer categoryId = this.getCategoryId(category);
            Integer providerId = this.getProviderId(provider);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, barcode);
                ps.setString(2, productName);
                ps.setString(3, description);
                ps.setInt(4, stock);
                ps.setDouble(5, price);
                ps.setString(6, model);
                ps.setString(7, brand);
                ps.setInt(8, categoryId);
                ps.setInt(9, providerId);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("product_id");

        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer productId, Product product) throws CBadRequestException {
        try {
            Integer categoryId = this.getCategoryId(product.getCategory());
            Integer providerId = this.getProviderId(product.getProvider());

            jdbcTemplate.update(SQL_UPDATE,
                    new Object[] { product.getBarcode(), product.getProductName(), product.getDescription(),
                            product.getStock(), product.getPrice(), product.getModel(), product.getBrand(), categoryId,
                            providerId, productId });
        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer productId) throws CResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[] { productId });

        if (count == 0) {
            throw new CResourceNotFoundException("Product not found");
        }
    }

    @Override
    public Integer getCount() {
        return jdbcTemplate.queryForObject(SQL_COUNT, Integer.class);
    }

    private Integer getCategoryId(String categoryName) {
        return jdbcTemplate.queryForObject(SQL_GET_CATEGORY_ID, Integer.class, new Object[] { categoryName });
    }

    private Integer getProviderId(String provName) {
        return jdbcTemplate.queryForObject(SQL_GET_PROVIDER_ID, Integer.class, new Object[] { provName });
    }

    private RowMapper<Product> productRowMapper = ((rs, rowNum) -> {
        return new Product(
                rs.getInt("product_id"),
                rs.getString("barcode"),
                rs.getString("product_name"),
                rs.getString("description"),
                rs.getInt("stock"),
                rs.getDouble("price"),
                rs.getString("model"),
                rs.getString("brand"),
                rs.getString("category"),
                rs.getString("provider"));
    });

}
