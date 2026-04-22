package com.restaurant.api.controller;

import com.restaurant.api.dto.OrderItemRequest;
import com.restaurant.api.dto.OrderItemResponse;
import com.restaurant.api.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders/{orderId}/items")
@RequiredArgsConstructor
@Tag(name = "Order Items", description = "Manage items within an order")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    @Operation(
            summary = "Add an item to an order",
            description = "Adds a menu item to an existing OPEN order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Item added successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid item data or order is closed"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    public ResponseEntity<OrderItemResponse> addItem(
            @Parameter(description = "Order ID", example = "1") @PathVariable Long orderId,
            @Valid @RequestBody OrderItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.addItem(orderId, request));
    }

    @GetMapping
    @Operation(
            summary = "Get all items for an order",
            description = "Returns all items belonging to the specified order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Items retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    public ResponseEntity<List<OrderItemResponse>> getItems(
            @Parameter(description = "Order ID", example = "1") @PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemService.getItemsByOrderId(orderId));
    }
}
