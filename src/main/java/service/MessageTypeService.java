package service;

import domain.MessageType;
import org.springframework.stereotype.Service;
import persistence.IMessageTypeDAO;
import persistence.impl.MessageTypeDAO;

import java.util.List;

@Service
public class MessageTypeService implements IMessageTypeDAO {

    private final IMessageTypeDAO messageTypeDAO;

    public MessageTypeService(MessageTypeDAO messageTypeDAO) {
        this.messageTypeDAO = messageTypeDAO;
    }

    @Override
    public List<MessageType> getAll() {
        return messageTypeDAO.getAll();
    }

    @Override
    public MessageType getEntityById(int id) {
        return messageTypeDAO.getEntityById(id);
    }

    @Override
    public MessageType getMessageTypeByName(String messageTypeName) {
        return messageTypeDAO.getMessageTypeByName(messageTypeName);
    }

    @Override
    public void saveEntity(MessageType messageType) {
        messageTypeDAO.saveEntity(messageType);
    }

    @Override
    public void updateEntity(MessageType messageType) {
        messageTypeDAO.updateEntity(messageType);
    }

    @Override
    public void removeEntityById(int id) {
        messageTypeDAO.removeEntityById(id);
    }

}
