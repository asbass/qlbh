package com.buoi2.ltw.service;

import com.buoi2.ltw.dto.ProductDTO;
import com.buoi2.ltw.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    String addProduct(ProductDTO productDTO, MultipartFile image);

    String updateProduct(ProductDTO productDTO, MultipartFile image);

    List<Product> searchProducts(String keyword);

    String deleteProduct(int id);

    List<Product> getAllProducts();

    Optional<Product> getProductById(int id);
}
