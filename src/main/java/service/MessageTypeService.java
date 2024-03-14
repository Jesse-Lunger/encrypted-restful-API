package service;

import domain.MessageType;
import persistence.IMessageTypeDAO;
import persistence.impl.MessageTypeDAO;

import java.util.List;

public class MessageTypeService implements IMessageTypeDAO {

    private final MessageTypeDAO messageTypeDAO;

    public MessageTypeService(){
        messageTypeDAO = new MessageTypeDAO();
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
