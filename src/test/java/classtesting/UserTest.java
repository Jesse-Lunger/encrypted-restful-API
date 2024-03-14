package classtesting;

import domain.User;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.UserService;

public class UserTest {

    private static final UserService userService = new UserService();

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

    @BeforeTest
    public void pupulateUsers(){
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

    @Test
    public void test(){
        System.out.println(userService.getAll());
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
