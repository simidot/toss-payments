package com.example.toss;

import com.example.toss.dto.ItemsPaidDto;
import com.example.toss.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문정보 전체조회
    @GetMapping("orders")
    public List<ItemsPaidDto> readAllOrders() {
        return orderService.readAllOrders();
    }

    // 주문정보 단일조회 > tossOrderId로 조회하기
    @GetMapping("/orders/{tossOrderId}")
    public ItemsPaidDto readByTossOrderId(
            @PathVariable("tossOrderId") String tossOrderId
    ) {
        return orderService.readByTossOrderId(tossOrderId);
    }

}
