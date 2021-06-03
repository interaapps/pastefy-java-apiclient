package de.interaapps.pastefy.apiclient.models;

import com.google.gson.annotations.SerializedName;
import de.interaapps.pastefy.apiclient.helper.EncryptionHelper;
import de.interaapps.pastefy.apiclient.model.response.ActionResponse;
import de.interaapps.pastefy.apiclient.PastefyAPI;
import de.interaapps.pastefy.apiclient.models.response.CreatePasteResponse;
import org.javawebstack.abstractdata.AbstractObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

public class Paste {
    private boolean exists;

    private String id;
    private String title;
    private String content;
    private boolean encrypted;
    @SerializedName("folder")
    private String folderId;
    private int userId;
    @SerializedName("created")
    private Timestamp createdAt;

    transient private PastefyAPI api;

    public Paste(PastefyAPI api){
        this.api = api;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public int getUserId() {
        return userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public boolean create(){
        CreatePasteResponse response = api.post("/paste", this).object(CreatePasteResponse.class);
        if (response.success) {
            exists = true;
            id = response.paste.id;
            userId = response.paste.userId;
            createdAt = response.paste.createdAt;
        }
        return response.success;
    }

    public boolean create(String password) {
        try {
            encrypt(password);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return create();
    }

    public void encrypt(String password) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        encrypted = true;
        content = EncryptionHelper.encrypt(password, content);
        title   = EncryptionHelper.encrypt(password, title);
    }

    public boolean addFriend(String name){
        return api.post("/paste/"+id+"/friend", new AbstractObject().set("friend", name)).object(ActionResponse.class).success;
    }

    public boolean decrypt(String password) {
        try {
            content = EncryptionHelper.decrypt(password, content);
            title   = EncryptionHelper.decrypt(password, title);
        } catch (BadPaddingException e) {
            return false;
        }
        return true;
    }

    public boolean delete(){
        return api.deletePaste(id);
    }

    public Folder getFolder(){
        return api.getFolder(folderId);
    }

    public PastefyAPI getApi() {
        return api;
    }

    public Paste setApi(PastefyAPI api) {
        this.api = api;
        return this;
    }

    public Paste setTitle(String title) {
        this.title = title;
        return this;
    }

    public Paste setContent(String content) {
        this.content = content;
        return this;
    }

    public Paste setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
        return this;
    }

    public String getFolderId() {
        return folderId;
    }

    public Paste setFolderId(String folderId) {
        this.folderId = folderId;
        return this;
    }

    public boolean exists(){
        return exists;
    }
}
