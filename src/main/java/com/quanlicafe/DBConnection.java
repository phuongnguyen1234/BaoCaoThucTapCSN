package com.quanlicafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Properties props = DBConfig.loadProperties("db.properties");
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Kết nói thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                throw new SQLException("Unable to connect to the database.");
            }
        }
        return connection;
    }
}
