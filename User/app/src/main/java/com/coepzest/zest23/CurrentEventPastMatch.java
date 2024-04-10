package com.coepzest.zest23;

import android.app.ProgressDialog;
import android.os.Bundle;

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

public class CurrentEventPastMatch extends Fragment {

    View view;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ArrayList<CurrentEventLiveScoreModel> arrayList;
    CurrentEventLiveScoreAdapter adapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String CurrentEvent;

    String TAG="Pratik";

    public CurrentEventPastMatch() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_current_event_past_match, container, false);

        CurrentEvent=getArguments().getString("CurrentEvent");

        recyclerView=view.findViewById(R.id.CurrentEventPastMatchRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayList=new ArrayList<CurrentEventLiveScoreModel>();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(150000000)
                .build();
        db.setFirestoreSettings(settings);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");

        getPastMatch(CurrentEvent);

        adapter=new CurrentEventLiveScoreAdapter(getContext(), arrayList, CurrentEvent);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void getPastMatch(String currentEvent) {
        progressDialog.show();
        db.collection(currentEvent)
                .document("Timeline")
                .collection("Past Match")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()){
                            Object obj=documentSnapshot.getData();
                            try {
                                String Category = ((Map<?, ?>) obj).get("Category").toString();
                                String MatchNo = ((Map<?, ?>) obj).get("MatchNo").toString();
                                String Innings = ((Map<?, ?>) obj).get("Innings").toString();
                                String TeamA = ((Map<?, ?>) obj).get("TeamA").toString();
                                String TeamB = ((Map<?, ?>) obj).get("TeamB").toString();
                                String TeamALogo = ((Map<?, ?>) obj).get("TeamALogo").toString();
                                String TeamBLogo = ((Map<?, ?>) obj).get("TeamBLogo").toString();
                                String TeamAScore = ((Map<?, ?>) obj).get("TeamAScore").toString();
                                String TeamBScore = ((Map<?, ?>) obj).get("TeamBScore").toString();
                                arrayList.add(new CurrentEventLiveScoreModel(Category, MatchNo, Innings, TeamAScore, TeamBScore, TeamA, TeamB, TeamALogo, TeamBLogo));
                            }catch (Exception exception){
//                                Log.d(TAG, exception.getMessage());
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