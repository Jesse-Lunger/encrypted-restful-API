package utils;

import java.lang.invoke.MethodHandles;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAMethods {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static Optional<KeyPair> generateKeyPair(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(3072);
            return Optional.of(keyPairGenerator.generateKeyPair());
        } catch (NoSuchAlgorithmException e){
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static String convertPublicKeyToString(PublicKey publicKey){
        byte[] bytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String convertPrivateKeyToString(PrivateKey privateKey){
        byte[] bytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static Optional<PublicKey> convertStringToPublicKey(String publicKey){
        try{
            byte[] bytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return Optional.of(keyFactory.generatePublic(spec));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<PrivateKey> convertStringToPrivateKey(String privateKey){
        try {
            byte[] bytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return Optional.of(keyFactory.generatePrivate(spec));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<String> encrypt(PublicKey publicKey, String plainText){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(plainText.getBytes());
            return Optional.of(Base64.getEncoder().encodeToString(bytes));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e){
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<String> decrypt(PrivateKey privateKey, String encryptedText){
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return Optional.of(new String(bytes));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException e){
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }
}