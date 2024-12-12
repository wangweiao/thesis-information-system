package util;

public class UserSession {
    private static UserSession instance;
    private String username;

    // Private constructor prevents instantiation
    private UserSession() {}

    // Method to get the singleton instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Set the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Get the username
    public String getUsername() {
        return this.username;
    }
}

