package domain;

public class Conversation {

    private int conversationId;
    private User sender;
    private User receiver;
    private byte[] aesKeySender;
    private byte[] aesKeyReceiver;

    public Conversation() {
    }

    private Conversation(Builder builder) {
        this.sender = builder.sender;
        this.receiver = builder.receiver;
        this.aesKeyReceiver = builder.aesKeySender;
        this.aesKeySender = builder.aesKeySender;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public byte[] getAesKeySender() {
        return aesKeySender;
    }

    public void setAesKeySender(byte[] aesKeySender) {
        this.aesKeySender = aesKeySender;
    }

    public byte[] getAesKeyReceiver() {
        return aesKeyReceiver;
    }

    public void setAesKeyReceiver(byte[] aesKeyReceiver) {
        this.aesKeyReceiver = aesKeyReceiver;
    }

    @Override
    public String toString() {
        return "(conversationId: " + conversationId + ", sender: " + sender + ", receiver: " + receiver + ")";
    }

    public static class Builder {

        private User sender;
        private User receiver;
        private byte[] aesKeySender;
        private byte[] aesKeyReceiver;

        public Builder sender(User sender) {
            this.sender = sender;
            return this;
        }

        public Builder receiver(User receiver) {
            this.receiver = receiver;
            return this;
        }

        public Builder aesKeySender(byte[] aesKeySender) {
            this.aesKeySender = aesKeySender;
            return this;
        }

        public Builder aesKeyReceiver(byte[] aesKeyReceiver) {
            this.aesKeyReceiver = aesKeyReceiver;
            return this;
        }

        public Conversation build() {
            return new Conversation(this);
        }
    }
}
