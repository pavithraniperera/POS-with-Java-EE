package com.example.posbackend.Bo.impl;

import com.example.posbackend.Bo.UserBo;
import com.example.posbackend.Dao.UserDao;
import com.example.posbackend.Dao.impl.UserDaoImpl;
import com.example.posbackend.Dto.UserDto;
import com.example.posbackend.Entity.UserEntity;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    private final UserDao userDAO = new UserDaoImpl();
    @Override
    public boolean saveUser(UserDto user) throws SQLException, NamingException {
        return userDAO.saveUser(user);
    }

    @Override
    public List<UserDto> getAllUsers() throws SQLException, NamingException {
        List<UserEntity> userEntities = userDAO.getAllUsers();
        List<UserDto> userDTOs = new ArrayList<>();
        for (UserEntity user:userEntities){
            UserDto userDTO = new UserDto();
            userDTO.setUserName(user.getUserName());
            userDTO.setPassword(user.getPassword());
            userDTOs.add(userDTO);
        }

        return userDTOs;

    }
}
