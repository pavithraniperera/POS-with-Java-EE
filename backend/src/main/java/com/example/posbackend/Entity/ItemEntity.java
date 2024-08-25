package com.example.posbackend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemEntity {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String description;
    private String imgSrc;
    public ItemEntity(String name,double price,int quantity,String category,String description,String imgSrc){
        this.name = name;
        this.price =price;
        this.quantity =quantity;
        this.category =category;
        this.description =description;
        this.imgSrc =imgSrc;
    }

    public ItemEntity(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
