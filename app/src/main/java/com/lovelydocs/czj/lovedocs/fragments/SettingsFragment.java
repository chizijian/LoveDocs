package com.lovelydocs.czj.lovedocs.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.lovelydocs.czj.lovedocs.R;
import com.lovelydocs.czj.lovedocs.helpers.PreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingsFragment extends Fragment {

    @BindView(R.id.checkBox1)
    CheckBox mCheckbox1;

    @BindView(R.id.checkBox2)
    CheckBox mCheckbox2;
    private PreferencesHelper mPrefsHelper;

   @BindView(R.id.tvSubtitle1)
    TextView mSubtitle1;
    @BindView(R.id.tvSubtitle2)

    TextView mSubtitle2;
    //private Tracker t;

    private class CheckBoxListener implements OnCheckedChangeListener {
        private CheckBoxListener() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            String enabled;
            if (b) {
                enabled = "true";
            } else {
                enabled = "false";
            }
            switch (compoundButton.getId()) {
                case R.id.checkBox1:
                    SettingsFragment.this.mSubtitle1.setEnabled(b);
                    SettingsFragment.this.mPrefsHelper.setMobileEnabled(b);
                    return;
                case R.id.checkBox2:
                    SettingsFragment.this.mSubtitle2.setEnabled(b);
                    SettingsFragment.this.mPrefsHelper.setUpdatesEnabled(b);
                    return;
                default:
                    return;
            }
        }
    }

    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        this.mPrefsHelper = new PreferencesHelper(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        CheckBoxListener l = new CheckBoxListener();
        this.mCheckbox1.setChecked(this.mPrefsHelper.isMobileEnabled());
        this.mCheckbox1.setOnCheckedChangeListener(l);
        this.mSubtitle1.setEnabled(this.mCheckbox1.isChecked());
        this.mCheckbox2.setChecked(this.mPrefsHelper.isUpdatesEnabled());
        this.mCheckbox2.setOnCheckedChangeListener(l);
        this.mSubtitle2.setEnabled(this.mCheckbox2.isChecked());
        return view;
    }
}
