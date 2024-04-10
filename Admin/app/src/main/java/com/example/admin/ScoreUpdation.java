package com.example.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ScoreUpdation extends AppCompatActivity {

    TextView TeamAName,TeamBName,TeamB,TeamA,TeamAScore,TeamBScore,Innings,MatchNo,Category;
    ImageView TeamALogo,TeamBLogo;
    Button TeamADecrese,TeamAIncrese,TeamBIncrese,TeamBDecrese ;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseFirestore dbwrite=FirebaseFirestore.getInstance();
    FirebaseFirestore dbInnings=FirebaseFirestore.getInstance();
    DocumentReference inningsReference;
    String matchno,EventName,category;
    ProgressDialog progressDialog;
    DocumentReference docRef;
    String TAG="Pratik";

    EditText InningsSetter;
    Button InningsSetterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_updation);

        Intent intent = getIntent();
        matchno = intent.getStringExtra("MatchNo");
        EventName = intent.getStringExtra("EventName");
        category = intent.getStringExtra("Category");

        TeamAName=findViewById(R.id.TeamAName);
        TeamBName=findViewById(R.id.SelectedInnigs);
        TeamB=findViewById(R.id.TeamB);
        TeamA=findViewById(R.id.TeamA);
        TeamAScore=findViewById(R.id.TeamAScore);
        TeamALogo=findViewById(R.id.TeamALogo);
        TeamBLogo=findViewById(R.id.TeamBLogo);
        TeamBScore=findViewById(R.id.TeamBScore);
        Innings=findViewById(R.id.Innings);
        MatchNo=findViewById(R.id.MatchNo);
        Category=findViewById(R.id.Category);
        TeamADecrese=findViewById(R.id.TeamADecrese);
        TeamAIncrese=findViewById(R.id.TeamARun1);
        TeamBIncrese=findViewById(R.id.BallsIncrese);
        TeamBDecrese=findViewById(R.id.Balls_Decrese);
        InningsSetter=findViewById(R.id.InningsSetter);
        InningsSetterButton=findViewById(R.id.InningsSetterButton);


        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Uploading Data");


        db.collection(EventName)
                .document("Live")
                .collection("MatchDetails")
                .document(category + "Match No: " + matchno)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        progressDialog.setMessage("Loading Match");
                        progressDialog.show();
                        Object obj=documentSnapshot.getData();
                        String Categorystr = ((Map<?, ?>) obj).get("Category").toString();
                        String MatchNostr = ((Map<?, ?>) obj).get("MatchNo").toString();
                        String Inningsstr = ((Map<?, ?>) obj).get("Innings").toString();
                        String TeamAstr = ((Map<?, ?>) obj).get("TeamA").toString();
                        String TeamBstr = ((Map<?, ?>) obj).get("TeamB").toString();
                        String TeamALogostr = ((Map<?, ?>) obj).get("TeamALogo").toString();
                        String TeamBLogostr = ((Map<?, ?>) obj).get("TeamBLogo").toString();
                        try{
                            Category.setText(Categorystr);
                            MatchNo.setText(MatchNostr);
                            Innings.setText(Inningsstr);
                            TeamA.setText(TeamAstr);
                            TeamAName.setText(TeamAstr);
                            TeamB.setText(TeamBstr);
                            TeamBName.setText(TeamBstr);
                            Glide.with(ScoreUpdation.this).load(TeamALogostr).placeholder(R.drawable.ic_launcher_foreground)
                                    .circleCrop()
                                    .error(R.drawable.ic_launcher_foreground)
                                    .into(TeamALogo);
                            Glide.with(ScoreUpdation.this).load(TeamBLogostr).placeholder(R.drawable.ic_launcher_foreground)
                                    .circleCrop()
                                    .error(R.drawable.ic_launcher_foreground)
                                    .into(TeamBLogo);
                        }catch (Exception exception){

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
                        Toast.makeText(ScoreUpdation.this, "Failed Fetching Score", Toast.LENGTH_SHORT).show();
//                        if(lottieDialog.isShowing()){
//                            lottieDialog.dismiss();
//                        }
//                        if(refresh.isRefreshing()){
//                            refresh.setRefreshing(false);
//                        }
                    }
                });



        docRef=dbwrite.collection(EventName)
                .document("Live")
                .collection("Score")
                .document(category + "Match No: " + matchno);

        TeamAIncrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                int score=Integer.valueOf(TeamAScore.getText().toString());
                score+=+1;
                docRef.update("TeamA", String.valueOf(score))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(ScoreUpdation.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        TeamADecrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                int score=Integer.valueOf(TeamAScore.getText().toString());
                score+=-1;
                docRef.update("TeamA", String.valueOf(score))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(ScoreUpdation.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        TeamBIncrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                int score=Integer.valueOf(TeamBScore.getText().toString());
                score+=+1;
                docRef.update("TeamB", String.valueOf(score))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(ScoreUpdation.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        TeamBDecrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Uploading Data");
                progressDialog.show();
                int score=Integer.valueOf(TeamBScore.getText().toString());
                score+=-1;
                docRef.update("TeamB", String.valueOf(score))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(ScoreUpdation.this, "Failed Uploading Score", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        inningsReference=dbInnings.collection(EventName)
                        .document("Live")
                                .collection("MatchDetails")
                                        .document(category + "Match No: " + matchno);

        InningsSetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InningsSetter.getText().toString().isEmpty()){
                    Toast.makeText(ScoreUpdation.this, "Blank Message", Toast.LENGTH_SHORT).show();
                }
                else{
                    inningsReference.update("Innings",InningsSetter.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Innings.setText(InningsSetter.getText().toString());
                                    InningsSetter.setText("");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ScoreUpdation.this, "Failed Setting Message", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }

    private void fetchLiveScore() {

        db.collection(EventName)
                .document("Live")
                .collection("Score")
                .document(category + "Match No: " + matchno)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.exists()) {
                            String teamascore = value.getString("TeamA");
                            String teambscore = value.getString("TeamB");
                            TeamAScore.setText(teamascore);
                            TeamBScore.setText(teambscore);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}