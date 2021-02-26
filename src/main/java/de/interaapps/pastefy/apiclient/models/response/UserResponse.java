package de.interaapps.pastefy.apiclient.models.response;

public class UserResponse {
    private int id;
    private boolean loggedIn;
    private String color;
    private String name;
    private String profilePicture;
    private String authType;

    public int getId() {
        return id;
    }

    public String getAuthType() {
        return authType;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
