
package com.coepzest.zest23;

import android.annotation.SuppressLint;
import android.icu.text.DecimalFormat;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.coepzest.zest23.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;


public class CricketLiveScore extends Fragment {

    View view;
    LinearLayout FirstInninng,SecondInning;
    TextView MatchNo,Toss,Innings,TeamA,ARuns,AOvers,Target,TeamB,BRuns,BOvers,CRR,RRR;
    String innings,aruns,awkt,aovers,bruns,bwkt,bovers,teama,teamb;
    ImageView TeamALogo,TeamBLogo;
    Boolean status=false;
    String CurrentEvent;
    Double TotalBalls;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DecimalFormat df = new DecimalFormat("#,###.##");

    String TAG="Pratik";

    public CricketLiveScore() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_cricket_live_score, container, false);

        CurrentEvent=getArguments().getString("CurrentEvent");
        if(CurrentEvent.equals("Box Cricket")) {
            TotalBalls = 24.0;
        }else{
            TotalBalls=120.0;
        }


        FirstInninng=view.findViewById(R.id.FirstInninng);
        SecondInning=view.findViewById(R.id.SecondInning);
        MatchNo=view.findViewById(R.id.MatchNo);
        Toss=view.findViewById(R.id.Toss);
        Innings=view.findViewById(R.id.Innings);
        TeamA=view.findViewById(R.id.TeamA);
        ARuns=view.findViewById(R.id.ARuns);
        AOvers=view.findViewById(R.id.AOvers);
        Target=view.findViewById(R.id.Target);
        TeamB=view.findViewById(R.id.TeamB);
        BRuns=view.findViewById(R.id.BRuns);
        BOvers=view.findViewById(R.id.BOvers);
        CRR=view.findViewById(R.id.CRR);
        RRR=view.findViewById(R.id.RRR);
        TeamALogo=view.findViewById(R.id.TeamALogo);
        TeamBLogo=view.findViewById(R.id.TeamBLogo);

        db.collection(CurrentEvent)
                .document("Live")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        status=value.getBoolean("status");
                        if(Boolean.TRUE.equals(status)){
                            FirstInninng.setVisibility(View.VISIBLE);
                            SecondInning.setVisibility(View.VISIBLE);
                            loadMatchDetails();
                        }
                        else{
                            FirstInninng.setVisibility(View.GONE);
                        }
                    }
                });

        db.collection(CurrentEvent)
                .document("Live")
                .collection("Score")
                .document("Score")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        try{
                            Object obj=value.getData();
                            innings = ((Map<?, ?>) obj).get("Innings").toString();
                            aruns = ((Map<?, ?>) obj).get("ARuns").toString();
                            awkt = ((Map<?, ?>) obj).get("AWkt").toString();
                            aovers = ((Map<?, ?>) obj).get("AOvers").toString();
                            bruns = ((Map<?, ?>) obj).get("BRuns").toString();
                            bwkt = ((Map<?, ?>) obj).get("BWkt").toString();
                            bovers = ((Map<?, ?>) obj).get("BOvers").toString();

                            Innings.setText(innings + " Innings");

////                        if(innings=="2"){
////                            SecondInningView.setVisibility(View.VISIBLE);
                            BRuns.setText(bruns + "/" + bwkt);
                            BOvers.setText("("+ bovers + ")");
////                        }
////                        else{
                            ARuns.setText(aruns + "/" + awkt);
                            AOvers.setText("(" + aovers + ")");
                            updateTarget();
////                        }
                        }catch (Exception e){

                        }
                    }

                    @SuppressLint("SetTextI18n")
                    private void updateTarget() {
                        Double TeamARuns=Double.valueOf(aruns);
                        Double TeamBRuns=Double.valueOf(bruns);
                        Double TeamBOvers=Double.valueOf(bovers);

                        Double target=TeamARuns-TeamBRuns;
                        Double balls=getBalls(TeamBOvers);

                        if(target<=0){
                            Target.setText(teamb + " Won by " + String.valueOf(TotalBalls-balls) + " Balls");
                            CRR.setText("");
                            RRR.setText("");
                        }else if(balls>=TotalBalls){
                            Target.setText(teama + " Won by " + String.valueOf(target.intValue()-1) + " Runs");
                            CRR.setText("");
                            RRR.setText("");
                        }
                        else {
                            String crr = df.format(TeamBRuns * 6 / balls);
                            String rrr = df.format(target * 6 / (TotalBalls - balls));

                            Target.setText("Required Runs: " + String.valueOf(target.intValue()));
                            CRR.setText("CRR: " + crr );
                            RRR.setText("RRR :" + rrr);
                        }
                    }

                });

        return view;
    }



    private Double getBalls(Double overs) {
        Double balls=(overs*10)%10;
        balls+=(overs.intValue()*6);
        return balls;
    }

    private void loadMatchDetails() {

        db.collection(CurrentEvent)
                .document("Live")
                .collection("MatchDetails")
                .document("MatchDetails")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String matchno=documentSnapshot.getString("MatchNo");
                            String toss=documentSnapshot.getString("Toss");
                            String choose=documentSnapshot.getString("Choose");
                            teama=documentSnapshot.getString("TeamA");
                            teamb=documentSnapshot.getString("TeamB");
                            String teamalogo=documentSnapshot.getString("TeamALogo");
                            String teamblogo=documentSnapshot.getString("TeamBLogo");

                            MatchNo.setText("Match No: " + matchno);
                            Toss.setText(toss + " Won the toss and chose to " + choose);
                            TeamA.setText(teama);
                            TeamB.setText(teamb);
                            Glide.with(getContext()).load(teamalogo)
                                    .circleCrop().placeholder(R.drawable.ic_launcher_foreground).into(TeamALogo);
                            Glide.with(getContext()).load(teamblogo)
                                    .circleCrop().placeholder(R.drawable.ic_launcher_foreground).into(TeamBLogo);
                        } else {
//                            Log.d(TAG, "No such document");
                        }
                    }
                });
    }
}