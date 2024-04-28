package service;

import domain.Conversation;
import org.springframework.stereotype.Service;
import persistence.IConversationDAO;
import persistence.impl.ConversationDAO;

import java.util.List;

@Service
public class ConversationService implements IConversationDAO {

    private final ConversationDAO conversationDAO;

    public ConversationService(ConversationDAO conversationDAO) {
        this.conversationDAO = conversationDAO;
    }

    @Override
    public List<Conversation> getAll() {
        return conversationDAO.getAll();
    }

    @Override
    public Conversation getEntityById(int id) {
        return conversationDAO.getEntityById(id);
    }

    @Override
    public void saveEntity(Conversation conversation) {
        conversationDAO.saveEntity(conversation);
    }

    @Override
    public void updateEntity(Conversation conversation) {
        conversationDAO.updateEntity(conversation);
    }

    @Override
    public void removeEntityById(int id) {
        conversationDAO.removeEntityById(id);
    }
}
