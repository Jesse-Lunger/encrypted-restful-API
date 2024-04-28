package utils.encryptionMethods.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Optional;

public class AESMethods {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static Optional<SecretKey> generateAESKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            return Optional.of(keyGenerator.generateKey());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static String convertKeyToString(SecretKey secretKey) {
        byte[] bytes = convertKeyToBytes(secretKey);
        return BasicConversions.bytesToString(bytes);
    }

    public static byte[] convertKeyToBytes(SecretKey secretKey) {
        return secretKey.getEncoded();
    }

    public static SecretKey convertStringToKey(String string) {
        byte[] bytes = BasicConversions.stringToBytes(string);
        return convertBytesToKey(bytes);
    }

    public static SecretKey convertBytesToKey(byte[] bytes) {
        return new SecretKeySpec(bytes, 0, bytes.length, "AES");
    }

    public static Optional<SecretKey> generateAesKeyFromHash(String hash) {
        try {
            char[] hashChars = hash.toCharArray();
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            PBEKeySpec keySpec = new PBEKeySpec(hashChars, new byte[16], 100, 256);
            byte[] keyBytes = factory.generateSecret(keySpec).getEncoded();
            return Optional.of(new SecretKeySpec(keyBytes, "AES"));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<SecretKey> deriveKey(String key, int iterations) {
        try {
            byte[] salt = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
            int keyLength = 256;
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, iterations, keyLength);
            return Optional.of(convertBytesToKey(factory.generateSecret(spec).getEncoded()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<SecretKey> deriveKey100(String key) {
        return deriveKey(key, 100);
    }

    public static Optional<String> encryptString(SecretKey secretKey, String plainText) {
        Optional<byte[]> encryptedPlainTextByteOptional = encryptBytes(secretKey, plainText.getBytes());
        return encryptedPlainTextByteOptional.map(BasicConversions::bytesToString);
    }

    public static Optional<String> decryptString(SecretKey secretKey, String encryptedText) {
        try {
            byte[] byteFormat = BasicConversions.stringToBytes(encryptedText);
            return decryptBytes(secretKey, byteFormat)
                    .map(decryptedBytes -> new String(decryptedBytes, StandardCharsets.UTF_8));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid Base64 encoded string", e);
            return Optional.empty();
        }
    }

    public static Optional<byte[]> encryptBytes(SecretKey secretKey, byte[] plainBytes) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainBytes);
            return Optional.of(encryptedBytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<byte[]> decryptBytes(SecretKey secretKey, byte[] encryptedBytes) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return Optional.of(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }
}