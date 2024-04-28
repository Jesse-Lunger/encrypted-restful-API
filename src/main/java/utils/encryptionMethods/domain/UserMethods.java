package utils.encryptionMethods.domain;

import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.encryptionMethods.core.AESMethods;
import utils.encryptionMethods.core.HashMethods;
import utils.encryptionMethods.core.RSAMethods;

import javax.crypto.SecretKey;
import java.lang.invoke.MethodHandles;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Optional;

public class UserMethods {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void assignPublicPrivateKeys(User user) {
        Optional<KeyPair> keyPairOptional = RSAMethods.generateKeyPair();
        if (keyPairOptional.isEmpty()) {
            LOGGER.error("Failed to generate RSA keyPair");
            return;
        }
        user.setPublicKey(keyPairOptional.get().getPublic().getEncoded());
        user.setPrivateKey(keyPairOptional.get().getPrivate().getEncoded());
    }

    public static void encryptUser(User user) {

        String hashPassword15 = HashMethods.hashString(user.getPassword(), 15);
        Optional<SecretKey> secretKeyOptional = AESMethods.deriveKey100(user.getPassword());

        if (secretKeyOptional.isEmpty()) {
            LOGGER.error("Failed to generate secret key from hash");
            return;
        }
        Optional<byte[]> encryptedPrivateKeyOptional = AESMethods.encryptBytes(secretKeyOptional.get(), user.getPrivateKey());
        if (encryptedPrivateKeyOptional.isEmpty()) {
            LOGGER.error("Failed to encrypt private key with password hash");
            return;
        }
        user.setPassword(hashPassword15);
        user.setPrivateKey(encryptedPrivateKeyOptional.get());
    }

    public static User createEncyptedUser(String username, String password) {
        User user = new User.Builder()
                .userName(username)
                .password(password)
                .build();
        assignPublicPrivateKeys(user);
        encryptUser(user);
        return user;
    }

    public static User decryptUser(User user, String password) {
        if (!HashMethods.isHashMatch(password, user.getPassword())) {
            LOGGER.error("Incorrect password");
        }
        Optional<SecretKey> secretKeyOptional = AESMethods.deriveKey100(password);
        if (secretKeyOptional.isEmpty()) {
            LOGGER.error("Failed to generate key from password");
            return null;
        }
        Optional<byte[]> decryptBytesOptional = AESMethods.decryptBytes(secretKeyOptional.get(), user.getPrivateKey());
        if (decryptBytesOptional.isEmpty()) {
            LOGGER.error("Failed to decrypt key");
            return null;
        }
        Optional<PrivateKey> privateKeyOptional = RSAMethods.convertBytesToPrivateKey(decryptBytesOptional.get());
        if (privateKeyOptional.isEmpty()) {
            LOGGER.error("Failed to convert privateKey(byte[]) -> PrivateKey");
            return null;
        }
        user.setPrivateKey(privateKeyOptional.get().getEncoded());
        user.setPassword(password);
        return user;
    }

    public static PrivateKey getUserPrivateKey(User user, String password) {
        if (!HashMethods.isHashMatch(password, user.getPassword())) {
            LOGGER.error("Incorrect password");
        }
        Optional<SecretKey> secretKeyOptional = AESMethods.deriveKey100(password);
        if (secretKeyOptional.isEmpty()) {
            LOGGER.error("Failed to generate key from password");
            return null;
        }
        Optional<byte[]> decryptBytesOptional = AESMethods.decryptBytes(secretKeyOptional.get(), user.getPrivateKey());
        if (decryptBytesOptional.isEmpty()) {
            LOGGER.error("Failed to decrypt key");
            return null;
        }
        Optional<PrivateKey> privateKeyOptional = RSAMethods.convertBytesToPrivateKey(decryptBytesOptional.get());
        if (privateKeyOptional.isEmpty()) {
            LOGGER.error("Failed to convert privateKey(byte[]) -> PrivateKey");
            return null;
        }
        return privateKeyOptional.get();
    }
}
