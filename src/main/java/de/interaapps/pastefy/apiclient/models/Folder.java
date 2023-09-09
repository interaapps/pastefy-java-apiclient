package de.interaapps.pastefy.apiclient.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import de.interaapps.pastefy.apiclient.PastefyAPI;
import de.interaapps.pastefy.apiclient.models.response.CreateFolderResponse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Folder {
    private boolean exists;

    private String id;
    private String name;
    private List<Paste> pastes = new ArrayList<>();
    private List<Folder> children = new ArrayList<>();
    @SerializedName("parent")
    private String parentId;
    private int userId;
    @SerializedName("created")
    private Timestamp createdAt;

    public String getName() {
        return name;
    }

    public Folder setName(String name) {
        this.name = name;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public List<Folder> getChildren() {
        return children;
    }

    public List<Paste> getPastes() {
        return pastes;
    }


    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean exists(){
        return exists;
    }

}
