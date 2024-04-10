package com.coepzest.zest23;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coepzest.zest23.R;

public class NavigationSimpleItem extends NavigationItem<NavigationSimpleItem.ViewHolder> {

    private int selectedItemIconTint;
    private int selectedItemTextTint;

    private int normalItemIconTint;
    private int normalItemTextTint;

    private Drawable icon;
    private String title;

    public NavigationSimpleItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.navigation_item_option,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {
        holder.title.setText(title);
        holder.icon.setImageDrawable(icon);

        holder.title.setTextColor(isChecked ? selectedItemTextTint : normalItemTextTint);
        holder.icon.setColorFilter(isChecked ? selectedItemIconTint : normalItemIconTint);
    }

    public NavigationSimpleItem withSelectedIconTint(int selectedItemIconTint) {
        this.selectedItemIconTint = selectedItemIconTint;
        return this;
    }

    public NavigationSimpleItem withSelectedTextTint(int selectedItemTextTint) {
        this.selectedItemTextTint = selectedItemTextTint;
        return this;
    }

    public NavigationSimpleItem withIconTint(int normalItemIconTint) {
        this.normalItemIconTint = normalItemIconTint;
        return this;
    }

    public NavigationSimpleItem withTextTint(int normalItemTextTint) {
        this.normalItemTextTint = normalItemTextTint;
        return this;
    }


    static class  ViewHolder extends NavigationAdapter.ViewHolder{

        private ImageView icon;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
