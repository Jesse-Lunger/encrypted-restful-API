package persistence.impl;

import domain.Message;
import org.apache.ibatis.session.SqlSession;
import persistence.IConversationDAO;
import persistence.IMessageDAO;
import persistence.IMessageTypeDAO;
import utils.MySQLFactory;

import java.util.List;

public class MessageDAO implements IMessageDAO {
    @Override
    public List<Message> getAll() {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageDAO mapper = sqlSession.getMapper(IMessageDAO.class);
            return mapper.getAll();
        }
    }

    @Override
    public Message getEntityById(int id) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageDAO mapper = sqlSession.getMapper(IMessageDAO.class);
            return mapper.getEntityById(id);
        }
    }

    @Override
    public void saveEntity(Message message) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageDAO mapper = sqlSession.getMapper(IMessageDAO.class);
            mapper.saveEntity(message);
            sqlSession.commit();
        }
    }

    @Override
    public void updateEntity(Message message) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageDAO mapper = sqlSession.getMapper(IMessageDAO.class);
            mapper.updateEntity(message);
            sqlSession.commit();
        }
    }

    @Override
    public void removeEntityById(int id) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageDAO mapper = sqlSession.getMapper(IMessageDAO.class);
            mapper.removeEntityById(id);
            sqlSession.commit();
        }
    }
}
