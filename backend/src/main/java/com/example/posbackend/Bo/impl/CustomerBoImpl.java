package com.example.posbackend.Bo.impl;

import com.example.posbackend.Bo.CustomerBo;
import com.example.posbackend.Dao.CustomerDao;
import com.example.posbackend.Dao.impl.CustomerDaoImpl;
import com.example.posbackend.Dto.CustomerDto;
import com.example.posbackend.Dto.UserDto;
import com.example.posbackend.Entity.CustomerEntity;
import com.example.posbackend.Entity.UserEntity;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {

      private CustomerDao customerDao = new CustomerDaoImpl();
    @Override
    public boolean save(CustomerDto customerDto) throws SQLException, NamingException {
        CustomerEntity customer = new CustomerEntity(
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getContact(),
                customerDto.getAddress(),
                customerDto.getNote()
        );
        return customerDao.save(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() throws SQLException, NamingException {
        List<CustomerEntity> customerEntities = customerDao.getAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (CustomerEntity customer:customerEntities){
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.getId());
            customerDto.setName(customer.getName());
            customerDto.setContact(customer.getContact());
            customerDto.setAddress(customer.getAddress());
            customerDto.setNote(customer.getNote());

            customerDtos.add(customerDto);
        }

        return customerDtos;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDTO) throws SQLException, NamingException {
        CustomerEntity customer = new CustomerEntity(
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getContact(),
                customerDTO.getAddress(),
                customerDTO.getNote()
        );
        return customerDao.update(customer);
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException, NamingException {
        return customerDao.delete(customerId);
    }

    @Override
    public CustomerDto findCustomerById(String customerId) throws SQLException, NamingException {

        CustomerEntity entity = customerDao.getById(customerId);
        CustomerDto dto = new CustomerDto(entity.getId(),entity.getName(),entity.getContact(),entity.getAddress(),entity.getNote());

        return dto;
    }
}
