package com.asebas.api.repositories.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.asebas.api.domain.User;
import com.asebas.api.exceptions.CAuthException;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO users (user_id, username, first_name, last_name, password, email, dni, address, phone) VALUES(NEXTVAL('users_seq'), ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String SQL_COUNT_BY_USERNAME = "SELECT COUNT(*) FROM users WHERE username = ?";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM users WHERE email = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM users";
    private static final String SQL_UPDATE = "UPDATE users SET first_name = ?, last_name = ?, email = ?, address = ?, phone = ? WHERE user_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String username, String firstName, String lastName, String password, String email,
            Integer dni, String address, String phone) throws CAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, hashedPassword);
                ps.setString(5, email);
                ps.setInt(6, dni);
                ps.setString(7, address);
                ps.setString(8, phone);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("user_id");

        } catch (Exception e) {
            throw new CAuthException("Invalid details. Failed to register");
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws CAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_USERNAME, userRowMapper, new Object[] { username });

            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new CAuthException("Invalid username/password");
            }

            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new CAuthException("Invalid username/password");
        }
    }

    @Override
    public User findById(Integer userId) throws CResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, userRowMapper, new Object[] { userId });
        } catch (Exception e) {
            throw new CResourceNotFoundException("User not found");
        }
    }

    @Override
    public Integer getCountByUsername(String username) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_USERNAME, Integer.class, new Object[] { username });
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, Integer.class, new Object[] { email });
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public void update(Integer userId, User user) throws CBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[] { user.getFirstName(), user.getLastName(), user.getEmail(),
                    user.getAddress(), user.getPhone(), userId });
        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getInt("dni"),
                rs.getString("address"),
                rs.getString("phone"));
    });

}
