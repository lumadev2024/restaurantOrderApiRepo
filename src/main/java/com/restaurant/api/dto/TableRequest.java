package com.restaurant.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request body for creating a table")
public class TableRequest {

    @NotNull(message = "Table number is required")
    @Min(value = 1, message = "Table number must be at least 1")
    @Schema(description = "Unique table number", example = "5")
    private Integer number;

    @NotNull(message = "Seats count is required")
    @Min(value = 1, message = "Seats must be at least 1")
    @Schema(description = "Number of seats at the table", example = "4")
    private Integer seats;
}
