package encryptiontesting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AESMethods;
import utils.HashMethods;
import utils.RSAMethods;

import javax.crypto.SecretKey;
import java.lang.invoke.MethodHandles;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;

public class EncryptionMethodTest {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void testHash(){
        String password = "123assdfdsaf";
        String hashedPassword = HashMethods.hashString(password, 14);
        Assert.assertTrue(HashMethods.isHashMatch(password, hashedPassword), "password does not match hashedPassword");
    }

    @Test
    public void testAES(){
        Optional<SecretKey> secretKeyOptional = AESMethods.generateAESKey();
        Assert.assertTrue(secretKeyOptional.isPresent());
        SecretKey secretKey = secretKeyOptional.get();
        String stringKey = AESMethods.convertKeyToString(secretKey);
        secretKey = AESMethods.convertStringToKey(stringKey);
        String plainText = "Hello this is a plain text message asdf983204!$#%#^DFG%%^)_";
        Optional<String> encryptedTextOpt = AESMethods.encrypt(secretKey, plainText);
        Assert.assertTrue(encryptedTextOpt.isPresent());
        Optional<String> decryptedText = AESMethods.decrypt(secretKey, encryptedTextOpt.get());
        Assert.assertTrue(decryptedText.isPresent());
        Assert.assertEquals(decryptedText.get(), plainText);
    }

    @Test
    void testRSA(){
        Optional<KeyPair> keyPairOptional = RSAMethods.generateKeyPair();
        Assert.assertTrue(keyPairOptional.isPresent());
        Optional<SecretKey> AESKeyOptional = AESMethods.generateAESKey();
        Assert.assertTrue(AESKeyOptional.isPresent());

        String publicKeyToString = RSAMethods.convertPublicKeyToString(keyPairOptional.get().getPublic());
        Optional<PublicKey> publicKeyOptional = RSAMethods.convertStringToPublicKey(publicKeyToString);
        Assert.assertTrue(publicKeyOptional.isPresent());
        String AESKey = AESMethods.convertKeyToString(AESKeyOptional.get());
        Optional<String> encryptedMessageOptional = RSAMethods.encrypt(keyPairOptional.get().getPublic(), AESKey);
        Assert.assertTrue(encryptedMessageOptional.isPresent());

        String privateKeyToString = RSAMethods.convertPrivateKeyToString(keyPairOptional.get().getPrivate());
        Optional<PrivateKey> privateKeyOptional = RSAMethods.convertStringToPrivateKey(privateKeyToString);
        Assert.assertTrue(privateKeyOptional.isPresent());
        Optional<String> decryptedMessageOptional = RSAMethods.decrypt(keyPairOptional.get().getPrivate(), encryptedMessageOptional.get());
        Assert.assertTrue(decryptedMessageOptional.isPresent());

        Assert.assertEquals(decryptedMessageOptional.get(), AESKey);
    }
}
