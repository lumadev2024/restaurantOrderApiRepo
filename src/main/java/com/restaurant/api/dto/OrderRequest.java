package com.restaurant.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request body for creating an order")
public class OrderRequest {

    @NotNull(message = "Table ID is required")
    @Schema(description = "ID of the table to associate with this order", example = "1")
    private Long tableId;
}
