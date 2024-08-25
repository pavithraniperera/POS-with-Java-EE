package com.example.posbackend.Bo;

import com.example.posbackend.Dto.CustomerDto;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBo {
    boolean save(CustomerDto customerDto) throws SQLException, NamingException;

    List<CustomerDto> getAllCustomers() throws SQLException,NamingException;

    boolean updateCustomer(CustomerDto customerDTO) throws SQLException,NamingException;

    boolean deleteCustomer(String customerId) throws SQLException,NamingException;

    CustomerDto findCustomerById(String customerId) throws SQLException,NamingException;
}
