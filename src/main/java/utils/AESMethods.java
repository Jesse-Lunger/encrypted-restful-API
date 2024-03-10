package utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class AESMethods {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static Optional<SecretKey> generateAESKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            return Optional.of(keyGenerator.generateKey());
        } catch (NoSuchAlgorithmException e){
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static String convertKeyToString(SecretKey secretKey){
        byte[] bytes = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static SecretKey convertStringToKey(String string){
        byte[] bytes = Base64.getDecoder().decode(string);
        return new SecretKeySpec(bytes, 0, bytes.length, "AES");
    }

    public static Optional<String> encrypt(SecretKey secretKey, String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(plainText.getBytes());
            return Optional.of(Base64.getEncoder().encodeToString(bytes));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e){
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<String> decrypt(SecretKey secretKey, String encryptedText){
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] byteFormat = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(byteFormat);
            return Optional.of(new String(decryptedBytes, StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e){
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }
}