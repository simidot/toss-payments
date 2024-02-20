package com.example.toss.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfirmDto {
    private String paymentKey;
    private String orderId;
    private Integer amount;
}
