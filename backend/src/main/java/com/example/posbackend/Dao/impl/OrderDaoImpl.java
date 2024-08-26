package com.example.posbackend.Dao.impl;

import com.example.posbackend.Dao.OrderDao;
import com.example.posbackend.Dao.SQLUtil;
import com.example.posbackend.Entity.OrderEntity;
import com.example.posbackend.Entity.Order_itemEntity;

import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<OrderEntity> getAll() throws SQLException, NamingException {
        String sql = "SELECT * FROM orders";
        ResultSet resultSet = SQLUtil.execute(sql);
        List<OrderEntity> orderList = new ArrayList<>();

        while (resultSet.next()) {
            OrderEntity order = new OrderEntity();
            order.setOrderId(resultSet.getString(1));
            order.setCustomerId(resultSet.getString(2));
            order.setTotal(resultSet.getDouble(3));
            order.setDate(resultSet.getDate(4));
            orderList.add(order);
        }
        System.out.println(orderList);
        return orderList;
    }

    @Override
    public String getLastOrderId() throws SQLException, NamingException {
        String sql = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        ResultSet resultSet = SQLUtil.execute(sql);
        String id =null;
        while (resultSet.next()){
            id=resultSet.getString(1);
        }
        System.out.println(id);
        return id;

    }

    @Override
    public List<Order_itemEntity> getOrderItemById(String orderId) throws SQLException, NamingException {
        String sql = "SELECT * FROM order_items WHERE orderId=?";
        ResultSet resultSet = SQLUtil.execute(sql,orderId);
        List<Order_itemEntity> entities = new ArrayList<>();
        Order_itemEntity orderItem = null;
        while (resultSet.next()){
           orderItem = new Order_itemEntity(resultSet.getString(1),resultSet.getInt(2), resultSet.getInt(3) ) ;
           entities.add(orderItem);
        }
        return entities;
    }

    @Override
    public OrderEntity getOrderById(String orderId) throws SQLException, NamingException {
        String sql = "SELECT * FROM orders WHERE orderId=?";
        ResultSet resultSet = SQLUtil.execute(sql,orderId);
        OrderEntity orderEntity =null;
        while (resultSet.next()){
            orderEntity = new OrderEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3),resultSet.getDate(4) );
        }
        return orderEntity;
    }

    @Override
    public List<OrderEntity> getOrderByCustomer(String customerId) throws SQLException, NamingException {
        String sql = "SELECT * FROM orders WHERE customerId=?";
        ResultSet resultSet = SQLUtil.execute(sql,customerId);
        List<OrderEntity> entities = new ArrayList<>();
        while (resultSet.next()){
            entities.add(new OrderEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3),resultSet.getDate(4) ));
        }
        return entities;
    }

    @Override
    public boolean deleteOrderItemsByOrderId(String orderId) throws SQLException, NamingException {
        String sql = "DELETE FROM order_items WHERE orderId = ?";
        return SQLUtil.execute(sql,orderId);
    }

    @Override
    public boolean deleteOrderByOrderId(String orderId) throws SQLException, NamingException {
        String sql = "DELETE FROM orders WHERE orderId = ?";
        return  SQLUtil.execute(sql,orderId);
    }

}
