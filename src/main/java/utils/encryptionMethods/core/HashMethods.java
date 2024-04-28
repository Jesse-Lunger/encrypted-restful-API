package utils.encryptionMethods.core;

import org.mindrot.jbcrypt.BCrypt;

public class HashMethods {

    public static String hashString(String password, int iterations) {
        String salt = BCrypt.gensalt(iterations);
        return BCrypt.hashpw(password, salt);
    }

    public static String hashString(String string) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(string, salt);
    }

    public static boolean isHashMatch(String string, String hashedString) {
        return BCrypt.checkpw(string, hashedString);
    }
}
