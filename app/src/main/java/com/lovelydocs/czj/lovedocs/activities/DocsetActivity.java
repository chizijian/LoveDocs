package com.lovelydocs.czj.lovedocs.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.lovelydocs.czj.lovedocs.R;
import com.lovelydocs.czj.lovedocs.fragments.DocsetMenuFragment;
import com.lovelydocs.czj.lovedocs.helpers.PreferencesHelper;
import com.lovelydocs.czj.lovedocs.helpers.TarixDatabaseHelper;
import com.lovelydocs.czj.lovedocs.models.DocsetVersion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;

public class DocsetActivity extends Activity {
    public static final String KEY_DOCSET_VERSION = "docset_version";
    private DocsetVersion mDocsetVersion;
    AsyncTask mExtractTarixTask;
    AsyncTask mExtractTgzTask;
    private boolean mIsLargeScreen;
    RelativeLayout mPreparingDocsLayout;
    private EditText mSearchEt;
    private boolean mSearchOpened;
    private String mSearchQuery;
    private TarixDatabaseHelper mTarixDatabaseHelper;

    private class ExtractorTarixTask extends AsyncTask<Void, Integer, Boolean> {
        File destinationFile;
        File tarixFile;

        public ExtractorTarixTask(String tarixPath, String destinationPath) {
            this.tarixFile = new File(tarixPath);
            this.destinationFile = new File(destinationPath);
        }

        protected Boolean doInBackground(Void... params) {
            try {
                try {
                    ArchiverFactory.createArchiver(ArchiveFormat.TAR, CompressionType.GZIP).extract(this.tarixFile, this.destinationFile);
                } catch (FileNotFoundException e) {
                }
                return Boolean.valueOf(true);
            } catch (IOException e2) {
                e2.printStackTrace();
                return Boolean.valueOf(false);
            }
        }

        protected void onCancelled() {
            super.onCancelled();
            this.destinationFile.delete();
        }

        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success.booleanValue()) {
                this.tarixFile.delete();
                String docsetDirectoryPath = DocsetActivity.this.mDocsetVersion.getPath();
                String docsetTgzPath = docsetDirectoryPath + File.separator + DocsetActivity.this.mDocsetVersion.getTgzName();
                DocsetActivity.this.mExtractTgzTask = new ExtractorTgzTask(docsetTgzPath, docsetDirectoryPath).execute(new Void[0]);
                return;
            }
            DocsetActivity.this.finish();
        }
    }

    private class ExtractorTgzTask extends AsyncTask<Void, Integer, Boolean> {
        File destinationFile;
        File tgzFile;

        protected java.lang.Boolean doInBackground(java.lang.Void... r15) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r14 = this;
            r10 = 0;
            r9 = com.lovelyhq.lovelydocs.activities.DocsetActivity.this;
            r9 = r9.mTarixDatabaseHelper;
            r8 = r9.getExtractionList();
            r9 = r8.size();
            if (r9 <= 0) goto L_0x00ec;
        L_0x0011:
            r6 = 0;
        L_0x0012:
            r9 = r8.size();
            if (r6 >= r9) goto L_0x00f1;
        L_0x0018:
            r7 = r8.get(r6);
            r7 = (com.lovelyhq.lovelydocs.models.TarixItem) r7;
            r9 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r9.<init>();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r10 = r14.destinationFile;	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r10 = r10.toString();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r9 = r9.append(r10);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r10 = "/";	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r9 = r9.append(r10);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r10 = r7.getPath();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r9 = r9.append(r10);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r5 = r9.toString();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r4 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r4.<init>(r5);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r9 = "/";	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r3 = r5.split(r9);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r9 = r3.length;	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r9 = r9 + -1;	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r2 = r3[r9];	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r0 = r4.getParentFile();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r0.mkdirs();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r9 = r4.exists();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            if (r9 != 0) goto L_0x005f;	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
        L_0x005c:
            r4.createNewFile();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
        L_0x005f:
            r9 = r14.tgzFile;	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r9 = r9.toString();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r10 = r7.getBlockNum();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r10 = java.lang.Integer.parseInt(r10);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r11 = r7.getBlockLength();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r11 = java.lang.Integer.parseInt(r11);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r12 = r7.getOffset();	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r12 = java.lang.Integer.parseInt(r12);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            com.lovelyhq.lovelydocs.helpers.TarixExtractHelper.writeToFile(r9, r10, r11, r12, r5);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r9 = new java.io.File;
            r10 = r14.tgzFile;
            r10 = r10.getAbsolutePath();
            r11 = ".tgz";
            r12 = ".tar";
            r10 = r10.replace(r11, r12);
            r9.<init>(r10);
            org.apache.commons.io.FileUtils.deleteQuietly(r9);
            r6 = r6 + 1;
            goto L_0x0012;
        L_0x009a:
            r1 = move-exception;
            r9 = 0;
            r9 = java.lang.Boolean.valueOf(r9);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r10 = new java.io.File;
            r11 = r14.tgzFile;
            r11 = r11.getAbsolutePath();
            r12 = ".tgz";
            r13 = ".tar";
            r11 = r11.replace(r12, r13);
            r10.<init>(r11);
            org.apache.commons.io.FileUtils.deleteQuietly(r10);
        L_0x00b6:
            return r9;
        L_0x00b7:
            r1 = move-exception;
            r9 = 0;
            r9 = java.lang.Boolean.valueOf(r9);	 Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x00b7, all -> 0x00d4 }
            r10 = new java.io.File;
            r11 = r14.tgzFile;
            r11 = r11.getAbsolutePath();
            r12 = ".tgz";
            r13 = ".tar";
            r11 = r11.replace(r12, r13);
            r10.<init>(r11);
            org.apache.commons.io.FileUtils.deleteQuietly(r10);
            goto L_0x00b6;
        L_0x00d4:
            r9 = move-exception;
            r10 = new java.io.File;
            r11 = r14.tgzFile;
            r11 = r11.getAbsolutePath();
            r12 = ".tgz";
            r13 = ".tar";
            r11 = r11.replace(r12, r13);
            r10.<init>(r11);
            org.apache.commons.io.FileUtils.deleteQuietly(r10);
            throw r9;
        L_0x00ec:
            r9 = java.lang.Boolean.valueOf(r10);
            goto L_0x00b6;
        L_0x00f1:
            r9 = 1;
            r9 = java.lang.Boolean.valueOf(r9);
            goto L_0x00b6;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lovelyhq.lovelydocs.activities.DocsetActivity.ExtractorTgzTask.doInBackground(java.lang.Void[]):java.lang.Boolean");
        }

        public ExtractorTgzTask(String tgzPath, String destinationPath) {
            this.tgzFile = new File(tgzPath);
            this.destinationFile = new File(destinationPath);
        }

        protected void onCancelled() {
            super.onCancelled();
            this.destinationFile.delete();
        }

        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success.booleanValue()) {
                DocsetActivity.this.setLayout();
            } else {
                DocsetActivity.this.finish();
            }
        }
    }

    private class SearchTextWatcher implements TextWatcher {
        private SearchTextWatcher() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            DocsetActivity.this.mSearchQuery = s.toString();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.animator.right_slide_in, R.animator.left_slide_out);
        setContentView(R.layout.activity_docset);
        this.mIsLargeScreen = findViewById(R.id.placeholder2) != null;
        new PreferencesHelper(this).setLargeScreen(this.mIsLargeScreen);
        this.mPreparingDocsLayout = (RelativeLayout) findViewById(R.id.layoutPreparingDocs);
        if (savedInstanceState == null) {
            this.mDocsetVersion = (DocsetVersion) getIntent().getParcelableExtra("docset_version");
            setLayout();
        } else {
            this.mDocsetVersion = (DocsetVersion) savedInstanceState.getParcelable("docset_version");
            this.mPreparingDocsLayout.setVisibility(View.INVISIBLE);
        }
        this.mTarixDatabaseHelper = new TarixDatabaseHelper(this, this.mDocsetVersion);
      /*  ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(this.mDocsetVersion.getDocset().getName());
            if (this.mDocsetVersion.getDocset().getActionBarIcon() != 0) {
                actionBar.setIcon(this.mDocsetVersion.getDocset().getActionBarIcon());
            }
        }*/
    }

    protected void onPause() {
        super.onPause();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(1, 0);
        overridePendingTransition(R.animator.left_slide_in, R.animator.right_slide_out);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("docset_version", this.mDocsetVersion);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("docset_version", this.mDocsetVersion);
            startActivity(intent);
            return true;
        } else if (item.getItemId() != 16908332 || this.mIsLargeScreen) {
            return super.onOptionsItemSelected(item);
        } else {
            onBackPressed();
            return true;
        }
    }

    private void openSearchBar(String searchQuery) {
        this.mSearchQuery = searchQuery;
       /* ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.search_bar);
        }*/
        /*this.mSearchEt = (EditText) (actionBar != null ? actionBar.getCustomView().findViewById(R.id.etSearch) : null);
        if (this.mSearchEt != null) {
            this.mSearchEt.setHint("Find in docset..");
            this.mSearchEt.setText(this.mSearchQuery);
            this.mSearchEt.addTextChangedListener(new SearchTextWatcher());
            this.mSearchEt.requestFocus();
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(this.mSearchEt, 0);
            this.mSearchOpened = true;
        }*/
    }

    private void closeSearchBar() {
     /*   ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.mSearchEt.getWindowToken(), 0);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(false);
        }
        this.mSearchOpened = false;*/
    }

    private void setLayout() {
        showPreparingDocsetsLayout();
        String docsetDirectoryPath = this.mDocsetVersion.getPath();
        String docsetTgzPath = docsetDirectoryPath + File.separator + this.mDocsetVersion.getTgzName();
        String docsetTarixDirectoryPath = this.mDocsetVersion.getPath();
        String docsetTarixPath = docsetDirectoryPath + File.separator + this.mDocsetVersion.getTarixName();
        if (isDocsetExtracted()) {
            placeFragment(DocsetMenuFragment.newInstance(this.mDocsetVersion), R.id.placeholder1, false);
            if (this.mSearchOpened) {
                openSearchBar(this.mSearchQuery);
                return;
            }
            return;
        }
        this.mExtractTarixTask = new ExtractorTarixTask(docsetTarixPath, docsetTarixDirectoryPath).execute(new Void[0]);
    }

    private boolean isDocsetExtracted() {
        return new File(this.mDocsetVersion.getPath() + "/" + this.mDocsetVersion.getExtractedDirectory()).exists();
    }

    private void showPreparingDocsetsLayout() {
        this.mPreparingDocsLayout.setVisibility(View.VISIBLE);
    }

    public void hidePreparingDocsetsLayout() {
        this.mPreparingDocsLayout.setVisibility(View.INVISIBLE);
    }

    public void placeFragment(Fragment fragment, int container, boolean addToBackStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (!this.mIsLargeScreen) {
            ft.setCustomAnimations(R.animator.animator_right_slide_in, R.animator.animator_left_slide_out, R.animator.animator_left_slide_in, R.animator.animator_right_slide_out);
        }
        ft.replace(container, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        if (this != null && !isFinishing()) {
            ft.commit();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mExtractTgzTask != null) {
            this.mExtractTgzTask.cancel(true);
        }
        if (this.mExtractTarixTask != null) {
            this.mExtractTarixTask.cancel(true);
        }
    }
}
