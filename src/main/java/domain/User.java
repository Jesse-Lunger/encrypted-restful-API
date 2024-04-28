package domain;

public class User {

    private int userId;
    private String userName;
    private String password;
    private byte[] publicKey;
    private byte[] privateKey;

    public User() {}

    private User(Builder builder) {
        this.userId = builder.userId;
        this.userName = builder.userName;
        this.password = builder.password;
        this.publicKey = builder.publicKey;
        this.privateKey = builder.privateKey;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }

    public static class Builder {
        private int userId;
        private String userName;
        private String password;
        private byte[] publicKey;
        private byte[] privateKey;

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder publicKey(byte[] publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public Builder privateKeyEncrypted(byte[] privateKeyEncrypted) {
            this.privateKey = privateKeyEncrypted;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "(" + "UserName: " + userName + ", UserId: " + userId + ")";
    }
}
