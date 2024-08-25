package com.example.posbackend.Dao.impl;

import com.example.posbackend.Dao.CustomerDao;
import com.example.posbackend.Dao.SQLUtil;
import com.example.posbackend.Entity.CustomerEntity;

import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public boolean save(CustomerEntity customer) throws SQLException, NamingException {
        String sql = "INSERT INTO customers (id, name, contact, address, note) VALUES (?, ?, ?, ?, ?)";
        return SQLUtil.execute(sql, customer.getId(), customer.getName(),customer.getContact(),customer.getAddress(),customer.getNote());
    }

    @Override
    public List<CustomerEntity> getAll() throws SQLException, NamingException {
        String sql = "SELECT * FROM customers";
        ResultSet resultSet = SQLUtil.execute(sql);
        List<CustomerEntity> entities = new ArrayList<>();
        while (resultSet.next()){
            CustomerEntity entity = new CustomerEntity(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
                    );
            entities.add(entity);

        }
        return entities;
    }

    @Override
    public boolean update(CustomerEntity customer) throws SQLException, NamingException {
        String sql = "UPDATE customers SET name=?, contact=?, address=?, note=? WHERE id=?";
        return SQLUtil.execute(sql,customer.getName(),customer.getContact(),customer.getAddress(),customer.getNote(),customer.getId());
    }

    @Override
    public boolean delete(String customerId) throws SQLException, NamingException {
        String sql = "DELETE FROM customers WHERE id = ?";
        return SQLUtil.execute(sql, customerId);
    }

    @Override
    public CustomerEntity getById(String customerId) throws SQLException, NamingException {
        String sql = "SELECT * FROM customers WHERE id=?";
        ResultSet resultSet = SQLUtil.execute(sql,customerId);
        CustomerEntity entity = null;
        while (resultSet.next()){
            entity=new CustomerEntity(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        }

        return entity;
    }

}
