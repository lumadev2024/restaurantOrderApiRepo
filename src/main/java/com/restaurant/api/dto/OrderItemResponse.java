package com.restaurant.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Order item information")
public class OrderItemResponse {

    @Schema(description = "Item ID", example = "1")
    private Long id;

    @Schema(description = "Name of the menu item", example = "Margherita Pizza")
    private String name;

    @Schema(description = "Quantity ordered", example = "2")
    private Integer quantity;

    @Schema(description = "Unit price", example = "12.50")
    private BigDecimal price;

    @Schema(description = "Subtotal (quantity × price)", example = "25.00")
    private BigDecimal subtotal;
}
