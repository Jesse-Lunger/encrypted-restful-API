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
import persistence.impl.ConversationDAO;
import persistence.impl.MessageDAO;
import persistence.impl.MessageTypeDAO;
import persistence.impl.UserDAO;
import service.ConversationService;
import service.MessageService;
import service.MessageTypeService;
import service.UserService;
import utils.encryptionMethods.core.BasicConversions;
import utils.encryptionMethods.domain.ConversationMethods;
import utils.encryptionMethods.domain.MessageMethods;
import utils.encryptionMethods.domain.UserMethods;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class MessageTest {


    private final UserService userService = new UserService(new UserDAO());
    private final ConversationService conversationService = new ConversationService(new ConversationDAO());
    private final MessageTypeService messageTypeService = new MessageTypeService(new MessageTypeDAO());
    private final MessageService messageService = new MessageService(new MessageDAO());


    @DataProvider(name = "messageTypes")
    public Object[][] messageTypes() {
        return new Object[][]{
                {"publicMessage"},
                {"privateMessage"}
        };
    }

    @DataProvider
    public Object[][] users() {
        return new Object[][]{
                {"john", "password1"},
                {"billy", "password2"},
                {"mick", "password3"},
                {"nolan", "password3"},
                {"durgan", "password4"}
        };
    }

    public HashMap<String, String> createUserMap(Object[][] users) {
        HashMap<String, String> userMap = new HashMap<>();
        for (Object[] user : users) {
            String username = (String) user[0];
            String password = (String) user[1];
            userMap.put(username, password);
        }
        return userMap;
    }

    @DataProvider
    public Object[][] messages() {
        return new Object[][]{
                {"message aes1", new Timestamp(System.currentTimeMillis())},
                {"message aes2", new Timestamp(System.currentTimeMillis())},
                {"message aes3", new Timestamp(System.currentTimeMillis())},
                {"message aes4", new Timestamp(System.currentTimeMillis())}
        };
    }

    @BeforeTest
    public void populateMessageTypes() {
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
    public void populateUsers() {
        Object[][] data = users();
        for (Object[] userData : users()) {
            User user = UserMethods.createEncyptedUser((String) userData[0], (String) userData[1]);
            userService.saveEntity(user);
            Assert.assertNotNull(userService.getUserByName(user.getUserName()));
        }
    }

    @BeforeTest(dependsOnMethods = "populateUsers")
    public void populateConversations() {
        List<User> users = userService.getAll();
        int numUsers = users.size();
        for (int i = 0; i < numUsers; i++) {
            for (int j = i + 1; j < numUsers; j++) {
                Conversation conversation = ConversationMethods.createEncryptedConversation(users.get(i), users.get(j));
                conversationService.saveEntity(conversation);
            }
        }
    }

    @BeforeTest(dependsOnMethods = "populateConversations")
    public void populateMessages() {
        Object[][] data = messages();
        List<Conversation> conversationList = conversationService.getAll();
        List<MessageType> messageTypeList = messageTypeService.getAll();

        HashMap<String, String> userMap = createUserMap(users());

        int count = 0;
        for (Object[] messageData : data) {
            Conversation conversation = conversationList.get(count);

            String senderPassword = userMap.get(conversation.getSender().getUserName());
            String receiverPassword = userMap.get(conversation.getReceiver().getUserName());
            MessageType messageType = messageTypeList.get(count % messageTypeList.size());

            Message message = MessageMethods.createEncryptedMessage(senderPassword, conversation, messageType, (String) messageData[0]);
            messageService.saveEntity(message);
            count++;
        }
    }

    @Test
    public void updateTest() {
        Message message = messageService.getAll().get(0);
        byte[] newMessage = BasicConversions.stringToBytes("new message");
        message.setMessage(newMessage);
        messageService.updateEntity(message);
        message = messageService.getAll().get(0);
        Assert.assertEquals(message.getMessage(), newMessage);
    }

    @Test
    public void receiverSenderDecryptMessagetest() {
        HashMap<String, String> userMap = createUserMap(users());
        Message message = messageService.getAll().get(0);
        Conversation conversation = message.getConversation();

        String receiverPassword = userMap.get(conversation.getReceiver().getUserName());
        String decryptedMessageReceiver = MessageMethods.receiverDecryptMessage(message, receiverPassword);

        String senderPassword = userMap.get(conversation.getSender().getUserName());
        String decryptedMessageSender = MessageMethods.senderDecryptMessage(message, senderPassword);

        Assert.assertEquals(decryptedMessageReceiver, decryptedMessageSender);
    }

    @AfterTest
    public void depopulateUsers() {
        for (Object[] userData : users()) {
            String username = (String) userData[0];
            User user = userService.getUserByName(username);
            userService.removeEntityById(user.getUserId());
        }
        Assert.assertTrue(userService.getAll().isEmpty());
    }

    @AfterTest(dependsOnMethods = "depopulateUsers")
    public void depopulateMessageTypes() {
        for (MessageType messageType : messageTypeService.getAll()) {
            messageTypeService.removeEntityById(messageType.getMessageTypeId());
        }
    }

}
