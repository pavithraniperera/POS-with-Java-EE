package com.example.posbackend.Dao.impl;

import com.example.posbackend.Dao.OrderDao;
import com.example.posbackend.Dao.SQLUtil;
import com.example.posbackend.Entity.OrderEntity;
import com.example.posbackend.Entity.Order_itemEntity;

import javax.naming.NamingException;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean saveOrder(OrderEntity orderEntity) throws SQLException, NamingException {
        String sql = "INSERT INTO orders (orderId, customerId, total, date) VALUES (?, ?, ?, ?)";
        return SQLUtil.execute(sql, orderEntity.getOrderId(), orderEntity.getCustomerId(), orderEntity.getTotal(), orderEntity.getDate());
    }

    @Override
    public boolean saveOrderItem(Order_itemEntity orderItem) throws SQLException, NamingException {
        String sql = "INSERT INTO order_items (orderId, itemId, quantity) VALUES (?, ?, ?)";
        return SQLUtil.execute(sql, orderItem.getOrderId(), orderItem.getItemId(), orderItem.getQuantity());
    }
}
