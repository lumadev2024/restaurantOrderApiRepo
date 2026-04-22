package com.restaurant.api.controller;

import com.restaurant.api.dto.OrderRequest;
import com.restaurant.api.dto.OrderResponse;
import com.restaurant.api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order lifecycle management")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(
            summary = "Create a new order",
            description = "Opens a new order for the specified table. The table must exist.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request or table not found")
            }
    )
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get order by ID",
            description = "Returns a full order including all items and the calculated total",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order found"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    public ResponseEntity<OrderResponse> getOrderById(
            @Parameter(description = "Order ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/{id}/close")
    @Operation(
            summary = "Close an order",
            description = "Marks the order as CLOSED. The order must have at least one item and must not already be closed.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order closed successfully"),
                    @ApiResponse(responseCode = "400", description = "Order is empty or already closed"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    public ResponseEntity<OrderResponse> closeOrder(
            @Parameter(description = "Order ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(orderService.closeOrder(id));
    }
}
