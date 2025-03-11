package com.irs.ordersservice.model.dtos;

public record OrderItemsResponse(Long id, String sku, Double price, Long quantity) {
}
