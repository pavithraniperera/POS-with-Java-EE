package com.example.posbackend.Bo;

import com.example.posbackend.Dto.OrderDto;

import javax.naming.NamingException;
import java.sql.SQLException;

public interface OrderBo {
    boolean save(OrderDto orderDto) throws SQLException, NamingException;
}
