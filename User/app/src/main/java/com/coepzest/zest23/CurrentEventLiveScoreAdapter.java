package com.coepzest.zest23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coepzest.zest23.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class CurrentEventLiveScoreAdapter extends RecyclerView.Adapter<CurrentEventLiveScoreAdapter.ViewHolder> {

    String TAG="Pratik";
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Context context;
    ArrayList<CurrentEventLiveScoreModel> arrayList;
    String CurrentEvent;

    public CurrentEventLiveScoreAdapter(Context context, ArrayList<CurrentEventLiveScoreModel> arrayList, String CurrentEvent) {
        this.context = context;
        this.arrayList = arrayList;
        this.CurrentEvent = CurrentEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.current_event_live_score_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).TeamALogo).placeholder(R.drawable.ic_launcher_foreground).circleCrop().into(holder.TeamALogo);
        Glide.with(context).load(arrayList.get(position).TeamBLogo).placeholder(R.drawable.ic_launcher_foreground).circleCrop().into(holder.TeamBLogo);
        holder.Category.setText(arrayList.get(position).Category);
        holder.MatchNo.setText(arrayList.get(position).MatchNo);
        holder.Innings.setText(arrayList.get(position).Innings);
        holder.TeamAScore.setText(arrayList.get(position).TeamAScore);
        holder.TeamBScore.setText(arrayList.get(position).TeamBScore);
        holder.TeamA.setText(arrayList.get(position).TeamA);
        holder.TeamB.setText(arrayList.get(position).TeamB);

        String matchno=arrayList.get(position).MatchNo;
        String category=arrayList.get(position).Category;

        db.collection(CurrentEvent)
                .document("Live")
                .collection("Score")
                .document(category+matchno)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.exists()) {
                            String teamascore = value.getString("TeamA");
                            String teambscore = value.getString("TeamB");
                            holder.TeamAScore.setText(teamascore);
                            holder.TeamBScore.setText(teambscore);
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Category,MatchNo,Innings,TeamAScore,TeamBScore,TeamA,TeamB;
        ImageView TeamALogo,TeamBLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TeamALogo=itemView.findViewById(R.id.TeamALogo);
            TeamBLogo=itemView.findViewById(R.id.TeamBLogo);
            Category=itemView.findViewById(R.id.Category);
            MatchNo=itemView.findViewById(R.id.MatchNo);
            Innings=itemView.findViewById(R.id.Innings);
            TeamAScore=itemView.findViewById(R.id.TeamAScore);
            TeamBScore=itemView.findViewById(R.id.TeamBScore);
            TeamA=itemView.findViewById(R.id.TeamA);
            TeamB=itemView.findViewById(R.id.TeamB);
        }
    }
}
