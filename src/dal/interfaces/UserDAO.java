package dal.interfaces;

import blogic.entity.User;

import java.util.ArrayList;

/**
 * Created by hammer on 20.07.2017.
 */
public interface UserDAO {

    int addUser(User u);

    void updateUser(User u);

    void deleteUser(User u);

    User getUser(int userId);

    User getUser(String user);

    boolean checkUser(String user, String pass);

    ArrayList<User> getUserList();

    String[] users();
}