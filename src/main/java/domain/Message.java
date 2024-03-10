package domain;

import java.sql.Timestamp;

public class Message {

    private int messageId;
    private Conversation conversation;
    private MessageType messageType;
    private String messageSender;
    private String messageReceiver;
    private String messageSignature;
    private String aesKey;
    private Timestamp time;

    public Message(){}

    private Message(Builder builder) {
        this.messageId = builder.messageId;
        this.conversation = builder.conversation;
        this.messageType = builder.messageType;
        this.messageSender = builder.messageSender;
        this.messageReceiver = builder.messageReceiver;
        this.messageSignature = builder.messageSignature;
        this.aesKey = builder.aesKey;
        this.time = builder.time;
    }

    public static class Builder {
        private int messageId;
        private Conversation conversation;
        private MessageType messageType;
        private String messageSender;
        private String messageReceiver;
        private String messageSignature;
        private String aesKey;
        private Timestamp time;

        public Builder messageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder conversation(Conversation conversation) {
            this.conversation = conversation;
            return this;
        }

        public Builder messageType(MessageType messageType) {
            this.messageType = messageType;
            return this;
        }

        public Builder messageSender(String messageSender) {
            this.messageSender = messageSender;
            return this;
        }

        public Builder messageReceiver(String messageReceiver) {
            this.messageReceiver = messageReceiver;
            return this;
        }

        public Builder messageSignature(String messageSignature) {
            this.messageSignature = messageSignature;
            return this;
        }

        public Builder aesKey(String aesKey) {
            this.aesKey = aesKey;
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

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageReceiver() {
        return messageReceiver;
    }

    public void setMessageReceiver(String messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public String getMessageSignature() {
        return messageSignature;
    }

    public void setMessageSignature(String messageSignature) {
        this.messageSignature = messageSignature;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
