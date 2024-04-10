package com.coepzest.zest23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coepzest.zest23.R;

import java.util.ArrayList;

public class MapEventsAdapter extends RecyclerView.Adapter<MapEventsAdapter.ViewHolder> {

    String TAG="Pratik";
    Context context;
    ArrayList<MapEventsModel> arrayList;

    public MapEventsAdapter(Context context, ArrayList<MapEventsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mapevent_card,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).Photo).placeholder(R.drawable.ic_launcher_foreground).into(holder.Photo);
        holder.Text.setText(arrayList.get(position).Text);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView Photo;
        TextView Text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Photo=itemView.findViewById(R.id.Photo);
            Text=itemView.findViewById(R.id.Text);
        }

    }

}
