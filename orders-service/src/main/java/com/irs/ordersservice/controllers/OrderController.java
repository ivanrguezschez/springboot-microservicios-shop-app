package com.irs.ordersservice.controllers;

import com.irs.ordersservice.model.dtos.OrderRequest;
import com.irs.ordersservice.model.dtos.OrderResponse;
import com.irs.ordersservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        this.orderService.placeOrder(orderRequest);

        return "Order placed successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders() {
        return this.orderService.getAllOrders();
    }
}
