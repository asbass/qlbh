package com.buoi2.ltw.service;

import com.buoi2.ltw.dto.ProductDTO;
import com.buoi2.ltw.entity.Category;
import com.buoi2.ltw.entity.Product;
import com.buoi2.ltw.dao.ProductDAO;
import com.buoi2.ltw.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDao;

    @Autowired
    private CategoryDAO categoryDao;

    private static final String IMAGE_DIRECTORY = "C:/uploadedimages/";

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String imageName = saveImage(image); // Lưu hình ảnh và lấy tên tệp
            productDTO.setImage(imageName);      // Cập nhật tên hình ảnh vào DTO
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuality(productDTO.getQuality());
        product.setImage(productDTO.getImage());

        // Set category if available
        if (productDTO.getCategoryId() != null) {
            product.setCategory(categoryDao.findById(productDTO.getCategoryId()).orElse(null));
        }

        product = productDao.save(product);
        productDTO.setId(product.getId()); // Set the id from the saved product

        return productDTO;
    }

    @Override
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO, MultipartFile image) throws IOException {
        Optional<Product> existingProductOpt = productDao.findById(id);
        if (!existingProductOpt.isPresent()) {
            return null; // Return null if product not found
        }

        Product existingProduct = existingProductOpt.get();
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuality(productDTO.getQuality());

        if (image != null && !image.isEmpty()) {
            String imageName = saveImage(image); // Lưu hình ảnh và lấy tên tệp
            existingProduct.setImage(imageName); // Cập nhật tên hình ảnh vào sản phẩm
        }

        if (productDTO.getCategoryId() != null) {
            Category category = categoryDao.findById(productDTO.getCategoryId()).orElse(null);
            if (category == null) {
                throw new IllegalArgumentException("Category not found");
            }
            existingProduct.setCategory(category);
        }
        productDao.save(existingProduct); // Save updated product

        productDTO.setId(existingProduct.getId());
        return productDTO;
    }

    @Override
    public void deleteProduct(Integer id) {
        productDao.deleteById(id);
    }

    @Override
    public Optional<ProductDTO> getProductById(Integer id) {
        Optional<Product> productOpt = productDao.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setPrice(product.getPrice());
            productDTO.setQuality(product.getQuality());
            productDTO.setImage(product.getImage());
            productDTO.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
            return Optional.of(productDTO);
        }
        return Optional.empty();
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productDao.findAll();
        return products.stream().map(this::mapToProductDTO).collect(Collectors.toList());
    }
    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImage());
        dto.setAvailable(product.getAvailable());
        dto.setDescription(product.getDescription());
        dto.setQuality(product.getQuality());
        dto.setCreateDate(product.getCreateDate());
        dto.setCategoryId(product.getCategory().getId().toString());
        dto.setCategoryName(product.getCategory().getName()); // Lấy tên danh mục
        return dto;
    }
    @Override
    public List<ProductDTO> searchProductsByName(String keyword) {
        List<Product> products = productDao.findAllByNameLike("%" + keyword + "%");
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private String saveImage(MultipartFile image) throws IOException {
        String filename = image.getOriginalFilename();
        Path path = Paths.get(IMAGE_DIRECTORY + filename);

        // Kiểm tra xem tệp đã tồn tại chưa
        if (Files.exists(path)) {
            // Nếu tệp đã tồn tại, trả về tên tệp hiện tại mà không tạo lại
            return filename;
        }

        // Nếu tệp chưa tồn tại, sao chép tệp vào thư mục
        Files.copy(image.getInputStream(), path);
        return filename;
    }


    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuality(product.getQuality());
        productDTO.setImage(product.getImage());
        productDTO.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        return productDTO;
    }
}
