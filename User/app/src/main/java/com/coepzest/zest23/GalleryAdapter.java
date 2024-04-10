package com.coepzest.zest23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.coepzest.zest23.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    Context context;
    ArrayList<GalleryModel> arrayList;
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    String TAG="Pratik";

    public GalleryAdapter(Context context, ArrayList<GalleryModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_card, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(arrayList.get(position).Photo)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(34)))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.archery_icon)
                .into(holder.Photo);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView Photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Photo=itemView.findViewById(R.id.Photo);
        }
    }
}
