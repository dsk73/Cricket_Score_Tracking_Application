package com.coepzest.zest23;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private List<NavigationItem> items;
    private Map<Class<? extends NavigationItem>, Integer> viewTypes;
    private SparseArray<NavigationItem> holderFactories;

    private OnItemSelectedListener listener;

    public NavigationAdapter(List<NavigationItem> items) {
        this.items = items;
        this.viewTypes = new HashMap<>();
        this.holderFactories = new SparseArray<>();

        processViewTypes();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder=holderFactories.get(viewType).createViewHolder(parent);
        holder.adapter=this;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        items.get(position).bindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position){
        return viewTypes.get(items.get(position).getClass());
    }


    private void processViewTypes() {
        int type = 0;
        for (NavigationItem item : items) {
            if(!viewTypes.containsKey(item.getClass())){
                viewTypes.put(item.getClass(),type);
                holderFactories.put(type, item);
                type++;
            }
        }
    }


    public void setSelected(int position) {
        NavigationItem newChecked = items.get(position);
        if (!newChecked.isSelectable()) {
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            NavigationItem item = items.get(i);
            if (item.isChecked()) {
                item.setChecked(false);
                notifyItemChanged(i);
                break;
            }
        }

        newChecked.setChecked(true);
        notifyItemChanged(position);

        if (listener != null) {
            listener.onItemSelected(position);
        }
    }

    public void setListener(OnItemSelectedListener listener){
        this.listener=listener;
    }

    static abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private NavigationAdapter adapter;

        @Override
        public void onClick(View v) {
            adapter.setSelected(getAdapterPosition());
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int position);
    }
}
