package persistence.impl;

import domain.User;
import org.apache.ibatis.session.SqlSession;
import persistence.IUserDAO;
import utils.encryptionMethods.core.MySQLFactory;

import java.util.List;

public class UserDAO implements IUserDAO {
    @Override
    public List<User> getAll() {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IUserDAO mapper = sqlSession.getMapper(IUserDAO.class);
            return mapper.getAll();
        }
    }

    @Override
    public User getEntityById(int id) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IUserDAO mapper = sqlSession.getMapper(IUserDAO.class);
            return mapper.getEntityById(id);
        }
    }

    @Override
    public User getByUserName(String userName) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IUserDAO mapper = sqlSession.getMapper(IUserDAO.class);
            return mapper.getByUserName(userName);
        }
    }

    @Override
    public void saveEntity(User user) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IUserDAO mapper = sqlSession.getMapper(IUserDAO.class);
            if (mapper.getByUserName(user.getUserName()) == null){
                mapper.saveEntity(user);
                sqlSession.commit();
            }
        }
    }

    @Override
    public void updateEntity(User user) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IUserDAO mapper = sqlSession.getMapper(IUserDAO.class);
            if (mapper.getByUserName(user.getUserName()) != null){
                mapper.updateEntity(user);
                sqlSession.commit();
            }
        }
    }

    @Override
    public void removeEntityById(int id) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IUserDAO mapper = sqlSession.getMapper(IUserDAO.class);
            if (mapper.getEntityById(id) != null){
                mapper.removeEntityById(id);
                sqlSession.commit();
            }
        }
    }
}
