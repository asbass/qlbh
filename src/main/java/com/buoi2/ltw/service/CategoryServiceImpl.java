package com.buoi2.ltw.service;

import com.buoi2.ltw.dto.CategoryDTO;
import com.buoi2.ltw.dao.CategoryDAO;
import com.buoi2.ltw.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryDAO.findAll(); // Lấy tất cả danh mục
        return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());

        category = categoryDAO.save(category);

        return convertToDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Optional<Category> existingCategoryOpt = categoryDAO.findById(categoryDTO.getId());
        if (existingCategoryOpt.isPresent()) {
            Category existingCategory = existingCategoryOpt.get();
            existingCategory.setName(categoryDTO.getName());

            existingCategory = categoryDAO.save(existingCategory);
            return convertToDTO(existingCategory);
        } else {
            return null; // Nếu không tìm thấy category
        }
    }

    @Override
    public boolean deleteCategory(String id) {
        Optional<Category> existingCategoryOpt = categoryDAO.findById(id);
        if (existingCategoryOpt.isPresent()) {
            categoryDAO.deleteById(id);
            return true;
        } else {
            return false; // Nếu không tìm thấy category
        }
    }


    @Override
    public CategoryDTO getCategoryById(String id) {
        Optional<Category> categoryOpt = categoryDAO.findById(id);
        return categoryOpt.map(this::convertToDTO).orElse(null);
    }

    @Override
    public List<CategoryDTO> searchCategories(String keyword) {
        List<Category> categories = categoryDAO.findAllByNameLike("%" + keyword + "%");
        return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Chuyển đổi từ Category Entity sang CategoryDTO
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }
}
