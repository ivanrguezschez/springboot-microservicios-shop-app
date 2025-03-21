package com.irs.ordersservice.events;

import com.irs.ordersservice.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
