package com.buoi2.ltw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO implements Serializable {
    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    private String numberPhone;

    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "Order details are required")
    @NotEmpty(message = "Order details cannot be empty")
    private List<OrderDetailDTO> orderDetails;

    @Data
    public static class OrderDetailDTO {

        @NotNull(message = "Product ID is required")
        private Integer productId;

        @NotNull(message = "Quantity is required")
        private Integer quantity;
    }
}
