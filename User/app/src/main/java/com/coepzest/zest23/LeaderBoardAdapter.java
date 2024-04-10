package com.coepzest.zest23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coepzest.zest23.R;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {

    Context context;

    public LeaderBoardAdapter(Context context, ArrayList<LeaderBoardModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<LeaderBoardModel> arrayList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leaderboard_card, parent, false);
        LeaderBoardAdapter.ViewHolder viewHolder = new LeaderBoardAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Position.setText(arrayList.get(position).Position);
        holder.Team.setText(arrayList.get(position).Team);
        holder.Points.setText(arrayList.get(position).Points);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Position, Team, Points;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Position=itemView.findViewById(R.id.Position);
            Team=itemView.findViewById(R.id.Team);
            Points=itemView.findViewById(R.id.Points);
        }
    }
}
