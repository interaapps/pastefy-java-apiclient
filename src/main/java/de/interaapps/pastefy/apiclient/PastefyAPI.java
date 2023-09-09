package de.interaapps.pastefy.apiclient;

import de.interaapps.pastefy.apiclient.exceptions.CreationFailedException;
import de.interaapps.pastefy.apiclient.exceptions.EncryptionException;
import de.interaapps.pastefy.apiclient.exceptions.NotFoundException;
import de.interaapps.pastefy.apiclient.exceptions.PasswordIncorrectException;
import de.interaapps.pastefy.apiclient.model.response.ActionResponse;
import de.interaapps.pastefy.apiclient.models.ApiKey;
import de.interaapps.pastefy.apiclient.models.Notification;
import de.interaapps.pastefy.apiclient.models.Paste;
import de.interaapps.pastefy.apiclient.models.Folder;
import de.interaapps.pastefy.apiclient.models.response.CreateFolderResponse;
import de.interaapps.pastefy.apiclient.models.response.CreatePasteResponse;
import de.interaapps.pastefy.apiclient.models.response.OverviewResponse;
import de.interaapps.pastefy.apiclient.models.response.UserResponse;
import org.javawebstack.abstractdata.AbstractObject;
import org.javawebstack.httpclient.HTTPClient;
import org.javawebstack.httpclient.HTTPRequest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PastefyAPI extends HTTPClient {

    public PastefyAPI(String apiKey, String server){
        setBaseUrl(server+"/api/v2");
        bearer(apiKey);
    }

    public PastefyAPI(String apiKey){
        this(apiKey, "https://pastefy.app");
    }

    public PastefyAPI(){
        this(null, "https://pastefy.app");
    }

    public Paste getPaste(String id) {
        HTTPRequest httpRequest = get("paste/" + id);
        return httpRequest.status() != 200 ? null : httpRequest.object(Paste.class);
    }

    public Paste getPaste(String id, String password) throws PasswordIncorrectException {
        Paste paste = getPaste(id);
        if (paste == null)
            return null;
        if (!paste.decrypt(password))
            throw new PasswordIncorrectException();
        return paste;
    }

    public void createPaste(Paste paste) throws CreationFailedException {
        CreatePasteResponse response = post("/paste", paste).object(CreatePasteResponse.class);

        if (!response.success)
            throw new CreationFailedException();

        paste.setId(response.paste.getId());
        paste.setUserId(response.paste.getUserId());
        paste.setCreatedAt(response.paste.getCreatedAt());
    }

    public void createPaste(Paste paste, String password) throws EncryptionException, CreationFailedException {
        try {
            paste.encrypt(password);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new EncryptionException();
        }
        createPaste(paste);
    }

    public void deletePaste(Paste paste){
        delete("/paste/"+paste.getId());
    }

    public void addFriendToPaste(Paste paste, String friendName){
        post("/paste/"+paste.getId()+"/friend", new AbstractObject().set("friend", friendName)).object(ActionResponse.class);
    }

    public Folder getFolder(String id, boolean hideChildren){
        HTTPRequest httpRequest = get("/folder/" + id + (hideChildren ? "?hide_children=true" : ""));
        return httpRequest.status() != 200 ? null : httpRequest.object(Folder.class);
    }

    public Folder getFolder(String id){
        return getFolder(id, false);
    }

    public boolean deletePaste(String id){
        return delete("/paste/"+id).object(ActionResponse.class).success;
    }

    public boolean deleteFolder(String id){
        return delete("/folder/"+id).object(ActionResponse.class).success;
    }

    public void createFolder(Folder folder) throws CreationFailedException {
        CreateFolderResponse response = post("/folder", folder).object(CreateFolderResponse.class);
        if (!response.success)
            throw new CreationFailedException();

        folder.setId (response.folder.getId());
        folder.setUserId(response.folder.getUserId());
        folder.setCreatedAt(response.folder.getCreatedAt());
    }

    public UserResponse getUser(){
        UserResponse userResponse = get("/user").object(UserResponse.class);
        return userResponse.isLoggedIn() ? userResponse : null;
    }

    public OverviewResponse getOverview(int page){
        OverviewResponse response = get("/user/overview").query("page", String.valueOf(page)).query("hide_children", "true").object(OverviewResponse.class);
        return response;
    }

    public OverviewResponse getOverview(){
        return getOverview(1);
    }

    public String createApiKey(){
        return post("/user/keys").object(AbstractObject.class).get("key").string();
    }

    public boolean deleteApiKey(String apiKey){
        return delete("/user/keys/"+apiKey).object(ActionResponse.class).success;
    }

    public List<ApiKey> getApiKeys(){
        try {
            return get("/user/keys").data().array().stream().map(entry -> new ApiKey(entry.string())).collect(Collectors.toList());
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }

    public List<Notification> getNotifications(){
        try {
            return Arrays.asList(get("/user/notification").object(Notification[].class).clone());
        } catch (IllegalStateException e) {
            return new ArrayList<>();
        }
    }

    public void deleteFolder(Folder folder) {
        deleteFolder(folder.getId());
    }
}
