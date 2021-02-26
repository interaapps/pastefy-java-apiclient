package de.interaapps.pastefy.apiclient.models;

public class Notification {
    private int id;
    private String message;
    private int userId;
    private String url;
    private boolean alreadyRead;
    private boolean received;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public boolean isAlreadyRead() {
        return alreadyRead;
    }

    public boolean isReceived() {
        return received;
    }
}
