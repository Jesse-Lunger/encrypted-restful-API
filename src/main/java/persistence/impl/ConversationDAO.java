package persistence.impl;

import domain.Conversation;
import org.apache.ibatis.session.SqlSession;
import persistence.IConversationDAO;
import utils.encryptionMethods.core.MySQLFactory;

import java.util.List;

public class ConversationDAO implements IConversationDAO {

    @Override
    public List<Conversation> getAll() {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IConversationDAO mapper = sqlSession.getMapper(IConversationDAO.class);
            return mapper.getAll();
        }
    }

    @Override
    public Conversation getEntityById(int id) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IConversationDAO mapper = sqlSession.getMapper(IConversationDAO.class);
            return mapper.getEntityById(id);
        }
    }

    @Override
    public void saveEntity(Conversation conversation) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IConversationDAO mapper = sqlSession.getMapper(IConversationDAO.class);
            mapper.saveEntity(conversation);
            sqlSession.commit();
        }
    }

    @Override
    public void updateEntity(Conversation conversation) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IConversationDAO mapper = sqlSession.getMapper(IConversationDAO.class);
            mapper.updateEntity(conversation);
            sqlSession.commit();
        }
    }

    @Override
    public void removeEntityById(int id) {
        try (SqlSession sqlSession = MySQLFactory.getSqlSessionFactory().openSession()) {
            IConversationDAO mapper = sqlSession.getMapper(IConversationDAO.class);
            mapper.removeEntityById(id);
            sqlSession.commit();
        }
    }
}
