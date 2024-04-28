import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import persistence.IUserDAO;
import persistence.impl.UserDAO;
import service.UserService;
import utils.encryptionMethods.domain.UserMethods;

import java.lang.invoke.MethodHandles;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());


    public static void main(String[] args) {

    }
}
