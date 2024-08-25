package com.example.posbackend.Dao;

import com.example.posbackend.Entity.ItemEntity;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface ItemDao {
    boolean save(ItemEntity itemEntity) throws SQLException, NamingException;

    List<ItemEntity> getAll() throws SQLException,NamingException;

    boolean update(ItemEntity item) throws SQLException,NamingException;

    boolean delete(int itemId) throws SQLException,NamingException;

    ItemEntity getByName(String itemName) throws SQLException,NamingException;

    boolean updateItemQuantity(int id, int quantity) throws SQLException,NamingException;
}
