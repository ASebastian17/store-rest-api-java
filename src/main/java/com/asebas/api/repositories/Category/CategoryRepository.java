package com.asebas.api.repositories.Category;

import java.util.List;

import com.asebas.api.domain.Category;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface CategoryRepository {

    Category findById(Integer categoryId) throws CResourceNotFoundException;

    List<Category> findAll();

    Integer create(String categoryName, String description) throws CBadRequestException;

    void update(Integer categoryId, Category category) throws CBadRequestException;

    void removeById(Integer categoryId) throws CResourceNotFoundException;

}
