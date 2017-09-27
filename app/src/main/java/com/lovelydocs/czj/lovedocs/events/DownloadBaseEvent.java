package com.lovelydocs.czj.lovedocs.events;


import com.lovelydocs.czj.lovedocs.models.DocsetVersion;

public class DownloadBaseEvent {
    private DocsetVersion docsetVersion;

    public DownloadBaseEvent(DocsetVersion docsetVersion) {
        this.docsetVersion = docsetVersion;
    }

    public DocsetVersion getDocsetVersion() {
        return this.docsetVersion;
    }

    public void setDocsetVersion(DocsetVersion docsetVersion) {
        this.docsetVersion = docsetVersion;
    }
}
