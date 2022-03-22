package com.asebas.api.services.Category;

import java.util.List;

import com.asebas.api.domain.Category;
import com.asebas.api.exceptions.CBadRequestException;
import com.asebas.api.exceptions.CResourceNotFoundException;
import com.asebas.api.repositories.Category.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> fetchAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category fetchCategoryById(Integer categoryId) throws CResourceNotFoundException {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category addCategory(String categoryName, String description) throws CBadRequestException {
        Integer categoryId = categoryRepository.create(categoryName, description);
        return categoryRepository.findById(categoryId);
    }

    @Override
    public void updateCategory(Integer categoryId, Category category) throws CBadRequestException {
        categoryRepository.update(categoryId, category);
    }

    @Override
    public void deleteCategory(Integer categoryId) throws CResourceNotFoundException {
        categoryRepository.removeById(categoryId);
    }

}
