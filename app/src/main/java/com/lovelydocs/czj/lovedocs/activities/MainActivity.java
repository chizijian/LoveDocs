package com.lovelydocs.czj.lovedocs.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import com.lovelydocs.czj.lovedocs.R;
import com.lovelydocs.czj.lovedocs.events.StartActivityEvent;
import com.lovelydocs.czj.lovedocs.fragments.MainFragment;
import com.lovelydocs.czj.lovedocs.helpers.PreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class MainActivity extends Activity {
    PreferencesHelper mHelper;

    @BindView(R.id.preparingLayout)
    RelativeLayout mPreparingLayout;
    Thread prepareThread = new Thread(new Runnable() {
        public void run() {
            MainActivity.this.mHelper.clearLegacyPreferences();
            File oldDir = MainActivity.this.getCacheDir().getParentFile();
            if (oldDir.exists()) {
                try {
                    FileUtils.cleanDirectory(oldDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            MainActivity.this.mHelper.setFirstRun(false);
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    MainActivity.this.setLayout();
                }
            });
        }
    });

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            setLayout();
        }
        EventBus.getDefault().register(this);
    }

    private void setLayout() {
        this.mHelper = new PreferencesHelper(this);
        if (this.mHelper.isFirstRun()) {
            this.mPreparingLayout.setVisibility(View.VISIBLE);
            this.prepareThread.start();
            return;
        }
        this.mPreparingLayout.setVisibility(View.INVISIBLE);
        placeFragment(new MainFragment(), R.id.placeholder1);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.prepareThread.interrupt();
        EventBus.getDefault().unregister(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void placeFragment(Fragment fragment, int container) {
        getFragmentManager().beginTransaction().replace(container, fragment).commitAllowingStateLoss();
    }

    public void onEvent(StartActivityEvent event) {
        Intent intent;
        switch (event.getActivity()) {
            case DOCSET:
                intent = new Intent(this, DocsetActivity.class);
                break;
            case DOCSETS:
                intent = new Intent(this, DocsetsActivity.class);
                break;
            case SETTINGS:
                intent = new Intent(this, SettingsActivity.class);
                break;
            default:
                intent = new Intent();
                break;
        }
        startActivity(intent);
    }
}
