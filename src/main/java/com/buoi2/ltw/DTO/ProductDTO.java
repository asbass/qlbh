package com.buoi2.ltw.DTO;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class ProductDTO {

    private Integer id;

    @NotBlank(message = "Name cannot be blank") // Không được để trống
    @Size(max = 100, message = "Name must not exceed 100 characters") // Tối đa 100 ký tự
    private String name;

    @NotNull(message = "Price cannot be null") // Không được để trống
    @Positive(message = "Price must be greater than 0") // Giá phải lớn hơn 0
    private Double price;

    @NotBlank(message = "Image cannot be blank") // Không được để trống
    private String image;

    @Size(max = 500, message = "Description must not exceed 500 characters") // Tối đa 500 ký tự
    private String description;

    @NotNull(message = "Quality cannot be null") // Không được để trống
    @Min(value = 1, message = "Quality must be at least 1") // Giá trị tối thiểu là 1
    private Integer quality;

    @NotNull(message = "Availability status cannot be null") // Không được để trống
    private Boolean available;

    @NotNull(message = "Category ID cannot be null") // Không được để trống
    private Integer categoryId; // ID của danh mục

    public ProductDTO() {
    }

    public ProductDTO(Integer id, String name, Double price, String image, String description, Integer quality, Boolean available, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.quality = quality;
        this.available = available;
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
