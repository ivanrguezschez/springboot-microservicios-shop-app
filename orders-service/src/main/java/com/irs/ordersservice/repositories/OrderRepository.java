package com.irs.ordersservice.repositories;

import com.irs.ordersservice.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
