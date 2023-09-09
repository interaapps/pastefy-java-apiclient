package de.interaapps.pastefy.apiclient.models;

import com.google.gson.annotations.SerializedName;
import de.interaapps.pastefy.apiclient.exceptions.EncryptionException;
import de.interaapps.pastefy.apiclient.exceptions.PasswordIncorrectException;
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
    private String id;
    private String title;
    private String content;
    private boolean encrypted;
    @SerializedName("folder")
    private String folderId;
    private String userId;
    @SerializedName("created")
    private Timestamp createdAt;

    public Paste(){
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

    public void setUserId(String id) {
        userId = id;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }


    public void encrypt(String password) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        encrypted = true;
        content = EncryptionHelper.encrypt(password, content);
        title   = EncryptionHelper.encrypt(password, title);
    }

    public boolean decrypt(String password) throws PasswordIncorrectException {
        try {
            content = EncryptionHelper.decrypt(password, content);
            title   = EncryptionHelper.decrypt(password, title);
        } catch (BadPaddingException e) {
            throw new PasswordIncorrectException();
        }
        return true;
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
}
