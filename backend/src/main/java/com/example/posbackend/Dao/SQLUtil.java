package com.example.posbackend.Dao;

import com.example.posbackend.DB.DbConnection;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class SQLUtil {

    public static <T> T execute(String sql, Object... ob) throws SQLException, NamingException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < ob.length; i++) {
            preparedStatement.setObject(i + 1, ob[i]);
        }
        if (sql.startsWith("SELECT") || sql.startsWith("WITH")) {
            return (T) preparedStatement.executeQuery();
        } else {
            return (T) (Boolean) (preparedStatement.executeUpdate() > 0);
        }
    }
}
