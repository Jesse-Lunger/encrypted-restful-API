package persistence.impl;

import domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import persistence.IUserDAO;
import utils.MySQLFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Repository
public class UserDAO implements IUserDAO {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

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
    public User getUserByName(String userName) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IUserDAO mapper = sqlSession.getMapper(IUserDAO.class);
            return mapper.getUserByName(userName);
        }
    }

    @Override
    public void saveEntity(User user) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IUserDAO mapper = sqlSession.getMapper(IUserDAO.class);
            if (mapper.getUserByName(user.getUserName()) == null) {
                mapper.saveEntity(user);
                sqlSession.commit();
            }
        }
    }

    @Override
    public void updateEntity(User user) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IUserDAO mapper = sqlSession.getMapper(IUserDAO.class);
            if (mapper.getUserByName(user.getUserName()) != null) {
                mapper.updateEntity(user);
                sqlSession.commit();
            }
        }
    }

    @Override
    public void removeEntityById(int id) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IUserDAO mapper = sqlSession.getMapper(IUserDAO.class);
            if (mapper.getEntityById(id) != null) {
                mapper.removeEntityById(id);
                sqlSession.commit();
            }
        }
    }
}
