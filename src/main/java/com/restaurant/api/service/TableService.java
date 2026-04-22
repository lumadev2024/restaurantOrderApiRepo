package com.restaurant.api.service;

import com.restaurant.api.dto.TableRequest;
import com.restaurant.api.dto.TableResponse;
import com.restaurant.api.entity.RestaurantTable;
import com.restaurant.api.exception.ResourceNotFoundException;
import com.restaurant.api.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TableService {

    private final TableRepository tableRepository;

    @Transactional(readOnly = true)
    public List<TableResponse> getAllTables() {
        return tableRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TableResponse createTable(TableRequest request) {
        RestaurantTable table = RestaurantTable.builder()
                .number(request.getNumber())
                .seats(request.getSeats())
                .build();
        return toResponse(tableRepository.save(table));
    }

    @Transactional(readOnly = true)
    public RestaurantTable findById(Long id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));
    }

    private TableResponse toResponse(RestaurantTable table) {
        return TableResponse.builder()
                .id(table.getId())
                .number(table.getNumber())
                .seats(table.getSeats())
                .build();
    }
}
