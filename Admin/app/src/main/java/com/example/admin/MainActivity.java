package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;



import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<LeaderBoard> arrayList = new ArrayList<LeaderBoard>();
    String TAG = "Pratik";

    Button LeaderBoard,Past,Live,Upcoming,Cricket;

    String [] EventList=new String[]{"Archery","Badminton","Basketball","Box Cricket","Carrom","Chess","Cricket","Fencing","Football","Handball","Hockey","Kabaddi","Kho Kho","Lawn Tennis","Rowing","Swimming","Table Tennis","Taekwondo","Volleyball"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LeaderBoard=findViewById(R.id.LeaderBoard);
        Past=findViewById(R.id.Past);
        Live=findViewById(R.id.Live);
        Upcoming=findViewById(R.id.Upcoming);
        Cricket=findViewById(R.id.Cricket);

        LeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LeaderBoardUpdator.class);
                startActivity(intent);
            }
        });

        Past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PastMatchUpdator.class);
                startActivity(intent);
            }
        });

        Upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,UpcomingUpdator.class);
                startActivity(intent);
            }
        });

        Live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LiveMatchUpdator.class);
                startActivity(intent);
            }
        });

        Cricket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CricketLive.class);
                startActivity(intent);
            }
        });



        String EventName="Archery";

////            //////LeaderBoard
//        ArrayList<LeaderBoard> arrayList = new ArrayList<LeaderBoard>();
//        arrayList.add(new LeaderBoard("Superman", 3, 83));
//        arrayList.add(new LeaderBoard("Bunty", 4, 67));
//        arrayList.add(new LeaderBoard("Ramesh", 10, 4));
////        arrayList.add(new LeaderBoard("Priya", 7, 34));
////        arrayList.add(new LeaderBoard("Prathmesh", 4, 64));
//        for(LeaderBoard obj:arrayList) {
//            Map<String, Object> data = new HashMap<>();
//            data.put("Team", obj.getTeam());
//            data.put("Position", obj.getPosition());
//            data.put("Points", obj.getPoints());
//            db.collection(EventName).document("LeaderBoard")
//                    .collection("Boys")
//                    .document(obj.getTeam())
//                    .set(data)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, " successfully !" + data);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error  ", e);
//                        }
//                    });
//        }
//
//
//        ArrayList<LeaderBoard> arrayListBoys = new ArrayList<LeaderBoard>();
////        arrayListBoys.add(new LeaderBoard("Shubham", 2, 3));
////        arrayListBoys.add(new LeaderBoard("College of Engineering Pune Technological University", 02, 32));
////        arrayListBoys.add(new LeaderBoard("AISSMS", 9, 49));
//        arrayListBoys.add(new LeaderBoard("Shah Ruk Khan", 1, 54));
//        arrayListBoys.add(new LeaderBoard("Pawar", 5, 84));
//        for(LeaderBoard obj:arrayListBoys) {
//            Map<String, Object> data = new HashMap<>();
//            data.put("Team", obj.getTeam());
//            data.put("Position", obj.getPosition());
//            data.put("Points", obj.getPoints());
//            db.collection(EventName).document("LeaderBoard")
//                    .collection("Boys")
//                    .document(obj.getTeam())
//                    .set(data)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(TAG, " successfully !" + data);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error  ", e);
//                        }
//                    });
//        }
//
//
//
                ///////Live Match
//        ArrayList<EventGenerator> arrayLive=new ArrayList<EventGenerator>();
//        arrayLive.add(new EventGenerator("Boys", "2nd Half", "Modern", "DY Patil","https://firebasestorage.googleapis.com/v0/b/zest-23-203f1.appspot.com/o/Logo%2FIMG_20230206_160519_856.png?alt=media&token=c36c4717-4cf5-4adf-85bf-e44eb218fd40" ,"https://firebasestorage.googleapis.com/v0/b/zest-23-203f1.appspot.com/o/Logo%2FModern-logo1-removebg-preview.png?alt=media&token=7fac4e8f-fd35-45a6-933d-3da279218b7e" , 3));
////        arrayLive.add(new EventGenerator("Mixed", "2st Half", "Cummins", "Modern","https://firebasestorage.googleapis.com/v0/b/zest-demo-f68f0.appspot.com/o/Logo%2Flogo-removebg-preview%20(1).png?alt=media&token=2ef6d1ee-982d-4037-8e6a-7a6439bd7cfd" ,"https://firebasestorage.googleapis.com/v0/b/zest-23-203f1.appspot.com/o/Logo%2FIMG_20230206_160515_853.png?alt=media&token=3af81fde-4d62-425d-9ea5-12f3eefcf60c" , 21));
//        arrayLive.add(new EventGenerator("Girls Mixed", "2st Half", "PCCOE", "PICT","https://firebasestorage.googleapis.com/v0/b/zest-23-203f1.appspot.com/o/Logo%2Fvjti-removebg-preview.png?alt=media&token=28473848-b521-4450-a888-06b8d1625a88" ,"https://firebasestorage.googleapis.com/v0/b/zest-23-203f1.appspot.com/o/Logo%2FIMG_20230206_160515_853.png?alt=media&token=3af81fde-4d62-425d-9ea5-12f3eefcf60c" , 134));
//        for(EventGenerator obj:arrayLive){
//            Map<String, Object> data = new HashMap<>();
//            data.put("MatchNo", obj.getMatchNo());
//            data.put("Category", obj.getCategory());
//            data.put("Innings", obj.getInnings());
//            data.put("TeamA", obj.getTeamA());
//            data.put("TeamB", obj.getTeamB());
//            data.put("TeamALogo", obj.getTeamALogo());
//            data.put("TeamBLogo", obj.getTeamBLogo());
//
//            db.collection(EventName)
//                    .document("Live")
//                    .collection("MatchDetails")
//                    .document(obj.getCategory()+String.valueOf(obj.getMatchNo()))
//                    .set(data)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Log.d(TAG, "Match Details " + obj.getTeamA());
//
//                            Map<String, Object> scoreData = new HashMap<>();
//                            scoreData.put("TeamA", "0");
//                            scoreData.put("TeamB", "0");
//
//                            db.collection(EventName)
//                                    .document("Live")
//                                    .collection("Score")
//                                    .document(obj.getCategory()+String.valueOf(obj.getMatchNo()))
//                                    .set(scoreData)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Log.d(TAG, "Score: " + obj.getTeamB());
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d(TAG, "onFailure: Scores");
//                                        }
//                                    });
//
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d(TAG, "onFailure: MatchDetails");
//                        }
//                    });
//        }
//
//
//
//                    ///////Past MAtch
//        ArrayList<PastMatch> arrayPast=new ArrayList<PastMatch>();
//        arrayPast.add(new PastMatch("Mixed", "3st Half", "MIT WPU", "Walchand","https://firebasestorage.googleapis.com/v0/b/zest-demo-f68f0.appspot.com/o/Logo%2FCOEP_black_new-removebg-preview.png?alt=media&token=450d133d-721b-4084-aee6-123c0401c3b2" ,"https://firebasestorage.googleapis.com/v0/b/zest-demo-f68f0.appspot.com/o/Logo%2Flogo-removebg-preview%20(1).png?alt=media&token=2ef6d1ee-982d-4037-8e6a-7a6439bd7cfd" , "2","16",4));
//        arrayPast.add(new PastMatch("Intercollege", "Set 3-1", "Modeerm", "Mumbai","https://firebasestorage.googleapis.com/v0/b/zest-demo-f68f0.appspot.com/o/Logo%2Flogo-removebg-preview%20(1).png?alt=media&token=2ef6d1ee-982d-4037-8e6a-7a6439bd7cfd" ,"https://firebasestorage.googleapis.com/v0/b/zest-demo-f68f0.appspot.com/o/Logo%2Fpict-removebg-preview.png?alt=media&token=3acb601d-384b-4976-a300-081982a55c6e" , "2","16",4));
//        arrayPast.add(new PastMatch("Super", "1st Half", "MIT WPU", "Satara","https://firebasestorage.googleapis.com/v0/b/zest-23-203f1.appspot.com/o/Logo%2FModern-logo1-removebg-preview.png?alt=media&token=7fac4e8f-fd35-45a6-933d-3da279218b7e" ,"https://www.kasandbox.org/programming-images/avatars/old-spice-man.png" , "2","16",4));
//        for(PastMatch obj:arrayPast){
//            Map<String, Object> data = new HashMap<>();
//            data.put("MatchNo", obj.getMatchNo());
//            data.put("Category", obj.getCategory());
//            data.put("Innings", obj.getInnings());
//            data.put("TeamA", obj.getTeamA());
//            data.put("TeamB", obj.getTeamB());
//            data.put("TeamALogo", obj.getTeamALogo());
//            data.put("TeamBLogo", obj.getTeamBLogo());
//            data.put("TeamAScore", obj.getTeamAScore());
//            data.put("TeamBScore", obj.getTeamBScore());
//
//            db.collection(EventName)
//                    .document("Timeline")
//                    .collection("Past Match")
//                    .document(obj.getCategory()+String.valueOf(obj.getMatchNo()))
//                    .set(data)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Log.d(TAG, "Match Details " + obj.getTeamA());
//                            Map<String, Object> scoreData = new HashMap<>();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d(TAG, "onFailure: MatchDetails");
//                        }
//                    });
//        }






    }
}