package de.interaapps.pastefy.apiclient;

import de.interaapps.pastefy.apiclient.exceptions.PasswordIncorrectException;
import de.interaapps.pastefy.apiclient.model.response.ActionResponse;
import de.interaapps.pastefy.apiclient.models.ApiKey;
import de.interaapps.pastefy.apiclient.models.Notification;
import de.interaapps.pastefy.apiclient.models.Paste;
import de.interaapps.pastefy.apiclient.models.Folder;
import de.interaapps.pastefy.apiclient.models.response.UserResponse;
import org.javawebstack.httpclient.HTTPClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PastefyAPI extends HTTPClient {

    public PastefyAPI(String apiKey, String server){
        setBaseUrl(server+"/api/v2");
        header("x-auth-key", apiKey);

    }

    public PastefyAPI(String apiKey){
        this(apiKey, "https://pastefy.ga");
    }

    public PastefyAPI(){
        this(null, "https://pastefy.ga");
    }

    public Paste getPaste(String id){
        Paste paste = get("paste/"+id).object(Paste.class).setApi(this);
        return paste.exists() ? paste : null;
    }

    public Paste getPaste(String id, String password) throws PasswordIncorrectException {
        Paste paste = getPaste(id);
        if (paste == null)
            return null;
        if (!paste.decrypt(password))
            throw new PasswordIncorrectException();
        return paste;
    }

    public Folder getFolder(String id, boolean hideChildren){
        Folder folder = get("/folder/"+id+(hideChildren ? "?hide_children=true" : "")).object(Folder.class).setApi(this);
        return folder.exists() ? folder : null;
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

    public UserResponse getUser(){
        UserResponse userResponse = get("/user").object(UserResponse.class);
        return userResponse.isLoggedIn() ? userResponse : null;
    }

    public boolean createApiKey(){
        return post("/user/keys").object(ActionResponse.class).success;
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

}
