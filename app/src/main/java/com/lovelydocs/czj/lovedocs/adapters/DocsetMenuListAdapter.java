package com.lovelydocs.czj.lovedocs.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lovelydocs.czj.lovedocs.R;
import com.lovelydocs.czj.lovedocs.models.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.Collections;
import java.util.List;

public class DocsetMenuListAdapter extends BaseAdapter {
    private Context mContext;
    private boolean mIndexSelected;
    private List<MenuItem> mMenuItems = Collections.emptyList();
    private Resources mResources;

    class ViewHolder {

        @BindView(R.id.itemLayout)
        LinearLayout itemLayout;
        @BindView(R.id.imgIcon)

        ImageView menuItemIconImg;

        @BindView(R.id.tvTitle)
        TextView menuItemTitleTv;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public DocsetMenuListAdapter(Context context) {
        this.mContext = context;
        this.mResources = context.getResources();
    }

    public void update(List<MenuItem> menuItems, boolean isIndexSelected) {
        this.mMenuItems = menuItems;
        this.mIndexSelected = isIndexSelected;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.mMenuItems.size();
    }

    public Object getItem(int position) {
        return this.mMenuItems.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.list_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        MenuItem item = (MenuItem) getItem(position);
        if (item.getIcon() != 0) {
            holder.menuItemIconImg.setImageDrawable(this.mResources.getDrawable(item.getIcon()));
        }
        holder.menuItemTitleTv.setText(item.getTitle());
        if (position == 0 && this.mIndexSelected) {
            holder.itemLayout.setBackgroundColor(this.mContext.getResources().getColor(R.color.orange));
            holder.menuItemTitleTv.setTextColor(this.mContext.getResources().getColor(R.color.white));
        } else {
            holder.itemLayout.setBackgroundResource(R.drawable.selector_list_item);
            holder.menuItemTitleTv.setTextColor(this.mContext.getResources().getColorStateList(R.color.list_item_text));
        }
        return view;
    }
}
