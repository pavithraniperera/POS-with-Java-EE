package com.example.posbackend.Bo.impl;

import com.example.posbackend.Bo.OrderBo;
import com.example.posbackend.DB.DbConnection;
import com.example.posbackend.Dao.CustomerDao;
import com.example.posbackend.Dao.ItemDao;
import com.example.posbackend.Dao.OrderDao;
import com.example.posbackend.Dao.impl.CustomerDaoImpl;
import com.example.posbackend.Dao.impl.ItemDaoImpl;
import com.example.posbackend.Dao.impl.OrderDaoImpl;
import com.example.posbackend.Dto.CustomerDto;
import com.example.posbackend.Dto.ItemDto;
import com.example.posbackend.Dto.OrderDto;
import com.example.posbackend.Entity.CustomerEntity;
import com.example.posbackend.Entity.ItemEntity;
import com.example.posbackend.Entity.OrderEntity;
import com.example.posbackend.Entity.Order_itemEntity;
import com.example.posbackend.Tm.OrderTm;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    private ItemDao itemDao = new ItemDaoImpl();
    private OrderDao orderDao = new OrderDaoImpl();
  private CustomerDao customerDao = new CustomerDaoImpl();
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

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    public OrderDto findOrderById(String orderId) throws SQLException, NamingException {
        List<Order_itemEntity>  orderItem= orderDao.getOrderItemById(orderId);
        List<ItemDto> itemDtos = new ArrayList<>();
        OrderEntity orderEntity = orderDao.getOrderById(orderId);

        for (Order_itemEntity order : orderItem) {
            // Get the ItemEntity using the itemId
            ItemEntity itemEntity = itemDao.getItemById(order.getItemId());

            // Convert ItemEntity to ItemDto
            ItemDto itemDto = new ItemDto(
                    itemEntity.getId(),
                    itemEntity.getName(), itemEntity.getPrice(), itemEntity.getQuantity(), itemEntity.getCategory(), itemEntity.getDescription(), itemEntity.getImgSrc()
            );

            // Add the itemDto to the list
            itemDtos.add(itemDto);
        }
        return new OrderDto(orderId,orderEntity.getCustomerId(),itemDtos,orderEntity.getDate(),orderEntity.getTotal());
    }

    @Override
    public List<OrderTm> getAllOrders() throws SQLException, NamingException {
        List<OrderEntity> orders = orderDao.getAll();
        List<OrderTm> OrderTmList = new ArrayList<>();
        for(OrderEntity orderEntity: orders){
            CustomerEntity customer = customerDao.getById(orderEntity.getCustomerId());
            OrderTm orderTm = new OrderTm(orderEntity.getOrderId(), orderEntity.getCustomerId(), customer.getName(), orderEntity.getTotal(), orderEntity.getDate());
            OrderTmList.add(orderTm);
        }
        return OrderTmList;
    }

    @Override
    public String getLastOrderId() throws SQLException, NamingException {
        return orderDao.getLastOrderId();
    }

    @Override
    public List<OrderTm> getOrderByCustomer(String customerId) throws SQLException, NamingException {
        List<OrderEntity> entities = orderDao.getOrderByCustomer(customerId);
        List<OrderTm> tmList = new ArrayList<>();
        for (OrderEntity entity :entities){
            CustomerEntity customerEntity = customerDao.getById(entity.getCustomerId());
            tmList.add(new OrderTm(entity.getOrderId(),entity.getCustomerId(),customerEntity.getName(),entity.getTotal(),entity.getDate()));
        }
        return tmList;
    }

    @Override
    public boolean deleteOrder(String orderId) throws SQLException, NamingException {
        Connection connection = null;
        try {
            // Get a connection
            connection = DbConnection.getInstance().getConnection();

            // Start a transaction
            connection.setAutoCommit(false);

            // Step 1: Delete the order items associated with the orderId
            boolean orderItemsDeleted = orderDao.deleteOrderItemsByOrderId(orderId);
            if (!orderItemsDeleted) {
                connection.rollback();
                return false;
            }

            // Step 2: Delete the order itself
            boolean orderDeleted = orderDao.deleteOrderByOrderId(orderId);
            if (!orderDeleted) {
                connection.rollback();
                return false;
            }

            // If everything is successful, commit the transaction
            connection.commit();
            return true;

        } catch (SQLException | NamingException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
