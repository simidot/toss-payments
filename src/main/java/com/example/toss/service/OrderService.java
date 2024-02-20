package com.example.toss.service;

import com.example.toss.dto.PaymentConfirmDto;
import com.example.toss.entity.Item;
import com.example.toss.entity.ItemOrder;
import com.example.toss.repo.ItemOrderRepository;
import com.example.toss.repo.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final TossHttpInterface tossService;
    private final ItemRepository itemRepository;
    private final ItemOrderRepository itemOrderRepository;

    public Object confirmPayment(PaymentConfirmDto dto) {
        // HTTP 요청이 보내진다.
        HashMap<String, Object> tossPaymentObj = tossService.confirmPayment(dto);
        log.info(tossPaymentObj.toString());

        String itemId = tossPaymentObj.get("orderName").toString().split("-")[0];
        Optional<Item> item = itemRepository.findById(Long.parseLong(itemId));
        if (item != null) {
            ItemOrder newOrder = ItemOrder.builder()
                    .item(item.get())
                    .tossPaymentKey(dto.getPaymentKey())
                    .tossOrderId(dto.getOrderId())
                    .status("paid")
                    .build();
            itemOrderRepository.save(newOrder);

            log.info("new order:: " + itemOrderRepository.findByTossPaymentKey(dto.getPaymentKey()).toString());
        }
        return tossPaymentObj;
    }
}
