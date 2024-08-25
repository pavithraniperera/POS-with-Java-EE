package com.example.posbackend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerEntity {
    private String id;
    private String name;
    private String contact;
    private String address;
    private  String note;
}
