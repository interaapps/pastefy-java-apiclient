package de.interaapps.pastefy.apiclient.models.response;

import de.interaapps.pastefy.apiclient.models.Folder;
import de.interaapps.pastefy.apiclient.models.Paste;

import java.util.List;

public class OverviewResponse {
    private List<Paste> pastes;
    private List<Folder> folder;

    public List<Folder> getFolders() {
        return folder;
    }

    public List<Paste> getPastes() {
        return pastes;
    }
}
