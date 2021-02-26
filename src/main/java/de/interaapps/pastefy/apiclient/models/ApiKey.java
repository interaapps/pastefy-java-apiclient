package de.interaapps.pastefy.apiclient.models;

import de.interaapps.pastefy.apiclient.PastefyAPI;
import org.javawebstack.abstractdata.AbstractElement;

public class ApiKey {
    private String apiKey;
    private PastefyAPI api;


    public String getApiKey() {
        return apiKey;
    }

    public ApiKey(String apiKey) {

        this.apiKey = apiKey;
    }

    public ApiKey setApi(PastefyAPI api) {
        this.api = api;
        return this;
    }

    public PastefyAPI createAPIClient(){
        return new PastefyAPI(api.getBaseUrl(), apiKey);
    }
}
