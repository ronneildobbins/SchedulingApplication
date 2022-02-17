package model;

public class User {

    private int userId;

    private String Username;

    public User(int userId, String username) {

        this.userId = userId;
        Username = username;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
