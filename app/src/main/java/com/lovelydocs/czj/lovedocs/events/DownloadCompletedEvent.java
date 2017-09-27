package com.lovelydocs.czj.lovedocs.events;


import com.lovelydocs.czj.lovedocs.models.DocsetVersion;

public class DownloadCompletedEvent extends DownloadBaseEvent {
    public DownloadCompletedEvent(DocsetVersion docsetVersion) {
        super(docsetVersion);
    }
}
