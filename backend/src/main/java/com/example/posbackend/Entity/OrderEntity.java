package com.example.posbackend.Entity;

import com.example.posbackend.Dto.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEntity {
    private String orderId;
    private String customerId;
    private double total;
    private Date date;

}
