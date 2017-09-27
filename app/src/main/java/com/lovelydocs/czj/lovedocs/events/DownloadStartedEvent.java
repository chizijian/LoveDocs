package com.lovelydocs.czj.lovedocs.events;


import com.lovelydocs.czj.lovedocs.models.DocsetVersion;

public class DownloadStartedEvent extends DownloadBaseEvent {
    public DownloadStartedEvent(DocsetVersion docsetVersion) {
        super(docsetVersion);
    }
}
