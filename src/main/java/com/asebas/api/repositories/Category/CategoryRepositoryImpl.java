package com.asebas.api.repositories.Category;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.asebas.api.domain.Category;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM categories WHERE category_id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM categories";
    private static final String SQL_CREATE = "INSERT INTO categories(category_id, category_name, description) VALUES (NEXTVAL('categories_seq'), ?, ?)";
    private static final String SQL_UPDATE = "UPDATE categories SET category_name = ?, description = ? WHERE category_id = ?";
    private static final String SQL_DELETE = "DELETE FROM categories WHERE category_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Category findById(Integer categoryId) throws CResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, categoryRowMapper, new Object[] { categoryId });
        } catch (Exception e) {
            throw new CResourceNotFoundException("Category not found");
        }
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, categoryRowMapper);
    }

    @Override
    public Integer create(String categoryName, String description) throws CBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, categoryName);
                ps.setString(2, description);

                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("category_id");

        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer categoryId, Category category) throws CBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE,
                    new Object[] { category.getCategoryName(), category.getDescription(), categoryId });
        } catch (Exception e) {
            throw new CBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer categoryId) throws CResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[] { categoryId });

        if (count == 0) {
            throw new CResourceNotFoundException("Category not found");
        }
    }

    private RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
        return new Category(
                rs.getInt("category_id"),
                rs.getString("category_name"),
                rs.getString("description"));
    });

}
