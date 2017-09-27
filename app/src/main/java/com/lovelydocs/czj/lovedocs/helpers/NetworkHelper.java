package com.lovelydocs.czj.lovedocs.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelper {
    public static boolean isOnline(Context context) {
        NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isWifiEnabled(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1).isConnected();
    }

    public static boolean isMobileEnabled(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(0).isConnected();
    }
}
