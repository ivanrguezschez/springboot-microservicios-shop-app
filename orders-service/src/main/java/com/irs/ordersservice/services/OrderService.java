package com.irs.ordersservice.services;

import com.irs.ordersservice.events.OrderEvent;
import com.irs.ordersservice.model.dtos.*;
import com.irs.ordersservice.model.entities.Order;
import com.irs.ordersservice.model.entities.OrderItems;
import com.irs.ordersservice.model.enums.OrderStatus;
import com.irs.ordersservice.repositories.OrderRepository;
import com.irs.ordersservice.utils.JsonUtils;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObservationRegistry observationRegistry;

    //public void placeOrder(OrderRequest orderRequest) {
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        Observation observationInventory = Observation.createNotStarted("inventory-service", observationRegistry);

        return observationInventory.observe(() -> {
            // Check for inventory (comprobamos que hay productos en stock)
            BaseResponse result = this.webClientBuilder.build()
                    .post()
                    //.uri("http://localhost:8083/api/inventory/in-stock")
                    //.uri("http://localhost:8080/api/inventory/in-stock") // Api gateway
                    .uri("lb://inventory-service/api/inventory/in-stock") // Eureka
                    .bodyValue(orderRequest.getOrderItems())
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
            if (result != null && !result.hasErrors()) {
                Order order = new Order();
                order.setOrderNumber(UUID.randomUUID().toString());
                order.setOrderItems(orderRequest.getOrderItems().stream()
                        .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                        .toList());

                //this.orderRepository.save(order);
                Order savedOrder = this.orderRepository.save(order);

                // Send message to order topic
                this.kafkaTemplate.send("orders-topic", JsonUtils.toJson(
                        new OrderEvent(savedOrder.getOrderNumber(), savedOrder.getOrderItems().size(), OrderStatus.PLACED)
                ));

                return mapToOrderResponse(savedOrder);
            } else {
                throw new IllegalArgumentException("Some of the products are not in stock");
            }
        });
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();

        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getId(),
                order.getOrderNumber(),
                order.getOrderItems().stream().map(this::mapToOrderItemsResponse).toList());
    }

    private OrderItemsResponse mapToOrderItemsResponse(OrderItems orderItems) {
        return new OrderItemsResponse(orderItems.getId(), orderItems.getSku(), orderItems.getPrice(), orderItems.getQuantity());
    }
}
