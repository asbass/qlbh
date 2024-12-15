package com.buoi2.ltw.service;

import com.buoi2.ltw.DTO.ProductDTO;
import com.buoi2.ltw.dao.ProductDAO;
import com.buoi2.ltw.entity.Category;
import com.buoi2.ltw.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDao;

    // Get all products as a list of ProductDTO
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productDao.findAll(); // Get the list of Product entities
        return products.stream()                  // Convert each Product entity to ProductDTO
                .map(this::convertToDTO)         // Mapping function
                .collect(Collectors.toList());   // Collect the result into a List<ProductDTO>
    }

    // Get product by id as ProductDTO
    public Optional<ProductDTO> getProductById(int id) {
        return productDao.findById(id).map(this::convertToDTO);
    }

    // Add product
    public void addProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        productDao.save(product);
    }

    // Update product
    public void updateProduct(int id, ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        product.setId(id);
        productDao.save(product);
    }

    // Delete product
    public void deleteProduct(int id) {
        productDao.deleteById(id);
    }

    // Search products by name
    public List<ProductDTO> searchProductsByName(String keyword) {
        return productDao.findAllByNameLike("%" + keyword + "%").stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Save image to directory and return the filename
    public String saveImage(MultipartFile image) {
        try {
            String filename = image.getOriginalFilename();
            Path path = Paths.get("C:/uploadedimages/" + filename);
            Files.copy(image.getInputStream(), path);
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Error while saving image", e);
        }
    }

    // Convert ProductDTO to Product entity
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuality(productDTO.getQuality());
        product.setImage(productDTO.getImage());
        product.setCategory(productDTO.getCategoryId());
        return product;
    }

    // Convert Product entity to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuality(product.getQuality());
        productDTO.setImage(product.getImage());

        // If the category is an ID in the Product entity, fetch the Category object using that ID
        if (product.getCategory() != null) {
            Category category = product.getCategory();  // Assuming category is already a Category object
            productDTO.setCategoryId(category);           // Set the Category object in DTO
        } else {
            // Optionally handle the case when category is null or ID is missing
            productDTO.setCategoryId(null);
        }

        return productDTO;
    }


}
