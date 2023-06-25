package com.codecool.bruteforce.users.repository;

import com.codecool.bruteforce.logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrackedUserRepositoryImpl {

    private final String dbFile;
    private Connection connection = null;
    private Logger logger;

    public CrackedUserRepositoryImpl(String dbFile, Logger logger) {
        this.dbFile = dbFile;
        this.logger = logger;
        this.connection = getConnection();
    }

    private Connection getConnection() {
        Connection conn;
        try {
            String url = "jdbc:sqlite:" + dbFile;
            conn = DriverManager.getConnection(url);

            logger.logInfo("Connection to SQLite has been established.");

            return conn;

        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }

        return null;
    }
    public void add(String userName, long timeStamp) {
        String sql = "INSERT INTO CrackedUsers(user_name, time_stamp) VALUES(?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setLong(2, timeStamp);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM CrackedUsers";

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
