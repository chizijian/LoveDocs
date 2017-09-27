package com.lovelydocs.czj.lovedocs.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.lovelydocs.czj.lovedocs.helpers.Downloader;
import com.lovelydocs.czj.lovedocs.models.Docset;
import java.util.List;

public class DownloadService extends Service {
    public static final String ARG_DOCSET = "docset";
    public static final String ARG_LATEST_VERSION = "latest_version";
    public static final String ARG_MOBILE_ENABLED = "mobile_enabled";
    public static final String ARG_SERVERS = "servers";
    public static final String ARG_VERSION = "version";
    private Downloader mDownloader;

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Docset docset = (Docset) intent.getParcelableExtra("docset");
            String version = intent.getStringExtra(ARG_VERSION);
            boolean isLatest = intent.getBooleanExtra(ARG_LATEST_VERSION, true);
            List<String> servers = intent.getStringArrayListExtra("servers");
            this.mDownloader = new Downloader(this, docset, version, isLatest, (String) servers.get(0), intent.getBooleanExtra(ARG_MOBILE_ENABLED, true));
            this.mDownloader.downloadDocumentation();
        }
        return 1;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
