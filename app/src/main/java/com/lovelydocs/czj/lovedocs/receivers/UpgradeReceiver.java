package com.lovelydocs.czj.lovedocs.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import com.lovelydocs.czj.lovedocs.helpers.DocsetsDatabaseHelper;
import com.lovelydocs.czj.lovedocs.helpers.InitialDocsetsGenerator;

public class UpgradeReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        DocsetsDatabaseHelper.updateIconsOfDocsets(InitialDocsetsGenerator.getFullList(), SQLiteDatabase.openDatabase(context.getApplicationContext().getDatabasePath("DocsetsDatabase").getAbsolutePath(), null, 0));
    }
}
