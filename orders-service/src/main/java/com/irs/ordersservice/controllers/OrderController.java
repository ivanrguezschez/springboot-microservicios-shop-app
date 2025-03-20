package com.irs.ordersservice.controllers;

import com.irs.ordersservice.model.dtos.OrderRequest;
import com.irs.ordersservice.model.dtos.OrderResponse;
import com.irs.ordersservice.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /*
        {
            "orderItems": [
                {
                    "sku": "00001",
                    "price": 2345,
                    "quantity": 2
                }
            ]
        }
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "orders-service", fallbackMethod = "placeOrderFallback")
    //public String placeOrder(@RequestBody OrderRequest orderRequest) {
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        //this.orderService.placeOrder(orderRequest);
        //return "Order placed successfully";
        OrderResponse orderResponse = this.orderService.placeOrder(orderRequest);

        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders() {
        return this.orderService.getAllOrders();
    }

    private ResponseEntity<OrderResponse> placeOrderFallback(OrderRequest orderRequest, Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
}
