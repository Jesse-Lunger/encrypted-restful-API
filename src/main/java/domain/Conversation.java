package domain;

public class Conversation {

    private int conversationId;
    private User sender;
    private User receiver;

    public Conversation(){}

    private Conversation(Builder builder){
        this.conversationId = builder.conversationId;
        this.sender = builder.sender;
        this.receiver = builder.receiver;
    }

    public static class Builder{
        private int conversationId;
        private User sender;
        private User receiver;

        public Builder conversationId(int conversationId){
            this.conversationId = conversationId;
            return this;
        }

        public Builder sender(User sender){
            this.sender = sender;
            return this;
        }

        public Builder receiver(User receiver){
            this.receiver = receiver;
            return this;
        }

        public Conversation build(){
            return new Conversation(this);
        }
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

    @Override
    public String toString(){
        return "conversationId: " + conversationId + ", sender: " + sender + ", receiver: " + receiver;
    }
}
