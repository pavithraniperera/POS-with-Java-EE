package com.example.posbackend.Bo;

import com.example.posbackend.Dto.ItemDto;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface ItemBo {
    boolean saveItem(ItemDto itemDto) throws SQLException, NamingException;

    List<ItemDto> getAllCustomers() throws SQLException,NamingException;

    ItemDto findCustomerById(String itemId) throws SQLException,NamingException;

    boolean updateItem(ItemDto itemDto) throws SQLException,NamingException;

    boolean deleteCustomer(int itemId) throws SQLException,NamingException;
}
