package com.example.posbackend.Bo;

import com.example.posbackend.Dto.UserDto;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface UserBo {
    boolean saveUser(UserDto userDTO) throws SQLException, NamingException;

    List<UserDto> getAllUsers() throws SQLException, NamingException;
}
