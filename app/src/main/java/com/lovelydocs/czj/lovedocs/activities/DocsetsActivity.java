package com.lovelydocs.czj.lovedocs.activities;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;

import com.lovelydocs.czj.lovedocs.R;
import com.lovelydocs.czj.lovedocs.fragments.DocsetsFragment;

public class DocsetsActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.bottom_slide_in, R.animator.top_slide_out);
        setContentView(R.layout.activity_docsets);
        setActionBar();
        if (savedInstanceState == null) {
            placeFragment(new DocsetsFragment(), R.id.placeholder1);
        }
    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.animator.top_slide_in, R.animator.bottom_slide_out);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

    private void setActionBar() {
      /*  ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Docsets");
        }*/
    }

    private void placeFragment(Fragment fragment, int container) {
        getFragmentManager().beginTransaction().replace(container, fragment).commit();
    }
}
