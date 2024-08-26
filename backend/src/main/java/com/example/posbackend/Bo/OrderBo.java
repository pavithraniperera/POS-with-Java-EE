package com.example.posbackend.Bo;

import com.example.posbackend.Dto.OrderDto;
import com.example.posbackend.Tm.OrderTm;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface OrderBo {
    boolean save(OrderDto orderDto) throws SQLException, NamingException;

    OrderDto findOrderById(String orderId) throws SQLException,NamingException;

    List<OrderTm> getAllOrders() throws SQLException,NamingException;

    String getLastOrderId() throws SQLException,NamingException;

    List<OrderTm> getOrderByCustomer(String customerId)throws SQLException,NamingException ;

    boolean deleteOrder(String orderId) throws SQLException,NamingException;
}
