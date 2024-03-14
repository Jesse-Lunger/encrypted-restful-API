package persistence.impl;

import domain.MessageType;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.IMessageTypeDAO;
import utils.MySQLFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class MessageTypeDAO implements IMessageTypeDAO {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public List<MessageType> getAll() {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageTypeDAO mapper = sqlSession.getMapper(IMessageTypeDAO.class);
            return mapper.getAll();
        }
    }

    @Override
    public MessageType getEntityById(int id) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageTypeDAO mapper = sqlSession.getMapper(IMessageTypeDAO.class);
            return mapper.getEntityById(id);
        }
    }

    @Override
    public MessageType getMessageTypeByName(String messageTypeName) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageTypeDAO mapper = sqlSession.getMapper(IMessageTypeDAO.class);
            return mapper.getMessageTypeByName(messageTypeName);
        }
    }

    @Override
    public void saveEntity(MessageType messageType) {
        if (getMessageTypeByName(messageType.getMessageTypeName()) != null){
            LOGGER.error(messageType.getMessageTypeName() + " already exists");
            return;
        }
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageTypeDAO mapper = sqlSession.getMapper(IMessageTypeDAO.class);
            mapper.saveEntity(messageType);
            sqlSession.commit();
        }
    }

    @Override
    public void updateEntity(MessageType messageType) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageTypeDAO mapper = sqlSession.getMapper(IMessageTypeDAO.class);
            mapper.updateEntity(messageType);
            sqlSession.commit();
        }
    }

    @Override
    public void removeEntityById(int id) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IMessageTypeDAO mapper = sqlSession.getMapper(IMessageTypeDAO.class);
            mapper.removeEntityById(id);
            sqlSession.commit();
        }
    }
}
