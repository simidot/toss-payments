package com.example.toss.dto;

import com.example.toss.entity.ItemOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemsPaidDto {
    private Long id;
    private ItemDto item;
    private String tossPaymentKey;
    private String tossOrderId;
    private String status;

    public static ItemsPaidDto fromEntity(ItemOrder entity) {
        return ItemsPaidDto.builder()
                .id(entity.getId())
                .tossOrderId(entity.getTossOrderId())
                .tossPaymentKey(entity.getTossPaymentKey())
                .status(entity.getStatus())
                .item(ItemDto.fromEntity(entity.getItem()))
                .build();
    }

}
