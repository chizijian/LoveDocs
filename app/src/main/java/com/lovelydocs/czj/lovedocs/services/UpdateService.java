package com.lovelydocs.czj.lovedocs.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.lovelydocs.czj.lovedocs.helpers.Updater;
import com.lovelydocs.czj.lovedocs.models.DocsetVersion;

public class UpdateService extends Service {
    public static final String ARG_DOCSET_VERSION = "docset_version";

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            new Updater(this, (DocsetVersion) intent.getParcelableExtra("docset_version")).updateDocset();
        }
        return 1;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
