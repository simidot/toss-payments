package com.example.toss;

import com.example.toss.dto.PaymentCancelDto;
import com.example.toss.dto.PaymentConfirmDto;
import com.example.toss.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/toss")
@Slf4j
@RequiredArgsConstructor
public class TossController {
    private final OrderService orderService;

    // 결제 승인 요청
    @PostMapping("/confirm-payment")
    public Object confirmPayment(
            @RequestBody PaymentConfirmDto dto
    ) {
        log.info("received : {}", dto.toString());
        return orderService.confirmPayment(dto);
    }

    // paymentKey로 결제 조회
    @GetMapping("/payments/{paymentKey}")
    public Object paymentsByPaymentKey(
            @PathVariable("paymentKey") String paymentKey) {
        log.info("received : {}", paymentKey);
        return orderService.readPaymentByPaymentKey(paymentKey);
    }

    // orderId로 결제 조회
    @GetMapping("/payments/orders/{orderId}")
    public Object paymentsByOrderId(
            @PathVariable("orderId") String orderId) {
        log.info("received : {}", orderId);
        return orderService.readPaymentByOrderId(orderId);
    }

    // 결제 취소
    @PostMapping("/payments/{paymentKey}/cancel")
    public Object cancelPayment(
            @PathVariable("paymentKey") String paymentKey,
            @RequestBody PaymentCancelDto dto
            ) {
        log.info("received: {}", paymentKey);
        return orderService.cancelPayment(dto, paymentKey);
    }
}
