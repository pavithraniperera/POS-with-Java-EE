package com.example.posbackend.Dao;

import com.example.posbackend.Entity.CustomerEntity;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDao {
    boolean save(CustomerEntity customer) throws SQLException, NamingException;

    List<CustomerEntity> getAll() throws SQLException,NamingException;

    boolean update(CustomerEntity customer) throws SQLException,NamingException;

    boolean delete(String customerId) throws SQLException,NamingException;

    CustomerEntity getById(String customerId) throws SQLException,NamingException;
}
