package com.asebas.api.repositories.Client;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.asebas.api.domain.Client;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM clients WHERE client_id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM clients";
    private static final String SQL_CREATE = "INSERT INTO clients(client_id, first_name, last_name, dni, address, address2, phone, email) VALUES (NEXTVAL('clients_seq'), ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE clients SET first_name = ?, last_name = ?, dni = ?, address = ?, address2 = ?, phone = ?, email = ? WHERE client_id = ?";
    private static final String SQL_DELETE = "DELETE FROM clients WHERE client_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Client findById(Integer clientId) throws CResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, clientRowMapper, new Object[] { clientId });
        } catch (Exception e) {
            throw new CResourceNotFoundException("Client not found");
        }
    }

    @Override
    public List<Client> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, clientRowMapper);
    }

    @Override
    public Integer create(String firstName, String lastName, Integer dni, String address, String address2, String phone,
            String email) throws CBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setInt(3, dni);
                ps.setString(4, address);
                ps.setString(5, address2);
                ps.setString(6, phone);
                ps.setString(7, email);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("client_id");

        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer clientId, Client client) throws CBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE,
                    new Object[] { client.getFirstName(), client.getLastName(), client.getDni(), client.getAddress(),
                            client.getAddress2(), client.getPhone(), client.getEmail(), clientId });
        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer clientId) throws CResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[] { clientId });

        if (count == 0) {
            throw new CResourceNotFoundException("Client not found");
        }
    }

    private RowMapper<Client> clientRowMapper = ((rs, rowNum) -> {
        return new Client(
                rs.getInt("client_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("dni"),
                rs.getString("address"),
                rs.getString("address2"),
                rs.getString("phone"),
                rs.getString("email"));
    });

}
