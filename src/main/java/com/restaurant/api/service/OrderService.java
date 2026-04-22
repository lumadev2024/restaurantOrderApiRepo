package com.restaurant.api.service;

import com.restaurant.api.dto.OrderItemResponse;
import com.restaurant.api.dto.OrderRequest;
import com.restaurant.api.dto.OrderResponse;
import com.restaurant.api.entity.Order;
import com.restaurant.api.entity.OrderItem;
import com.restaurant.api.entity.OrderStatus;
import com.restaurant.api.entity.RestaurantTable;
import com.restaurant.api.exception.BusinessException;
import com.restaurant.api.exception.ResourceNotFoundException;
import com.restaurant.api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final TableService tableService;

    public OrderResponse createOrder(OrderRequest request) {
        RestaurantTable table = tableService.findById(request.getTableId());
        Order order = Order.builder()
                .table(table)
                .status(OrderStatus.OPEN)
                .build();
        order = orderRepository.save(order);
        log.info("Created order {} for table number {}", order.getId(), table.getNumber());
        return toResponse(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        return toResponse(findById(id));
    }

    public OrderResponse closeOrder(Long id) {
        Order order = findById(id);
        if (order.getStatus() == OrderStatus.CLOSED) {
            throw new BusinessException("Order is already closed");
        }
        if (order.getItems().isEmpty()) {
            throw new BusinessException("Cannot close an empty order");
        }
        order.setStatus(OrderStatus.CLOSED);
        log.info("Closed order {} with {} item(s)", id, order.getItems().size());
        return toResponse(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());

        BigDecimal total = itemResponses.stream()
                .map(OrderItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return OrderResponse.builder()
                .id(order.getId())
                .tableId(order.getTable().getId())
                .tableNumber(order.getTable().getNumber())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .totalPrice(total)
                .items(itemResponses)
                .build();
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .subtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .build();
    }
}
