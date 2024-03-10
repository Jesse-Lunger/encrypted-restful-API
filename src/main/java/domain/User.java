package domain;

public class User {

    private int userId;
    private String userName;
    private String passwordHashed;
    private String aesKeyEncrypted;
    private String publicKey;
    private String privateKeyEncrypted;

    public User(){};

    private User(Builder builder) {
        this.userId = builder.userId;
        this.userName = builder.userName;
        this.passwordHashed = builder.passwordHashed;
        this.aesKeyEncrypted = builder.aesKeyEncrypted;
        this.publicKey = builder.publicKey;
        this.privateKeyEncrypted = builder.privateKeyEncrypted;
    }

    public static class Builder {
        private int userId;
        private String userName;
        private String passwordHashed;
        private String aesKeyEncrypted;
        private String publicKey;
        private String privateKeyEncrypted;

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }
        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder passwordHashed(String passwordHashed) {
            this.passwordHashed = passwordHashed;
            return this;
        }

        public Builder aesKeyEncrypted(String aesKeyEncrypted) {
            this.aesKeyEncrypted = aesKeyEncrypted;
            return this;
        }

        public Builder publicKey(String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public Builder privateKeyEncrypted(String privateKeyEncrypted) {
            this.privateKeyEncrypted = privateKeyEncrypted;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    public String getAesKeyEncrypted() {
        return aesKeyEncrypted;
    }

    public void setAesKeyEncrypted(String aesKeyEncrypted) {
        this.aesKeyEncrypted = aesKeyEncrypted;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKeyEncrypted() {
        return privateKeyEncrypted;
    }

    public void setPrivateKeyEncrypted(String privateKeyEncrypted) {
        this.privateKeyEncrypted = privateKeyEncrypted;
    }

    @Override
    public String toString(){
        return userName;
    }
}
