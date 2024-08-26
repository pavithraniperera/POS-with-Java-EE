package com.example.posbackend.Dao.impl;

import com.example.posbackend.Dao.ItemDao;
import com.example.posbackend.Dao.SQLUtil;
import com.example.posbackend.Entity.ItemEntity;

import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(ItemEntity itemEntity) throws SQLException, NamingException {
        String query = "INSERT INTO items ( name, price, quantity,category, description, imgSrc) VALUES ( ?, ?, ?, ?, ?,?)";
        return SQLUtil.execute(query,
                itemEntity.getName(),
                itemEntity.getPrice(),
                itemEntity.getQuantity(),
                itemEntity.getCategory(),
                itemEntity.getDescription(),
                itemEntity.getImgSrc());
    }

    @Override
    public List<ItemEntity> getAll() throws SQLException, NamingException {

        String sql = "SELECT * FROM items";
        ResultSet resultSet = SQLUtil.execute(sql);

        List<ItemEntity> entities = new ArrayList<>();
        while (resultSet.next()){
            ItemEntity entity = new ItemEntity(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );

            entities.add(entity);

        }

        return entities;
    }

    @Override
    public boolean update(ItemEntity item) throws SQLException, NamingException {
        String sql = "UPDATE items SET name=?,price=?, quantity=?, category=?, description=?,imgSrc=? WHERE id=?";
        return SQLUtil.execute(sql,item.getName(),item.getPrice(),item.getQuantity(),item.getCategory(),item.getDescription(),item.getImgSrc(),item.getId());
    }

    @Override
    public boolean delete(int itemId) throws SQLException, NamingException {
        String sql = "DELETE FROM items WHERE id = ?";
        return SQLUtil.execute(sql, itemId);
    }

    @Override
    public ItemEntity getByName(String itemName) throws SQLException, NamingException {
        String sql = "SELECT * FROM items WHERE name=?";
        ResultSet resultSet = SQLUtil.execute(sql,itemName);
        ItemEntity entity = null;
        while (resultSet.next()){
            entity=new ItemEntity(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
        }

        return entity;
    }

    @Override
    public boolean updateItemQuantity(int id, int quantity) throws SQLException, NamingException {
        String sql = "UPDATE items SET quantity = quantity - ? WHERE id = ?";
        return SQLUtil.execute(sql, quantity, id);
    }

    @Override
    public ItemEntity getItemById(int itemId) throws SQLException, NamingException {
        String sql = "SELECT * FROM items WHERE id=?";
        ResultSet resultSet = SQLUtil.execute(sql,itemId);
        ItemEntity entity = null;
        while (resultSet.next()){
            entity=new ItemEntity(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
        }

        return entity;
    }
}
