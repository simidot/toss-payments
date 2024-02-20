package com.example.toss.service;

import com.example.toss.dto.ItemOrderDto;
import com.example.toss.dto.PaymentConfirmDto;
import com.example.toss.entity.Item;
import com.example.toss.entity.ItemOrder;
import com.example.toss.repo.ItemOrderRepository;
import com.example.toss.repo.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final TossHttpInterface tossService;
    private final ItemRepository itemRepository;
    private final ItemOrderRepository itemOrderRepository;


    // 결제 승인 요청 보내기
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

    // 주문정보 전체조회
    public List<ItemOrderDto> readAllOrders() {
        List<ItemOrder> orders = itemOrderRepository.findAll();
        List<ItemOrderDto> dtoList = new ArrayList<>();
        for (ItemOrder order : orders) {
            dtoList.add(ItemOrderDto.fromEntity(order));
        }
        return dtoList;
    }

    // 주문정보 단일조회 > tossOrderId로 조회하기
    public ItemOrderDto readByTossOrderId(String tossOrderId) {
        ItemOrder itemOrder = itemOrderRepository.findByTossOrderId(tossOrderId);
        return ItemOrderDto.fromEntity(itemOrder);
    }



}
