package com.irs.ordersservice.model.dtos;

import lombok.*;

import java.util.List;

public record OrderResponse(Long id, String orderNumber, List<OrderItemsResponse> orderItems) {


}
