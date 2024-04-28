package utils.encryptionMethods.domain;

import domain.Conversation;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.encryptionMethods.core.AESMethods;
import utils.encryptionMethods.core.RSAMethods;

import javax.crypto.SecretKey;
import java.lang.invoke.MethodHandles;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;

public class ConversationMethods {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void encryptConversation(Conversation conversation) {
        Optional<PublicKey> senderPublicKeyOptional = RSAMethods.convertBytesToPublicKey(conversation.getSender().getPublicKey());
        Optional<PublicKey> receiverPublicKeyOptional = RSAMethods.convertBytesToPublicKey(conversation.getReceiver().getPublicKey());

        if (senderPublicKeyOptional.isEmpty() || receiverPublicKeyOptional.isEmpty()) {
            LOGGER.error("encryptConversation: Failed to create public key from byte data");
            return;
        }
        Optional<byte[]> encryptedAESKeySenderOptional = RSAMethods.encryptBytes(senderPublicKeyOptional.get(), conversation.getAesKeySender());
        Optional<byte[]> encryptedAESKeyReceiverOptional = RSAMethods.encryptBytes(receiverPublicKeyOptional.get(), conversation.getAesKeyReceiver());
        if (encryptedAESKeySenderOptional.isEmpty() || encryptedAESKeyReceiverOptional.isEmpty()) {
            LOGGER.error("encryptConversation: FAiled to encrypt AES key");
            return;
        }
        conversation.setAesKeySender(encryptedAESKeySenderOptional.get());
        conversation.setAesKeyReceiver(encryptedAESKeyReceiverOptional.get());
    }

    public static Conversation createEncryptedConversation(User sender, User receiver) {
        Optional<SecretKey> secretKeyOptional = AESMethods.generateAESKey();
        if (secretKeyOptional.isEmpty()) {
            LOGGER.error("createEncryptedConversation: Failed to generate AES key");
            return null;
        }
        byte[] secretKeyBytes = secretKeyOptional.get().getEncoded();
        Conversation conversation = new Conversation.Builder()
                .sender(sender)
                .receiver(receiver)
                .aesKeySender(secretKeyBytes)
                .aesKeyReceiver(secretKeyBytes)
                .build();
        encryptConversation(conversation);
        return conversation;
    }

    public static Optional<SecretKey> decryptAESKeySender(Conversation conversation, String senderPassword) {
        PrivateKey privateKey = UserMethods.getUserPrivateKey(conversation.getSender(), senderPassword);
        Optional<byte[]> keyDataOptional = RSAMethods.decryptBytes(privateKey, conversation.getAesKeySender());
        if (keyDataOptional.isEmpty()) {
            LOGGER.error("decryptAESKeySender: Failed to decrypt conversationSenderAESKey");
            return Optional.empty();
        }
        return Optional.of(AESMethods.convertBytesToKey(keyDataOptional.get()));
    }

    public static Optional<SecretKey> decryptAESKeyReceiver(Conversation conversation, String receiverPassword) {
        PrivateKey privateKey = UserMethods.getUserPrivateKey(conversation.getReceiver(), receiverPassword);
        Optional<byte[]> keyDataOptional = RSAMethods.decryptBytes(privateKey, conversation.getAesKeyReceiver());
        if (keyDataOptional.isEmpty()) {
            LOGGER.error("decryptAESKeySender: Failed to decrypt conversationSenderAESKey");
            return Optional.empty();
        }
        return Optional.of(AESMethods.convertBytesToKey(keyDataOptional.get()));
    }
}
