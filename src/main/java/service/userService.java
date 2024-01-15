package service;

import java.sql.SQLException;

import DAO.userDAO;
import model.*;;

public class userService {
    public static Integer saveUser(User user) {
        try {
            if (userDAO.isExists(user.getEmail())) {
                return 0;
            }
            return userDAO.savUsers(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
