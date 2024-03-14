package persistence;

import domain.MessageType;
import org.apache.ibatis.annotations.Param;

public interface IMessageTypeDAO extends IBaseDAO<MessageType>{

    public MessageType getMessageTypeByName(@Param("messageTypeName") String messageTypeName);

}
