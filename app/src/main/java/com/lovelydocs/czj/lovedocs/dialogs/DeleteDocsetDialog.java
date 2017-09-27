package com.lovelydocs.czj.lovedocs.dialogs;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

import com.lovelydocs.czj.lovedocs.R;
import com.lovelydocs.czj.lovedocs.models.DocsetVersion;
import com.lovelydocs.czj.lovedocs.services.DeleteService;

public class DeleteDocsetDialog extends DialogFragment {
    public static final String KEY_DOCSET_VERSION = "docset_version";
    private DocsetVersion mDocsetVersion;

    private class NegativeButtonListener implements OnClickListener {
        private NegativeButtonListener() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    private class PositiveButtonListener implements OnClickListener {
        private PositiveButtonListener() {
        }

        public void onClick(DialogInterface dialogInterface, int id) {
            Intent i = new Intent(DeleteDocsetDialog.this.getActivity(), DeleteService.class);
            i.putExtra("docset_version", DeleteDocsetDialog.this.mDocsetVersion);
            DeleteDocsetDialog.this.getActivity().startService(i);
        }
    }

    public static DialogFragment newInstance(DocsetVersion docsetVersion) {
        DeleteDocsetDialog df = new DeleteDocsetDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("docset_version", docsetVersion);
        df.setArguments(bundle);
        return df;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.mDocsetVersion = (DocsetVersion) savedInstanceState.getParcelable("docset_version");
        } else {
            this.mDocsetVersion = (DocsetVersion) getArguments().getParcelable("docset_version");
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("docset_version", this.mDocsetVersion);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        builder.setTitle(this.mDocsetVersion.getDocset().getName() + " " + this.mDocsetVersion.getVersion()).setMessage(R.string.dialog_delete_version).setPositiveButton(R.string.dialog_yes, new PositiveButtonListener()).setNegativeButton(R.string.dialog_no, new NegativeButtonListener());
        return builder.create();
    }
}
