package com.buoi2.ltw.service;

import com.buoi2.ltw.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO);
    boolean deleteCategory(String id);
    CategoryDTO getCategoryById(String id);
    List<CategoryDTO> searchCategories(String keyword);
}
