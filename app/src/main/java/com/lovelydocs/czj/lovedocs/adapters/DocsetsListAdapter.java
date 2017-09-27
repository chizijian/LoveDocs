package com.lovelydocs.czj.lovedocs.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovelydocs.czj.lovedocs.R;
import com.lovelydocs.czj.lovedocs.dialogs.AddDocsetDialog;
import com.lovelydocs.czj.lovedocs.events.StartDialogEvent;
import com.lovelydocs.czj.lovedocs.models.Docset;

import butterknife.BindView;
import butterknife.ButterKnife;

import de.greenrobot.event.EventBus;
import java.util.List;

public class DocsetsListAdapter extends BaseAdapter {
    private static final int ROTATE_DURATION = 10000;
    private static final float ROTATE_FROM = 0.0f;
    private static final float ROTATE_TO = 3600.0f;
    private boolean mAllItemsEnabled = true;
    private Context mContext;
    private List<Docset> mDocsets;
    private Resources mResources;
    private RotateAnimation rotation = new RotateAnimation(0.0f, ROTATE_TO, 1, 0.5f, 1, 0.5f);

    private class StatusButtonListener implements OnClickListener {
        private int position;

        public StatusButtonListener(int position) {
            this.position = position;
        }

        public void onClick(View view) {
            EventBus.getDefault().post(new StartDialogEvent(AddDocsetDialog.newInstance((Docset) DocsetsListAdapter.this.getItem(this.position))));
        }
    }

    class ViewHolder {

        @BindView(R.id.imgIcon)
        ImageView docsetIconImg;

        @BindView(R.id.tvTitle)
        TextView docsetNameTv;

        @BindView(R.id.btnStatus)
        ImageButton docsetStatusBtn;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public DocsetsListAdapter(List<Docset> docsets, Context context) {
        this.mDocsets = docsets;
        this.mContext = context;
        this.rotation.setRepeatCount(-1);
        mResources = this.mContext.getResources();
        this.rotation.setInterpolator(new LinearInterpolator());
        //this.rotation.setDuration(NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
    }

    public void setAllItemsEnabled(boolean isConnected) {
        this.mAllItemsEnabled = isConnected;
        notifyDataSetChanged();
    }

    public void update(List<Docset> docsets) {
        this.mDocsets = docsets;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.mDocsets.size();
    }

    public Object getItem(int position) {
        return this.mDocsets.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        int statusButtonImage;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.list_item_alt, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        Docset docset = (Docset) getItem(position);
        if (docset.getIcon() != 0) {
            holder.docsetIconImg.setVisibility(View.VISIBLE);
            holder.docsetIconImg.setImageDrawable(this.mResources.getDrawable(docset.getIcon()));
        } else {
            holder.docsetIconImg.setVisibility(View.GONE);
        }
        holder.docsetNameTv.setText(docset.getName());
        boolean isRotating = false;
        if (docset.getStatus() == 0) {
            statusButtonImage = R.drawable.icon_download;
        } else if (docset.getStatus() == 1) {
            statusButtonImage = R.drawable.icon_loading;
            isRotating = true;
        } else {
            statusButtonImage = R.drawable.icon_heart;
        }
        holder.docsetStatusBtn.setBackgroundResource(statusButtonImage);
        holder.docsetStatusBtn.setOnClickListener(new StatusButtonListener(position));
        holder.docsetStatusBtn.setEnabled(this.mAllItemsEnabled);
        if (isRotating) {
            holder.docsetStatusBtn.startAnimation(this.rotation);
        } else {
            holder.docsetStatusBtn.setAnimation(null);
        }
        return view;
    }
}
