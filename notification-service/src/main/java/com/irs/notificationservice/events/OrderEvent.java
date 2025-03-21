package com.irs.notificationservice.events;

import com.irs.notificationservice.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
