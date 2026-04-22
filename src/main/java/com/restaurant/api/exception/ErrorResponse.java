package com.restaurant.api.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard error response body")
public class ErrorResponse {

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Short error label", example = "Not Found")
    private String error;

    @Schema(description = "Human-readable error message", example = "Order not found with id: 5")
    private String message;

    @Schema(description = "Timestamp of the error")
    private LocalDateTime timestamp;
}
