package com.example.toss;

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

    @PostMapping("/confirm-payment")
    public Object confirmPayment(
            @RequestBody PaymentConfirmDto dto
    ) {
        log.info("received : {}", dto.toString());
        return orderService.confirmPayment(dto);
    }

    @GetMapping("/payments/{paymentKey}")
    public Object paymentsByPaymentKey(
            @PathVariable("paymentKey") String paymentKey) {
        log.info("received : {}", paymentKey);
        return orderService.readPaymentByPaymentKey(paymentKey);
    }
}
