package com.example.posbackend.Bo.impl;

import com.example.posbackend.Bo.OrderBo;
import com.example.posbackend.DB.DbConnection;
import com.example.posbackend.Dao.ItemDao;
import com.example.posbackend.Dao.OrderDao;
import com.example.posbackend.Dao.impl.ItemDaoImpl;
import com.example.posbackend.Dao.impl.OrderDaoImpl;
import com.example.posbackend.Dto.ItemDto;
import com.example.posbackend.Dto.OrderDto;
import com.example.posbackend.Entity.OrderEntity;
import com.example.posbackend.Entity.Order_itemEntity;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderBoImpl implements OrderBo {
    private ItemDao itemDao = new ItemDaoImpl();
    private OrderDao orderDao = new OrderDaoImpl();

    @Override
    public boolean save(OrderDto orderDto) throws SQLException, NamingException {
        System.out.println(orderDto);
        Connection connection = null;
        try {
            // Start a transaction
            connection = DbConnection.getInstance().getConnection();;
            connection.setAutoCommit(false);

            // Convert DTO to Entity
            OrderEntity orderEntity = new OrderEntity(
                    orderDto.getOrderId(),
                    orderDto.getCustomerId(),
                    orderDto.getTotal(),
                    orderDto.getDate()
            );

            // Save order
            if (!orderDao.saveOrder(orderEntity)) {
                System.out.println("Failed to save order.");
                throw new SQLException("Failed to save order.");

            }

            // Save each order item and update item quantity
            for (ItemDto itemDto : orderDto.getItemDtoList()) {

                Order_itemEntity orderItem = new Order_itemEntity(
                        orderDto.getOrderId(),
                        itemDto.getId(),
                        itemDto.getQuantity()
                );

                if (!orderDao.saveOrderItem(orderItem)) {
                    System.out.println("Failed to save order item.");
                    throw new SQLException("Failed to save order item.");
                }

                if (!itemDao.updateItemQuantity(itemDto.getId(), itemDto.getQuantity())) {
                    System.out.println("Failed to update item.");
                    throw new SQLException("Failed to update item quantity.");
                }
            }

            // Commit the transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction on failure
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
