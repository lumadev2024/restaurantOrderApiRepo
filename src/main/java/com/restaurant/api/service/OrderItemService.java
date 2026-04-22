package com.restaurant.api.service;

import com.restaurant.api.dto.OrderItemRequest;
import com.restaurant.api.dto.OrderItemResponse;
import com.restaurant.api.entity.Order;
import com.restaurant.api.entity.OrderItem;
import com.restaurant.api.entity.OrderStatus;
import com.restaurant.api.exception.BusinessException;
import com.restaurant.api.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;

    public OrderItemResponse addItem(Long orderId, OrderItemRequest request) {
        Order order = orderService.findById(orderId);
        if (order.getStatus() == OrderStatus.CLOSED) {
            throw new BusinessException("Cannot add items to a closed order");
        }
        OrderItem item = OrderItem.builder()
                .order(order)
                .name(request.getName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();
        return toResponse(orderItemRepository.save(item));
    }

    @Transactional(readOnly = true)
    public List<OrderItemResponse> getItemsByOrderId(Long orderId) {
        orderService.findById(orderId);
        return orderItemRepository.findByOrderId(orderId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private OrderItemResponse toResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .subtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .build();
    }
}
