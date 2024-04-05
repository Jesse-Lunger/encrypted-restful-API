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
import utils.encryptionMethods.domain.ConversationMethods;
import utils.encryptionMethods.domain.UserMethods;

import java.util.List;

public class ConversationTest {

    private final UserService userService = new UserService();
    private final ConversationService conversationService = new ConversationService();

    @DataProvider
    public Object[][] users(){
        return new Object[][]{
                {"john", "password1"},
                {"billy", "password2"},
                {"mick", "password3"},
                {"nolan", "password3"},
                {"durgan", "password4"}
        };
    }

    @BeforeTest()
    public void populateUsers(){
        Object[][] data = users();
        for (Object[] userData: users()){
            User user = UserMethods.createEncyptedUser((String) userData[0], (String) userData[1]);
            userService.saveEntity(user);
            Assert.assertNotNull(userService.getByUserName(user.getUserName()));
        }
    }

    @BeforeTest(dependsOnMethods = "populateUsers")
    public void populateConversations(){
        List<User> users = userService.getAll();
        int numUsers = users.size();
        for (int i = 0; i < numUsers; i++){
            for (int j = i + 1; j < numUsers; j++){
                Conversation conversation = ConversationMethods.createEncryptedConversation(users.get(i), users.get(j));
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