package com.lovelydocs.czj.lovedocs.events;

public class StartActivityEvent {
    private Activity activity;

    public enum Activity {
        DOCSETS,
        DOCSET,
        PREMIUM,
        SETTINGS
    }

    public StartActivityEvent(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
