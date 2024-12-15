package com.buoi2.ltw.dto;

import jakarta.validation.constraints.*;
import lombok.Data;



@Data
public class CategoryDTO {
    @NotBlank(message = "ID không được để trống")  // Kiểm tra id không được để trống
    private String id;

    @NotBlank(message = "Tên danh mục không được để trống")  // Kiểm tra name không được để trống
    @Size(min = 3, max = 50, message = "Tên danh mục phải có ít nhất 3 ký tự và tối đa 50 ký tự")
    private String name;
}
