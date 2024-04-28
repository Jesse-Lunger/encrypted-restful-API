package service;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import persistence.IUserDAO;
import persistence.impl.UserDAO;

import java.util.List;

public class UserService implements IUserDAO{



    private IUserDAO iUserDAO ;

    public UserService(UserDAO userDAO){
        this.iUserDAO = userDAO;
    }

    @Override
    public User getUserByName(String userName) {
        return iUserDAO.getUserByName(userName);
    }

    @Override
    public List<User> getAll() {
        return iUserDAO.getAll();
    }

    @Override
    public User getEntityById(int id) {
        return iUserDAO.getEntityById(id);
    }

    @Override
    public void saveEntity(User user) {
        iUserDAO.saveEntity(user);
    }

    @Override
    public void updateEntity(User user) {
        iUserDAO.updateEntity(user);
    }

    @Override
    public void removeEntityById(int id) {
        iUserDAO.removeEntityById(id);
    }
}
