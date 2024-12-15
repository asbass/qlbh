package com.buoi2.ltw.service;

import com.buoi2.ltw.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, MultipartFile image) throws Exception;
    ProductDTO updateProduct(Integer id, ProductDTO productDTO, MultipartFile image) throws Exception;
    void deleteProduct(Integer id);
    Optional<ProductDTO> getProductById(Integer id);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> searchProductsByName(String keyword);
}
