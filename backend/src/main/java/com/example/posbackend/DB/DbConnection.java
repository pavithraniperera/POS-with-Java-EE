package com.example.posbackend.DB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(DbConnection.class);

    private DbConnection() throws SQLException, NamingException {
        // Look up the DataSource using JNDI
        var ctx = new InitialContext();
        logger.info("Looking up the DataSource using JNDI...");
        DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/pos");
        // Get a connection from the DataSource
        connection = pool.getConnection();
    }

    public static DbConnection getInstance() throws SQLException, NamingException {
        if (dbConnection == null) {
            logger.info("Creating a new DbConnection instance.");
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
