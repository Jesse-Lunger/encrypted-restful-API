package service;

import domain.Message;
import org.springframework.stereotype.Service;
import persistence.IMessageDAO;
import persistence.impl.MessageDAO;

import java.util.List;

@Service
public class MessageService implements IMessageDAO {

    private final MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public List<Message> getAll() {
        return messageDAO.getAll();
    }

    @Override
    public Message getEntityById(int id) {
        return messageDAO.getEntityById(id);
    }

    @Override
    public void saveEntity(Message message) {
        messageDAO.saveEntity(message);
    }

    @Override
    public void updateEntity(Message message) {
        messageDAO.updateEntity(message);
    }

    @Override
    public void removeEntityById(int id) {
        messageDAO.removeEntityById(id);
    }
}
