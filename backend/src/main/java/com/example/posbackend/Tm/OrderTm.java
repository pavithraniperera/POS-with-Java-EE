package com.example.posbackend.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderTm {
    private String orderId;
    private String customerId;
    private String customerName;
    private double total;
    private Date date;


}

