package com.restaurant.api.dto;

import com.restaurant.api.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Order information with items and total")
public class OrderResponse {

    @Schema(description = "Order ID", example = "1")
    private Long id;

    @Schema(description = "Associated table ID", example = "1")
    private Long tableId;

    @Schema(description = "Associated table number", example = "5")
    private Integer tableNumber;

    @Schema(description = "Order status", example = "OPEN")
    private OrderStatus status;

    @Schema(description = "Order creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Total price of all items", example = "45.50")
    private BigDecimal totalPrice;

    @Schema(description = "List of items in this order")
    private List<OrderItemResponse> items;
}
