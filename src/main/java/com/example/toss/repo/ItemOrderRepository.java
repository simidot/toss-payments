package com.example.toss.repo;

import com.example.toss.entity.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {
    ItemOrder findByTossPaymentKey(String key);

}
