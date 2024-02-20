package com.example.toss.dto;

import com.example.toss.entity.Item;
import com.example.toss.entity.ItemOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemOrderDto {
    private Long id;
    private String item;
    private String tossPaymentKey;
    private String tossOrderId;
    private String status;

    public static ItemOrderDto fromEntity(ItemOrder entity) {
        return ItemOrderDto.builder()
                .id(entity.getId())
                .tossOrderId(entity.getTossOrderId())
                .tossPaymentKey(entity.getTossPaymentKey())
                .status(entity.getStatus())
                .item(entity.getItem().getName())
                .build();
    }

}
