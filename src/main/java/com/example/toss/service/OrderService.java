package com.example.toss.service;

import com.example.toss.dto.ItemsPaidDto;
import com.example.toss.dto.PaymentCancelDto;
import com.example.toss.dto.PaymentConfirmDto;
import com.example.toss.entity.Item;
import com.example.toss.entity.ItemOrder;
import com.example.toss.repo.ItemOrderRepository;
import com.example.toss.repo.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ItemsPaidDto> readAllOrders() {
        List<ItemOrder> orders = itemOrderRepository.findAll();
        List<ItemsPaidDto> dtoList = new ArrayList<>();
        for (ItemOrder order : orders) {
            dtoList.add(ItemsPaidDto.fromEntity(order));
        }
        return dtoList;
    }

    // 주문정보 단일조회 > tossOrderId로 조회하기
    public ItemsPaidDto readByTossOrderId(String tossOrderId) {
        ItemOrder itemOrder = itemOrderRepository.findByTossOrderId(tossOrderId);
        return ItemsPaidDto.fromEntity(itemOrder);
    }

    // 결제정보 조회 > tossPaymentKey로 조회하기
    public Object readPaymentByPaymentKey(String paymentKey) {
        HashMap<String, Object> tossPaymentObj = tossService.readPaymentByPaymentKey(paymentKey);
        return tossPaymentObj;
    }

    // 결제정보 조회 > orderId (점주가 생성한)로 조회하기
    public Object readPaymentByOrderId(String orderId) {
        return tossService.readPaymentByOrderId(orderId);
    }

    // 결제 취소
    @Transactional
    public Object cancelPayment(PaymentCancelDto dto, String paymentKey) {
        HashMap<String, Object> cancelPaymentObj = tossService.cancelPayment(dto, paymentKey);
        log.info(cancelPaymentObj.toString());

        ItemOrder foundOrder = itemOrderRepository.findByTossPaymentKey(paymentKey);
        foundOrder.setStatus("canceled");
        itemOrderRepository.save(foundOrder);

        log.info("canceled order:: " + itemOrderRepository.findByTossPaymentKey(paymentKey));

        return cancelPaymentObj;
    }
}
