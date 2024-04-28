package classtesting;

import domain.User;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import persistence.impl.UserDAO;
import service.UserService;
import utils.encryptionMethods.domain.UserMethods;

public class TestUserTest {

    private static final UserService userService = new UserService(new UserDAO());

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

    @BeforeTest
    public void populateUsers() {
        for (Object[] userData : users()) {
            User user = UserMethods.createEncyptedUser((String) userData[0], (String) userData[1]);
            userService.saveEntity(user);
            Assert.assertNotNull(userService.getUserByName(user.getUserName()));
        }
    }

    @Test
    public void test() {
        System.out.println(userService.getAll());
    }

    @AfterTest
    public void depopulateUsers() {
        Object[][] data = users();
        for (Object[] userData : users()) {
            String username = (String) userData[0];
            User user = userService.getUserByName(username);
            userService.removeEntityById(user.getUserId());
        }
        Assert.assertTrue(userService.getAll().isEmpty());
    }
}
