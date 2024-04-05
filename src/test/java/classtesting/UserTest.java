package classtesting;

import domain.User;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.UserService;
import utils.encryptionMethods.domain.UserMethods;

public class UserTest {

    private static final UserService userService = new UserService();

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

    @BeforeTest
    public void pupulateUsers(){
        Object[][] data = users();
        for (Object[] userData: users()){
            User user = new User.Builder()
                    .userName((String) userData[0])
                    .passwordHashed((String) userData[1])
                    .build();
            UserMethods.encryptUser(user);
            userService.saveEntity(user);
            Assert.assertNotNull(userService.getByUserName(user.getUserName()));
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
