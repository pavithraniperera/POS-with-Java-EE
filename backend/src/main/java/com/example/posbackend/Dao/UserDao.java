package com.example.posbackend.Dao;

import com.example.posbackend.Dto.UserDto;
import com.example.posbackend.Entity.UserEntity;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    boolean saveUser(UserDto user) throws SQLException, NamingException;

    List<UserEntity> getAllUsers() throws SQLException,NamingException;
}
