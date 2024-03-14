package service;

import domain.User;
import persistence.IUserDAO;
import persistence.impl.UserDAO;

import java.util.List;

public class UserService implements IUserDAO {

    private final UserDAO userDAO;

    public UserService(){
        userDAO = new UserDAO();
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public User getEntityById(int id) {
        return userDAO.getEntityById(id);
    }

    @Override
    public User getByUserName(String userName) {
        return userDAO.getByUserName(userName);
    }

    @Override
    public void saveEntity(User user) {
        userDAO.saveEntity(user);
    }

    @Override
    public void updateEntity(User user) {
        userDAO.updateEntity(user);
    }

    @Override
    public void removeEntityById(int id) {
        userDAO.removeEntityById(id);
    }
}
