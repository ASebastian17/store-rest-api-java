package com.asebas.api.repositories.PaymentType;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.asebas.api.domain.PaymentType;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentTypeRepositoryImpl implements PaymentTypeRepository {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM payment_types WHERE payment_type_id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM payment_types";
    private static final String SQL_CREATE = "INSERT INTO payment_types(payment_type_id, payment_name, description) VALUES (NEXTVAL('payment_type_seq'), ?, ?)";
    private static final String SQL_UPDATE = "UPDATE payment_types SET payment_name = ?, description = ? WHERE payment_type_id = ?";
    private static final String SQL_DELETE = "DELETE FROM payment_types WHERE payment_type_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public PaymentType findById(Integer paymentTypeId) throws CResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, paymentTypeRowMapper, new Object[] { paymentTypeId });
        } catch (Exception e) {
            throw new CResourceNotFoundException("Payment type not found");
        }
    }

    @Override
    public List<PaymentType> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, paymentTypeRowMapper);
    }

    @Override
    public Integer create(String paymentName, String description) throws CBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, paymentName);
                ps.setString(2, description);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("payment_type_id");

        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer paymentTypeId, PaymentType paymentType) throws CBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE,
                    new Object[] { paymentType.getPaymentName(), paymentType.getDescription(), paymentTypeId });
        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer paymentTypeId) throws CResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[] { paymentTypeId });

        if (count == 0) {
            throw new CResourceNotFoundException("Payment type not found");
        }
    }

    private RowMapper<PaymentType> paymentTypeRowMapper = ((rs, rowNum) -> {
        return new PaymentType(
                rs.getInt("payment_type_id"),
                rs.getString("payment_name"),
                rs.getString("description"));
    });

}
