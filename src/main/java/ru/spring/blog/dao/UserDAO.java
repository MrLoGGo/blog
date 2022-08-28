package ru.spring.blog.dao;

import org.springframework.stereotype.Component;
import ru.spring.blog.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {
    private static int USER_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/blog";
    private static final String USERNAME ="postgres";
    private static final String PASSWORD = "Tujh12325011";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            connection = DriverManager.getConnection(URL, USERNAME,PASSWORD);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }

    public List<User> index() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));

                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    public void save(User user){
        List<User> nullList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "insert into users(login, email, password) values('" + user.getLogin() +
                    "',"+"'" + user.getEmail() + "'," +"'" + user.getPassword()+"');" ;
            ResultSet resultSet = statement.executeQuery("select count(*) from users where login = '" + user.getLogin() + "'");
            resultSet.next();
            if(resultSet.getInt(1) == 0){
                statement.executeUpdate(SQL);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
