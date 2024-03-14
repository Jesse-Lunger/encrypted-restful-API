import domain.MessageType;
import service.MessageTypeService;

public class Main {

    public static void main(String[] args) {

        // hash methods

        // AES encryption symmetric

        // RSA encryption asymmetric

        // hardford travelers gaurdian fdm

        // "dog" "cat" %3 -> %10000000000000000000000000000000000000000000000000000000000000000

        MessageType messageType = new MessageType.Builder().messageTypeName("asdf").build();

        MessageTypeService messageTypeService = new MessageTypeService();
        messageTypeService.saveEntity(messageType);
        messageTypeService.saveEntity(messageType);
        messageType = messageTypeService.getMessageTypeByName(messageType.getMessageTypeName());
        System.out.println(messageType);


        messageTypeService.removeEntityById(messageType.getMessageTypeId());


    }
}
