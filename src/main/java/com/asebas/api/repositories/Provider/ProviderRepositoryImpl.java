package com.asebas.api.repositories.Provider;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.asebas.api.domain.Provider;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProviderRepositoryImpl implements ProviderRepository {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM providers WHERE provider_id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM providers";
    private static final String SQL_CREATE = "INSERT INTO providers(provider_id, ruc, prov_name, email, phone, web) VALUES (NEXTVAL('providers_seq'), ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE providers SET ruc = ?, prov_name = ?, email = ?, phone = ?, web = ? WHERE provider_id = ?";
    private static final String SQL_DELETE = "DELETE FROM providers WHERE provider_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Provider findById(Integer providerId) throws CResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, providerRowMapper, new Object[] { providerId });
        } catch (Exception e) {
            throw new CResourceNotFoundException("Provider not found");
        }
    }

    @Override
    public List<Provider> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, providerRowMapper);
    }

    @Override
    public Integer create(String ruc, String provName, String email, String phone, String web)
            throws CBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, ruc);
                ps.setString(2, provName);
                ps.setString(3, email);
                ps.setString(4, phone);
                ps.setString(5, web);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("provider_id");

        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer providerId, Provider provider) throws CBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[] { provider.getRuc(), provider.getProvName(),
                    provider.getEmail(), provider.getPhone(), provider.getWeb(), providerId });
        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer providerId) throws CResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[] { providerId });

        if (count == 0) {
            throw new CResourceNotFoundException("Provider not found");
        }
    }

    private RowMapper<Provider> providerRowMapper = ((rs, rowNum) -> {
        return new Provider(
                rs.getInt("provider_id"),
                rs.getString("ruc"),
                rs.getString("prov_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("web"));
    });

}
