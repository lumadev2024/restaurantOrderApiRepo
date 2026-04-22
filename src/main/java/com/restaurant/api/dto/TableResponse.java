package com.restaurant.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Table information")
public class TableResponse {

    @Schema(description = "Table ID", example = "1")
    private Long id;

    @Schema(description = "Table number", example = "5")
    private Integer number;

    @Schema(description = "Number of seats", example = "4")
    private Integer seats;
}
