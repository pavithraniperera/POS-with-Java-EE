package com.example.posbackend.Bo.impl;

import com.example.posbackend.Bo.ItemBo;
import com.example.posbackend.Dao.ItemDao;
import com.example.posbackend.Dao.impl.ItemDaoImpl;
import com.example.posbackend.Dto.CustomerDto;
import com.example.posbackend.Dto.ItemDto;
import com.example.posbackend.Entity.CustomerEntity;
import com.example.posbackend.Entity.ItemEntity;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    private ItemDao itemDao = new ItemDaoImpl();
    @Override
    public boolean saveItem(ItemDto itemDto) throws SQLException, NamingException {
       return itemDao.save(new ItemEntity( itemDto.getName(), itemDto.getPrice(), itemDto.getQuantity(), itemDto.getCategory(), itemDto.getDescription(), itemDto.getImgSrc()));
    }

    @Override
    public List<ItemDto> getAllCustomers() throws SQLException, NamingException {

        List<ItemEntity> itemEntities = itemDao.getAll();

        List<ItemDto> itemDtos = new ArrayList<>();
        for (ItemEntity item:itemEntities){
           ItemDto itemDto = new ItemDto(item.getId(),item.getName(),item.getPrice(),item.getQuantity(),item.getCategory(), item.getDescription(), item.getImgSrc());
           itemDtos.add(itemDto);
        }


        return itemDtos;
    }

    @Override
    public ItemDto findCustomerById(String itemName) throws SQLException, NamingException {
        ItemEntity entity = itemDao.getByName(itemName);
        ItemDto dto = new ItemDto(entity.getId(),entity.getName(), entity.getPrice(), entity.getQuantity(), entity.getCategory(), entity.getDescription(), entity.getImgSrc());

        return dto;
    }

    @Override
    public boolean updateItem(ItemDto itemDto) throws SQLException, NamingException {
        ItemEntity item = new ItemEntity(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getPrice(),
                itemDto.getQuantity(),
                itemDto.getCategory(),
                itemDto.getDescription(),
                itemDto.getImgSrc()
        );
        return itemDao.update(item);
    }

    @Override
    public boolean deleteCustomer(int itemId) throws SQLException, NamingException {
        return itemDao.delete(itemId);
    }
}
