package com.lovelydocs.czj.lovedocs.events;


import com.lovelydocs.czj.lovedocs.models.DocsetVersion;

public class DownloadFailedEvent extends DownloadBaseEvent {
    public DownloadFailedEvent(DocsetVersion docsetVersion) {
        super(docsetVersion);
    }
}
