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

public class EventsUpcomingAdapter extends RecyclerView.Adapter<EventsUpcomingAdapter.ViewHolder> {

    Context context;

    public EventsUpcomingAdapter(Context context, ArrayList<EventsUpcomingModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<EventsUpcomingModel> arrayList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.events_upcoming_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        /////////////////////////// PlaceHolder...........
        Glide.with(context).load(arrayList.get(position).TeamALogo).placeholder(R.drawable.ic_launcher_foreground).circleCrop().into(holder.TeamALogo);
        Glide.with(context).load(arrayList.get(position).TeamBLogo).placeholder(R.drawable.ic_launcher_foreground).circleCrop().into(holder.TeamBLogo);
        holder.MatchNo.setText(arrayList.get(position).MatchNo);
        holder.TeamA.setText(arrayList.get(position).TeamA);
        holder.TeamB.setText(arrayList.get(position).TeamB);
        holder.Time.setText(arrayList.get(position).Time);
        holder.Date.setText(arrayList.get(position).Date);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView TeamALogo,TeamBLogo;
        TextView MatchNo,TeamA,TeamB,Time,Date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TeamALogo = itemView.findViewById(R.id.TeamALogo);
            TeamBLogo = itemView.findViewById((R.id.TeamBLogo));
            MatchNo = itemView.findViewById(R.id.MatchNo);
            TeamA = itemView.findViewById((R.id.TeamA));
            TeamB = itemView.findViewById((R.id.TeamB));
            Time = itemView.findViewById((R.id.Time));
            Date = itemView.findViewById(R.id.Date);

        }
    }
}
