import domain.Conversation;
import domain.Message;
import domain.MessageType;
import domain.User;
import utils.encryptionMethods.domain.ConversationMethods;
import utils.encryptionMethods.domain.MessageMethods;
import utils.encryptionMethods.domain.UserMethods;

public class Main {


    public static void main(String[] args) {

        String password1 = "password1";
        String password2 = "password2";

        User user1 = UserMethods.createEncyptedUser("jake", password1);
        User user2 = UserMethods.createEncyptedUser("bill", password2);

        MessageType messageType = new MessageType.Builder()
                .messageTypeName("private message")
                .build();

        Conversation conversation = ConversationMethods.createEncryptedConversation(user1, user2);

        Message message = MessageMethods.createEncryptedMessage(password1, conversation, messageType, "hello this is a secret message");

        System.out.println(MessageMethods.senderDecryptMessage(message, password1));
        System.out.println(MessageMethods.receiverDecryptMessage(message, password2));

    }
}
