package classtesting;

import domain.Conversation;
import domain.User;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.ConversationService;
import service.UserService;

import java.util.List;

public class ConversationTest {

    private final UserService userService = new UserService();
    private final ConversationService conversationService = new ConversationService();

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

    @BeforeTest()
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

    @Test
    public void updateTest() {
        Conversation conversation = conversationService.getAll().getFirst();
        conversation.setReceiver(conversation.getSender());
        conversationService.updateEntity(conversation);
        conversation = conversationService.getEntityById(conversation.getConversationId());
        Assert.assertEquals(conversation.getReceiver().getUserId(), conversation.getSender().getUserId());
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

}
