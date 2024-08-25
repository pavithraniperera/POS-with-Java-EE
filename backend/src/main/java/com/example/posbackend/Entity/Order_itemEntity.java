package com.example.posbackend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order_itemEntity {
    private String OrderId;
    private int itemId;
    private int quantity;
}
