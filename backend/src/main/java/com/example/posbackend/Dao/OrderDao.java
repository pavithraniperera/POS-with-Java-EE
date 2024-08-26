package com.example.posbackend.Dao;

import com.example.posbackend.Entity.OrderEntity;
import com.example.posbackend.Entity.Order_itemEntity;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    boolean saveOrder(OrderEntity orderEntity) throws SQLException, NamingException;

    boolean saveOrderItem(Order_itemEntity orderItem) throws SQLException,NamingException;

    List<OrderEntity> getAll() throws SQLException,NamingException;

    String getLastOrderId() throws SQLException,NamingException;

    List<Order_itemEntity> getOrderItemById(String orderId) throws SQLException,NamingException;

    OrderEntity getOrderById(String orderId) throws SQLException,NamingException;

    List<OrderEntity> getOrderByCustomer(String customerId) throws SQLException,NamingException;

    boolean deleteOrderItemsByOrderId(String orderId) throws  SQLException,NamingException;

    boolean deleteOrderByOrderId(String orderId) throws  SQLException,NamingException;
}
