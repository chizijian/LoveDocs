package com.lovelydocs.czj.lovedocs.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.lovelydocs.czj.lovedocs.R;
import com.lovelydocs.czj.lovedocs.fragments.SearchResultsFragment;
import com.lovelydocs.czj.lovedocs.models.DocsetVersion;

import butterknife.ButterKnife;


public class SearchActivity extends Activity {
    public static final String DOCSET_VERSION = "docset_version";
    private DocsetVersion mDocsetVersion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.right_slide_in, R.animator.left_slide_out);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            this.mDocsetVersion = (DocsetVersion) savedInstanceState.getParcelable("docset_version");
        } else {
            this.mDocsetVersion = (DocsetVersion) getIntent().getParcelableExtra("docset_version");
        }
        placeFragment(SearchResultsFragment.newInstance(this.mDocsetVersion), R.id.placeholder1, false);
    }

    protected void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("docset_version", this.mDocsetVersion);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.animator.left_slide_in, R.animator.right_slide_out);
    }

    public void placeFragment(Fragment fragment, int container, boolean addToBackStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(container, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}
