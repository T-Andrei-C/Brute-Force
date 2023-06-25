package com.codecool.bruteforce.users.repository;

import com.codecool.bruteforce.logger.Logger;
import com.codecool.bruteforce.users.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final String dbFile;
    private Connection connection = null;
    private Logger logger;

    public UserRepositoryImpl(String dbFile, Logger logger) {
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

    public void add(String userName, String password) {
        String sql = "INSERT INTO Users(user_name, password) VALUES(?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(int id, String userName, String password) {
        String sql = "UPDATE Users SET user_name = ?, " + "password = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Users WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM Users";

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User get(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet result =  preparedStatement.executeQuery();
            return new User(id, result.getString("user_name"), result.getString("password"));
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM Users";
        try {
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()){
                users.add(new User(resultSet.getInt("id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("password")));
            }
            return users;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
