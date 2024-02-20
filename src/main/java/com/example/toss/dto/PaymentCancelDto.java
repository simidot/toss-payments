package com.example.toss.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCancelDto {
    //일단 전체취소로 한다.
    private String cancelReason;
}
