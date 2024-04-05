package utils.encryptionMethods.domain;

import domain.Conversation;
import domain.Message;
import domain.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.encryptionMethods.core.AESMethods;
import utils.encryptionMethods.core.BasicConversions;
import utils.encryptionMethods.core.HashMethods;

import javax.crypto.SecretKey;
import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.Optional;

public class MessageMethods {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static byte[] encrypt(Message message, SecretKey secretKey){
        Optional<byte[]> encryptedMessageOptional = AESMethods.encryptBytes(secretKey, message.getMessage());
        if (encryptedMessageOptional.isEmpty()){
            LOGGER.error("encrypt: Failed to encrypt message with conversation AES key");
            return null;
        }
        return  encryptedMessageOptional.get();
    }

    private static byte[] decrypt(Message message, SecretKey secretKey){
        Optional<byte[]> encryptedMessageOptional = AESMethods.decryptBytes(secretKey, message.getMessage());
        if (encryptedMessageOptional.isEmpty()){
            LOGGER.error("decrypt: Failed to decrypt message with conversation AES key");
            return null;
        }
        return encryptedMessageOptional.get();
    }

    public static void senderEncryptMessage(Message message, String senderPassword){
        Optional<SecretKey> secretKeyOptional = ConversationMethods.decryptAESKeySender(message.getConversation(), senderPassword);
        if (secretKeyOptional.isEmpty()){
            LOGGER.error("senderEncryptMessage: Failed to decrypt conversation AES key");
            return;
        }
        byte[] enMessage = encrypt(message, secretKeyOptional.get());
        message.setMessage(encrypt(message, secretKeyOptional.get()));
    }

    public static Message createEncryptedMessage(String senderPassword, Conversation conversation, MessageType messageType, String messageStr){
        Message message = new Message.Builder()
                .messageSignature(HashMethods.hashString(messageStr))
                .time( new Timestamp(System.currentTimeMillis()))
                .conversation(conversation)
                .messageType(messageType)
                .message(BasicConversions.stringToBytes(messageStr))
                .build();
        senderEncryptMessage(message, senderPassword);
        return message;
    }

    public static String senderDecryptMessage(Message message, String senderPassword){
        Optional<SecretKey> secretKeyOptional = ConversationMethods.decryptAESKeySender(message.getConversation(), senderPassword);
        if (secretKeyOptional.isEmpty()){
            LOGGER.error("receiverEncryptMessage: Failed to decrypt conversation AES key");
            return null;
        }
        String strMessage = BasicConversions.bytesToString(decrypt(message, secretKeyOptional.get()));
        if (!HashMethods.isHashMatch(strMessage, message.getMessageSignature())){
            LOGGER.error("senderDecryptMessage: Failed to validate message signature");
            return null;
        }
        return strMessage;
    }

    public static void receiverEncryptMessage(Message message, String receiverPassword){
        Optional<SecretKey> secretKeyOptional = ConversationMethods.decryptAESKeyReceiver(message.getConversation(), receiverPassword);
        if (secretKeyOptional.isEmpty()){
            LOGGER.error("receiverEncryptMessage: Failed to decrypt conversation AES key");
            return;
        }
        message.setMessage(encrypt(message, secretKeyOptional.get()));
    }

    public static String receiverDecryptMessage(Message message, String receiverPassword){
        Optional<SecretKey> secretKeyOptional = ConversationMethods.decryptAESKeyReceiver(message.getConversation(), receiverPassword);
        if (secretKeyOptional.isEmpty()){
            LOGGER.error("receiverEncryptMessage: Failed to decrypt conversation AES key");
            return null;
        }
        return BasicConversions.bytesToString(decrypt(message, secretKeyOptional.get()));
    }
}
