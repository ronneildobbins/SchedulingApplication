package model;

/**
 *
 * Creates a user and saves an id and username
 *
 * @author ronneildobbins
 */
public class User {

    /**
     * the user's ID
     */
    private int userId;

    /**
     * the user's name
     */
    private String Username;

    /**
     * Constructs an user with the parameter userId and userName
     *
     * @param userId the User's Id
     * @param username the User's Name
     */
    public User(int userId, String username) {

        this.userId = userId;
        Username = username;
    }

    /**
     * Gets the user's Id
     *
     * @return the user Id as an Integer
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the user's name
     *
     * @return the username as an Integer
     */
    public String getUsername() {
        return Username;
    }


}
