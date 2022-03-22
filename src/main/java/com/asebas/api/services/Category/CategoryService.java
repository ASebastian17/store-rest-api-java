package com.asebas.api.services.Category;

import java.util.List;

import com.asebas.api.domain.Category;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;

public interface CategoryService {

    List<Category> fetchAllCategories();

    Category fetchCategoryById(Integer categoryId) throws CResourceNotFoundException;

    Category addCategory(String categoryName, String description) throws CBadRequestException;

    void updateCategory(Integer categoryId, Category category) throws CBadRequestException;

    void deleteCategory(Integer categoryId) throws CResourceNotFoundException;

}
