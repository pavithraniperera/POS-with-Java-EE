package com.example.posbackend.Dao;

import com.example.posbackend.Entity.OrderEntity;
import com.example.posbackend.Entity.Order_itemEntity;

import javax.naming.NamingException;
import java.sql.SQLException;

public interface OrderDao {
    boolean saveOrder(OrderEntity orderEntity) throws SQLException, NamingException;

    boolean saveOrderItem(Order_itemEntity orderItem) throws SQLException,NamingException;
}
