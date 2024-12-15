package com.buoi2.ltw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusOrderDTO {
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "success|failed", message = "Status must be one of: success, or failed")
    private String status;
}
