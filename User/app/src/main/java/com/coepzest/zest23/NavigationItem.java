package com.coepzest.zest23;

import android.view.ViewGroup;

public abstract class NavigationItem <T extends NavigationAdapter.ViewHolder> {

    protected boolean isChecked;

    public abstract T createViewHolder(ViewGroup parent);

    public abstract void bindViewHolder(T holder);

    public NavigationItem<T> setChecked(boolean isChecked){
        this.isChecked = isChecked;
        return this;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean isSelectable() {
        return true;
    }
}
