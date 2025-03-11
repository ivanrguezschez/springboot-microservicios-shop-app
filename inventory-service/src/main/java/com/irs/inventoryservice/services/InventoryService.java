package com.irs.inventoryservice.services;

import com.irs.inventoryservice.model.dtos.BaseResponse;
import com.irs.inventoryservice.model.dtos.OrderItemRequest;
import com.irs.inventoryservice.model.entities.Inventory;
import com.irs.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;


    public boolean isInStock(String sku) {
        Optional<Inventory> inventoryOptional = this.inventoryRepository.findBySku(sku);

        return inventoryOptional.filter(value -> value.getQuantity() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {
        List<String> errorList = new ArrayList<>();

        List<String> skus = orderItems.stream().map(OrderItemRequest::getSku).toList();

        List<Inventory> inventoryList = this.inventoryRepository.findBySkuIn(skus);

        orderItems.forEach(orderItem -> {
            Optional<Inventory> inventoryOptional = inventoryList.stream().filter(value -> value.getSku().equals(orderItem.getSku())).findFirst();
            if (inventoryOptional.isEmpty()) {
                errorList.add("Product with sku " + orderItem.getSku() + " does nos exist");
            } else if (inventoryOptional.get().getQuantity() < orderItem.getQuantity()) {
                errorList.add("Product with sku " + orderItem.getSku() + " has insufficient quantity");
            }
        });

        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }
}
