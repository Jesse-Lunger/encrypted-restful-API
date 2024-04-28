package domain;

import java.sql.Timestamp;

public class Message {

    private int messageId;
    private Conversation conversation;
    private MessageType messageType;
    private byte[] message;
    private String messageSignature;
    private Timestamp time;

    public Message() {
    }

    private Message(Builder builder) {
        this.conversation = builder.conversation;
        this.messageType = builder.messageType;
        this.message = builder.message;
        this.messageSignature = builder.messageSignature;
        this.time = builder.time;
    }

    // Getters and setters
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public String getMessageSignature() {
        return messageSignature;
    }

    public void setMessageSignature(String messageSignature) {
        this.messageSignature = messageSignature;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "(messageID= " + messageId + ", " + conversation + ")";
    }

    public static class Builder {
        private Conversation conversation;
        private MessageType messageType;
        private byte[] message;
        private String messageSignature;
        private Timestamp time;

        public Builder conversation(Conversation conversation) {
            this.conversation = conversation;
            return this;
        }

        public Builder messageType(MessageType messageType) {
            this.messageType = messageType;
            return this;
        }

        public Builder message(byte[] message) {
            this.message = message;
            return this;
        }

        public Builder messageSignature(String messageSignature) {
            this.messageSignature = messageSignature;
            return this;
        }


        public Builder time(Timestamp time) {
            this.time = time;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
