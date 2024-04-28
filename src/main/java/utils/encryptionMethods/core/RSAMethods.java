package utils.encryptionMethods.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.lang.invoke.MethodHandles;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;

public class RSAMethods {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static Optional<KeyPair> generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return Optional.of(keyPairGenerator.generateKeyPair());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static String convertKeyToString(PublicKey publicKey) {
        byte[] bytes = publicKey.getEncoded();
        return BasicConversions.bytesToString(bytes);
    }

    public static String convertKeyToString(PrivateKey privateKey) {
        byte[] bytes = privateKey.getEncoded();
        return BasicConversions.bytesToString(bytes);
    }

    public static Optional<PublicKey> convertStringToPublicKey(String publicKey) {
        byte[] bytes = BasicConversions.stringToBytes(publicKey);
        return convertBytesToPublicKey(bytes);
    }

    public static Optional<PublicKey> convertBytesToPublicKey(byte[] bytes) {
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return Optional.of(keyFactory.generatePublic(spec));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<PrivateKey> convertStringToPrivateKey(String privateKey) {
        byte[] bytes = BasicConversions.stringToBytes(privateKey);
        return convertBytesToPrivateKey(bytes);
    }

    public static Optional<PrivateKey> convertBytesToPrivateKey(byte[] bytes) {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return Optional.of(keyFactory.generatePrivate(spec));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<String> encryptString(PublicKey publicKey, String plainText) {
        byte[] tmp = plainText.getBytes();
        Optional<byte[]> encryptedBytesOptional = encryptBytes(publicKey, tmp);
        return encryptedBytesOptional.map(BasicConversions::bytesToString);
    }

    public static Optional<String> decryptString(PrivateKey privateKey, String encryptedText) {
        byte[] bytes = BasicConversions.stringToBytes(encryptedText);
        Optional<byte[]> encryptedbytesOptional = decryptBytes(privateKey, bytes);
        return encryptedbytesOptional.map(String::new);
    }

    public static Optional<byte[]> encryptBytes(PublicKey publicKey, byte[] encryptedBytes) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Optional.of(cipher.doFinal(encryptedBytes));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<byte[]> decryptBytes(PrivateKey privateKey, byte[] encryptedBytes) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return Optional.of(cipher.doFinal(encryptedBytes));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }
}