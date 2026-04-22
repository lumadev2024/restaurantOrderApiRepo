package com.restaurant.api.controller;

import com.restaurant.api.dto.TableRequest;
import com.restaurant.api.dto.TableResponse;
import com.restaurant.api.service.TableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedMethods = {"GET", "POST", "PUT", "DELETE", "OPTIONS"})
@Tag(name = "Tables", description = "Restaurant table management")
public class TableController {

    private final TableService tableService;

    @GetMapping
    @Operation(
            summary = "Get all tables",
            description = "Returns a list of all restaurant tables",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tables retrieved successfully")
            }
    )
    public ResponseEntity<List<TableResponse>> getAllTables() {
        return ResponseEntity.ok(tableService.getAllTables());
    }

    @PostMapping
    @Operation(
            summary = "Create a new table",
            description = "Registers a new table in the restaurant",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Table created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    public ResponseEntity<TableResponse> createTable(@Valid @RequestBody TableRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tableService.createTable(request));
    }
}
