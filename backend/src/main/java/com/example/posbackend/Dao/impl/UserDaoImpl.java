package com.example.posbackend.Dao.impl;

import com.example.posbackend.Dao.SQLUtil;
import com.example.posbackend.Dao.UserDao;
import com.example.posbackend.Dto.UserDto;
import com.example.posbackend.Entity.UserEntity;


import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {
    @Override
    public boolean saveUser(UserDto user) throws SQLException, NamingException {
        String sql = "INSERT INTO users ( userName, password) VALUES ( ?, ?)";
        return SQLUtil.execute(sql, user.getUserName(), user.getPassword());
    }

    @Override
    public List<UserEntity> getAllUsers() throws SQLException, NamingException {
        String sql = "SELECT userName, password FROM users";
        ResultSet resultSet = SQLUtil.execute(sql);
        List<UserEntity> users = new ArrayList<>();
        while (resultSet.next()){
            UserEntity user = new UserEntity();
            user.setUserName(resultSet.getString("userName"));
            user.setPassword(resultSet.getString("password"));
            users.add(user);
        }

        return users;

    }
}
