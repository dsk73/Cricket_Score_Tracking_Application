package com.coepzest.zest23;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coepzest.zest23.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CurrentEventLeaderboard extends Fragment {

    View view;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ArrayList<LeaderBoardModel> arrayList;
    LeaderBoardAdapter adapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    CardView BoysLeaderBoard,GirlsLeaderBoard;

    String TAG="Pratik";
    String CurrentEvent;


    public CurrentEventLeaderboard() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_current_event_leaderboard, container, false);

        CurrentEvent=getArguments().getString("CurrentEvent");

//        BoysLeaderBoard=view.findViewById(R.id.BoysLeaderBoard);
//        GirlsLeaderBoard=view.findViewById(R.id.GirlsLeaderBoard);

//        recyclerView=view.findViewById(R.id.LeaderboardRV);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayList=new ArrayList<LeaderBoardModel>();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(150000000)
                .build();
        db.setFirestoreSettings(settings);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");

        fetchLeaderBoard(CurrentEvent,"Boys");

        BoysLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                fetchLeaderBoard(CurrentEvent,"Boys");
            }
        });

        GirlsLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                fetchLeaderBoard(CurrentEvent,"Girls");
            }
        });

        adapter=new LeaderBoardAdapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void fetchLeaderBoard(String CurrentEvent,String category) {
        progressDialog.show();
        db.collection(CurrentEvent)
                .document("LeaderBoard")
                .collection(category)
                .orderBy("Position")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot value) {
                        for(DocumentSnapshot documentSnapshot:value.getDocuments()){
                            if(documentSnapshot.exists()) {
                                Object obj = documentSnapshot.getData();
                                String Team = ((Map<?, ?>) obj).get("Team").toString();
                                String Position = ((Map<?, ?>) obj).get("Position").toString();
                                String Points = ((Map<?, ?>) obj).get("Points").toString();
                                arrayList.add(new LeaderBoardModel(Position, Team, Points));
                            }
                        }
                        adapter.notifyDataSetChanged();

                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}