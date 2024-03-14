package domain;

public class MessageType {

    private int messageTypeId;
    private String messageTypeName;

    public MessageType(){}

    private MessageType(Builder builder){
        this.messageTypeId = builder.messageTypeId;
        this.messageTypeName = builder.messageTypeName;
    }

    public static class Builder{
        private int messageTypeId;
        private String messageTypeName;

        public Builder messageTypeId(int messageTypeId){
            this.messageTypeId = messageTypeId;
            return this;
        }

        public Builder messageTypeName(String messageTypeName){
            this.messageTypeName = messageTypeName;
            return this;
        }

        public MessageType build(){
            return new MessageType(this);
        }
    }

    public int getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(int messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    public String getMessageTypeName() {
        return messageTypeName;
    }

    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName;
    }

    @Override
    public String toString(){
        return "(Message Type: " + messageTypeName + ", id: " + messageTypeId + ")";
    }
}
