package com.example.toss.service;

import com.example.toss.dto.PaymentConfirmDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.LinkedHashMap;

@HttpExchange("/payments")
public interface TossHttpInterface {

    @PostExchange("/confirm")
    LinkedHashMap<String, Object> confirmPayment(
            @RequestBody PaymentConfirmDto dto
    );
}
