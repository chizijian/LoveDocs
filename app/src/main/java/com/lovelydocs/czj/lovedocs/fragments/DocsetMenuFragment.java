package com.lovelydocs.czj.lovedocs.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import butterknife.ButterKnife;

import com.lovelydocs.czj.lovedocs.R;
import com.lovelydocs.czj.lovedocs.activities.DocsetActivity;
import com.lovelydocs.czj.lovedocs.adapters.DocsetMenuListAdapter;
import com.lovelydocs.czj.lovedocs.helpers.DocsetLookupHelper;
import com.lovelydocs.czj.lovedocs.helpers.PreferencesHelper;
import com.lovelydocs.czj.lovedocs.helpers.ReceivedDatabaseHelper;
import com.lovelydocs.czj.lovedocs.models.CondensedType;
import com.lovelydocs.czj.lovedocs.models.DocsetVersion;
import com.lovelydocs.czj.lovedocs.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class DocsetMenuFragment extends Fragment {
    public static final String KEY_CONDENSED_TYPES = "condensed_types";
    public static final String KEY_DOCSET_VERSION = "docset_version";
    public static final String KEY_INDEX_PATH = "index_path";
    public static final String KEY_INDEX_SELECTED = "index_selected";
    public static final String KEY_MENU_ITEMS = "menu_items";
    Thread fetchTypesThread = new Thread(new Runnable() {
        public void run() {
            final Activity activity = DocsetMenuFragment.this.getActivity();
            ReceivedDatabaseHelper mDatabaseHelper = new ReceivedDatabaseHelper(activity, DocsetMenuFragment.this.mDocsetVersion);
            DocsetMenuFragment.this.mIndexPath = DocsetLookupHelper.findDocsetIndex(activity, DocsetMenuFragment.this.mDocsetVersion);
            DocsetMenuFragment.this.mMenuItems.clear();
            DocsetMenuFragment.this.mMenuItems.add(new MenuItem((int) R.drawable.icon_alias_index, "Index"));
            List<CondensedType> types = mDatabaseHelper.getAllTypes();
            for (CondensedType type : types) {
                DocsetMenuFragment.this.mMenuItems.add(new MenuItem(type.getIcon(), type.getAlias()));
            }
            DocsetMenuFragment.this.mCondensedTypes = types;
            DocsetMenuFragment.this.mIndexSelected = new PreferencesHelper(activity).isLargeScreen();
            if (DocsetMenuFragment.this.mIndexSelected) {
                ((DocsetActivity) activity).placeFragment(DocsetDetailsFragment.newInstance(DocsetMenuFragment.this.mDocsetVersion.getPath() + "/" + DocsetMenuFragment.this.mDocsetVersion.getTgzName(), DocsetMenuFragment.this.mIndexPath, DocsetMenuFragment.this.mDocsetVersion), new PreferencesHelper(activity).isLargeScreen() ? R.id.placeholder2 : R.id.placeholder1, false);
            }
            if (!activity.isFinishing()) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        DocsetMenuFragment.this.mAdapter.update(DocsetMenuFragment.this.mMenuItems, DocsetMenuFragment.this.mIndexSelected);
                        ((DocsetActivity) activity).hidePreparingDocsetsLayout();
                    }
                });
            }
        }
    });
    private DocsetMenuListAdapter mAdapter;
    private List<CondensedType> mCondensedTypes;

    ListView mDocsetMenuLv;
    private DocsetVersion mDocsetVersion;
    private String mIndexPath = "";
    private boolean mIndexSelected;
    private List<MenuItem> mMenuItems;

    public static DocsetMenuFragment newInstance(DocsetVersion docsetVersion) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("docset_version", docsetVersion);
        DocsetMenuFragment fragment = new DocsetMenuFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.mAdapter = new DocsetMenuListAdapter(getActivity());
        if (savedInstanceState != null) {
            this.mDocsetVersion = (DocsetVersion) savedInstanceState.getParcelable("docset_version");
            this.mMenuItems = savedInstanceState.getParcelableArrayList(KEY_MENU_ITEMS);
            this.mIndexPath = savedInstanceState.getString(KEY_INDEX_PATH);
            this.mCondensedTypes = savedInstanceState.getParcelableArrayList(KEY_CONDENSED_TYPES);
            this.mIndexSelected = savedInstanceState.getBoolean(KEY_INDEX_SELECTED);
            return;
        }
        this.fetchTypesThread.start();
        this.mDocsetVersion = (DocsetVersion) getArguments().getParcelable("docset_version");
        this.mMenuItems = new ArrayList();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("docset_version", this.mDocsetVersion);
        outState.putParcelableArrayList(KEY_MENU_ITEMS, (ArrayList) this.mMenuItems);
        outState.putParcelableArrayList(KEY_CONDENSED_TYPES, (ArrayList) this.mCondensedTypes);
        outState.putString(KEY_INDEX_PATH, this.mIndexPath);
        outState.putBoolean(KEY_INDEX_SELECTED, this.mIndexSelected);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docset_menu, container, false);
        ButterKnife.bind(this, view);
        this.mDocsetMenuLv.setAdapter(this.mAdapter);
        this.mDocsetMenuLv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    boolean z;
                    DocsetMenuFragment.this.mIndexSelected = new PreferencesHelper(DocsetMenuFragment.this.getActivity()).isLargeScreen();
                    DocsetActivity docsetActivity = (DocsetActivity) DocsetMenuFragment.this.getActivity();
                    Fragment newInstance = DocsetDetailsFragment.newInstance(DocsetMenuFragment.this.mDocsetVersion.getPath() + "/" + DocsetMenuFragment.this.mDocsetVersion.getTgzName(), DocsetMenuFragment.this.mIndexPath, DocsetMenuFragment.this.mDocsetVersion);
                    int i = new PreferencesHelper(DocsetMenuFragment.this.getActivity()).isLargeScreen() ? R.id.placeholder2 : R.id.placeholder1;
                    if (new PreferencesHelper(DocsetMenuFragment.this.getActivity()).isLargeScreen()) {
                        z = false;
                    } else {
                        z = true;
                    }
                    docsetActivity.placeFragment(newInstance, i, z);
                    DocsetMenuFragment.this.mAdapter.update(DocsetMenuFragment.this.mMenuItems, DocsetMenuFragment.this.mIndexSelected);
                    return;
                }
                DocsetMenuFragment.this.mIndexSelected = false;
                if (DocsetMenuFragment.this.getActivity() != null && DocsetMenuFragment.this.mDocsetVersion != null && DocsetMenuFragment.this.mCondensedTypes.get(position - 1) != null) {
                    ((DocsetActivity) DocsetMenuFragment.this.getActivity()).placeFragment(DocsetSubmenuFragment.newInstance(DocsetMenuFragment.this.mDocsetVersion, (CondensedType) DocsetMenuFragment.this.mCondensedTypes.get(position - 1)), R.id.placeholder1, true);
                }
            }
        });
        return view;
    }

    public void onResume() {
        super.onResume();
        if (!this.mMenuItems.isEmpty()) {
            this.mAdapter.update(this.mMenuItems, this.mIndexSelected);
            ((DocsetActivity) getActivity()).hidePreparingDocsetsLayout();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.fetchTypesThread.isAlive()) {
            this.fetchTypesThread.interrupt();
        }
    }
}
