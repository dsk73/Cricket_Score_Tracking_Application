package com.example.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class CricketLive extends AppCompatActivity {

    TextView SelectedInnigs,TeamAName;
    Button TeamARun1,TeamARun4,TeamARun6,TeamARunDecrese,BallsIncrese,Balls_Decrese,WktIncrese,WktDecrese,Inningsfirst,Inningssecond;
    TextView MatchNo,Toss,TeamA,AOvers,ARuns,TeamB,BRuns,BOvers;
    String innings,aruns,awkt,aovers,bruns,bwkt,bovers,teama,teamb;
    ImageView TeamBLogo,TeamALogo;

    String Balls[]={"0.0","0.1","0.2","0.3","0.4","0.5","1","1.1","1.2","1.3","1.4","1.5","2","2.1","2.2","2.3","2.4","2.5","3","3.1","3.2","3.3","3.4","3.5","4","4.1","4.2","4.3","4.4","4.5","5","5.1","5.2","5.3","5.4","5.5","6","6.1","6.2","6.3","6.4","6.5","7","7.1","7.2","7.3","7.4","7.5","8","8.1","8.2","8.3","8.4","8.5","9","9.1","9.2","9.3","9.4","9.5","10","10.1","10.2","10.3","10.4","10.5","11","11.1","11.2","11.3","11.4","11.5","12","12.1","12.2","12.3","12.4","12.5","13","13.1","13.2","13.3","13.4","13.5","14","14.1","14.2","14.3","14.4","14.5","15","15.1","15.2","15.3","15.4","15.5","16","16.1","16.2","16.3","16.4","16.5","17","17.1","17.2","17.3","17.4","17.5","18","18.1","18.2","18.3","18.4","18.5","19","19.1","19.2","19.3","19.4","19.5","20"};
    int FirstarrayIndex=0;
    int SecondarrayIndex=0;


    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseFirestore dbwrite=FirebaseFirestore.getInstance();
    FirebaseFirestore dbInnings=FirebaseFirestore.getInstance();
    DocumentReference inningsReference;
    ProgressDialog progressDialog;
    DocumentReference docRef;
    String TAG="Pratik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_live);

        TeamAName=findViewById(R.id.TeamAName);
        SelectedInnigs=findViewById(R.id.SelectedInnigs);
        TeamARun1=findViewById(R.id.TeamARun1);
        TeamARun4=findViewById(R.id.TeamARun4);
        TeamARun6=findViewById(R.id.TeamARun6);
        TeamARunDecrese=findViewById(R.id.TeamARunDecrese);
        BallsIncrese=findViewById(R.id.BallsIncrese);
        Balls_Decrese=findViewById(R.id.Balls_Decrese);
        WktIncrese=findViewById(R.id.WktIncrese);
        WktDecrese=findViewById(R.id.WktDecrese);
        Inningsfirst=findViewById(R.id.Inningsfirst);
        Inningssecond=findViewById(R.id.Inningssecond);

        BOvers=findViewById(R.id.BOvers);
        BRuns=findViewById(R.id.BRuns);
        TeamB=findViewById(R.id.TeamB);
        ARuns=findViewById(R.id.ARuns);
        AOvers=findViewById(R.id.AOvers);
        TeamA=findViewById(R.id.TeamA);
        Toss=findViewById(R.id.Toss);
        MatchNo=findViewById(R.id.MatchNo);
        TeamBLogo=findViewById(R.id.TeamBLogo);
        TeamALogo=findViewById(R.id.TeamALogo);


        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Uploading Data");

        db.collection("Cricket")
                .document("Live")
                .collection("MatchDetails")
                .document("MatchDetails")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        progressDialog.setMessage("Loading Match");
                        progressDialog.show();
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
                            Glide.with(CricketLive.this).load(teamalogo)
                                    .circleCrop().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(TeamALogo);
                            Glide.with(CricketLive.this).load(teamblogo)
                                    .circleCrop().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(TeamBLogo);
                        } else {
//                            Log.d(TAG, "No such document");
                        }
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        fetchLiveScore();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CricketLive.this, "Failed Fetching Score", Toast.LENGTH_SHORT).show();
//                        if(lottieDialog.isShowing()){
//                            lottieDialog.dismiss();
//                        }
//                        if(refresh.isRefreshing()){
//                            refresh.setRefreshing(false);
//                        }
                    }
                });

        Inningsfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedInnigs.setText("1st Innings");
                TeamAName.setText("A");
            }
        });

        Inningssecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedInnigs.setText("2nd Innings");
                TeamAName.setText("B");
            }
        });


        docRef=dbwrite.collection("Cricket")
                .document("Live")
                .collection("Score")
                .document("Score");

        TeamARun1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                if(TeamAName.getText().toString().equals("A")) {
                    String dummy = ARuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(runs);
                    score += +1;
                    docRef.update("ARuns", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else if(TeamAName.getText().toString().equals("B")){
                    String dummy = BRuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(runs);
                    score += +1;
                    docRef.update("BRuns", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(CricketLive.this, "Select Innings", Toast.LENGTH_SHORT).show();
                }
            }
        });
//
        TeamARun4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                if(TeamAName.getText().toString().equals("A")) {
                    String dummy = ARuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(runs);
                    score += +4;
                    docRef.update("ARuns", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else if(TeamAName.getText().toString().equals("B")){
                    String dummy = BRuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(runs);
                    score += +4;
                    docRef.update("BRuns", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(CricketLive.this, "Select Innings", Toast.LENGTH_SHORT).show();
                }
            }
        });


        TeamARun6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                if(TeamAName.getText().toString().equals("A")) {
                    String dummy = ARuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(runs);
                    score += +6;
                    docRef.update("ARuns", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else if(TeamAName.getText().toString().equals("B")){
                    String dummy = BRuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(runs);
                    score += +6;
                    docRef.update("BRuns", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(CricketLive.this, "Select Innings", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TeamARunDecrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                if(TeamAName.getText().toString().equals("A")) {
                    String dummy = ARuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(runs);
                    score += -1;
                    docRef.update("ARuns", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else if(TeamAName.getText().toString().equals("B")){
                    String dummy = BRuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(runs);
                    score += -1;
                    docRef.update("BRuns", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(CricketLive.this, "Select Innings", Toast.LENGTH_SHORT).show();
                }
            }
        });

        WktIncrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                if(TeamAName.getText().toString().equals("A")) {
                    String dummy = ARuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(wickets);
                    score += +1;
                    docRef.update("AWkt", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else if(TeamAName.getText().toString().equals("B")){
                    String dummy = BRuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(wickets);
                    score += +1;
                    docRef.update("BWkt", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(CricketLive.this, "Select Innings", Toast.LENGTH_SHORT).show();
                }
            }
        });

        WktDecrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                if(TeamAName.getText().toString().equals("A")) {
                    String dummy = ARuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(wickets);
                    score += -1;
                    docRef.update("AWkt", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else if(TeamAName.getText().toString().equals("B")){
                    String dummy = BRuns.getText().toString();
                    int index = dummy.indexOf("/");
                    String runs = dummy.substring(0, index);
                    String wickets = dummy.substring(index + 1);
                    int score = Integer.valueOf(wickets);
                    score += -1;
                    docRef.update("BWkt", String.valueOf(score))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(CricketLive.this, "Select Innings", Toast.LENGTH_SHORT).show();
                }
            }
        });


        BallsIncrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                if(TeamAName.getText().toString().equals("A")) {
                    String dummy = AOvers.getText().toString();
                    FirstarrayIndex+=+1;
                    docRef.update("AOvers", Balls[FirstarrayIndex])
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else if(TeamAName.getText().toString().equals("B")){
                    SecondarrayIndex+=+1;
                    docRef.update("BOvers", Balls[SecondarrayIndex])
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(CricketLive.this, "Select Innings", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Balls_Decrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                if(TeamAName.getText().toString().equals("A")) {
                    String dummy = AOvers.getText().toString();
                    FirstarrayIndex+=-1;
                    docRef.update("AOvers", Balls[FirstarrayIndex])
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else if(TeamAName.getText().toString().equals("B")){
                    SecondarrayIndex+=-1;
                    docRef.update("BOvers", Balls[SecondarrayIndex])
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Toast.makeText(CricketLive.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(CricketLive.this, "Select Innings", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void fetchLiveScore() {
        db.collection("Cricket")
                .document("Live")
                .collection("Score")
                .document("Score")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        try{
                            Object obj=value.getData();
//                            innings = ((Map<?, ?>) obj).get("Innings").toString();
                            aruns = ((Map<?, ?>) obj).get("ARuns").toString();
                            awkt = ((Map<?, ?>) obj).get("AWkt").toString();
                            aovers = ((Map<?, ?>) obj).get("AOvers").toString();
                            bruns = ((Map<?, ?>) obj).get("BRuns").toString();
                            bwkt = ((Map<?, ?>) obj).get("BWkt").toString();
                            bovers = ((Map<?, ?>) obj).get("BOvers").toString();

//                            Innings.setText(innings + " Innings");

//                        if(innings.equals("2nd")){
                            BRuns.setText(bruns + "/" + bwkt);
                            BOvers.setText("("+ bovers + ")");
//                            updateTarget();
//                        }
//                        else{
                            ARuns.setText(aruns + "/" + awkt);
                            AOvers.setText("(" + aovers + ")");
//                            BRuns.setText("-/-");
//                            BOvers.setText("-");
//                            Target.setText("");
//                            CRR.setText("");
//                            RRR.setText("");
//                        }
                        }catch (Exception e){
//                            Log.d(TAG, e.getMessage());
                        }
                    }

                });


    }



}