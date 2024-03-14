package classtesting;

import domain.Conversation;
import domain.Message;
import domain.MessageType;
import domain.User;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.ConversationService;
import service.MessageService;
import service.MessageTypeService;
import service.UserService;

import java.sql.Timestamp;
import java.util.List;

public class MessageTest {


    private final UserService userService = new UserService();
    private final ConversationService conversationService = new ConversationService();
    private final MessageTypeService messageTypeService = new MessageTypeService();
    private final MessageService messageService = new MessageService();


    @DataProvider(name = "messageTypes")
    public Object[][] messageTypes(){
        return new Object[][]{
                {"publicMessage"},
                {"privateMessage"}
        };
    }

    @DataProvider
    public Object[][] users(){
        return new Object[][]{
                {"john", "password1", "AESKey1", "publicKey1", "privateKey1"},
                {"billy", "password2", "AESKey2", "publicKey2", "privateKey2"},
                {"mick", "password3", "AESKey3", "publicKey3", "privateKey3"},
                {"nolan", "password3", "AESKey3", "publicKey3", "privateKey3"},
                {"durgan", "password4", "AESKey4", "publicKey4", "privateKey4"}
        };
    }
    @DataProvider
    public Object[][] messages(){
        return  new Object[][]{
                {"my aes1", "message aes1", "message hash1", "aeskey1", new Timestamp(System.currentTimeMillis())},
                {"my aes2", "message aes2", "message hash2", "aeskey2", new Timestamp(System.currentTimeMillis())},
                {"my aes3", "message aes3", "message hash3", "aeskey3", new Timestamp(System.currentTimeMillis())},
                {"my aes4", "message aes4", "message hash4", "aeskey4", new Timestamp(System.currentTimeMillis())}
        };
    }

    @BeforeTest
    public void populateMessageTypes(){
        Object[][] data = messageTypes();
        for (Object[] messageTypeData : data) {
            String messageTypeStr = (String) messageTypeData[0];
            MessageType messageType = new MessageType.Builder()
                    .messageTypeName(messageTypeStr)
                    .build();
            messageTypeService.saveEntity(messageType);

            Assert.assertNotNull(messageTypeService.getMessageTypeByName(messageType.getMessageTypeName()));
        }
    }

    @BeforeTest(dependsOnMethods = "populateMessageTypes")
    public void populateUsers(){
        Object[][] data = users();
        for (Object[] userData: users()){
            User user = new User.Builder()
                    .userName((String) userData[0])
                    .passwordHashed((String) userData[1])
                    .aesKeyEncrypted((String) userData[2])
                    .publicKey((String) userData[3])
                    .privateKeyEncrypted((String) userData[4])
                    .build();
            userService.saveEntity(user);
            Assert.assertTrue(user.getUserId() != 0);
        }
    }

    @BeforeTest(dependsOnMethods = "populateUsers")
    public void populateConversations(){
        List<User> users = userService.getAll();
        int numUsers = users.size();
        for (int i = 0; i < numUsers; i++){
            for (int j = i + 1; j < numUsers; j++){
                Conversation conversation = new Conversation.Builder()
                        .sender(users.get(i))
                        .receiver(users.get(j))
                        .build();
                conversationService.saveEntity(conversation);
            }
        }

    }

    @BeforeTest(dependsOnMethods = "populateConversations")
    public void populateMessages(){
        Object[][] data = messages();
        List<Conversation> conversationList = conversationService.getAll();
        List<MessageType> messageTypeList = messageTypeService.getAll();
        int count = 0;
        for (Object[] messageData: data){
            Message message = new Message.Builder()
                    .conversation(conversationList.get(count++))
                    .messageType(messageTypeList.get(count % messageTypeList.size()))
                    .messageSender((String) messageData[0])
                    .messageReceiver((String) messageData[1])
                    .messageSignature((String) messageData[2])
                    .aesKey((String) messageData[3])
                    .time((Timestamp) messageData[4])
                    .build();
            messageService.saveEntity(message);
        }
    }

    @Test
    public void updateTest() {
        System.out.println(messageService.getAll());
    }

    @AfterTest
    public void depopulateUsers(){
        Object[][] data = users();
        for (Object[] userData: users()){
            String username = (String) userData[0];
            User user = userService.getByUserName(username);
            userService.removeEntityById(user.getUserId());
        }
        Assert.assertTrue(userService.getAll().isEmpty());
    }

    @AfterTest(dependsOnMethods = "depopulateUsers")
    public void depopulateMessageTypes(){
        for (MessageType messageType: messageTypeService.getAll()){
            messageTypeService.removeEntityById(messageType.getMessageTypeId());
        }
    }

}
