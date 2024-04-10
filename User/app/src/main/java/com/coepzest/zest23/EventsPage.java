package com.coepzest.zest23;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coepzest.zest23.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class EventsPage extends Fragment implements EventsIconInterface {
    View view;
    String [] EventList=new String[]{"Archery","Badminton","Basketball","Box Cricket","Carrom","Chess","Cricket","Fencing","Football","Hockey","Kabaddi","Kho Kho","Rowing","Table Tennis","Volleyball"};

    EventsViewModel viewModel;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;
    ExtendedFloatingActionButton fabLive;


    ArrayList<EventsIconModel> iconsArray;
    EventsIconAdapter iconsAdapter;
    RecyclerView iconsRecyclerView;

    ArrayList<EventsUpcomingModel> upcomingArray;
    EventsUpcomingAdapter upcomingAdapter;
    RecyclerView upcomingRecyclerView;

    String TAG="Pratik";
    String CurrentEvent;

    ImageView ComingSoon;

    public EventsPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_events_page, container, false);

        viewModel=new ViewModelProvider(requireActivity()).get(EventsViewModel.class);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(150000000)
                .build();
        db.setFirestoreSettings(settings);

        iconsRecyclerView = view.findViewById(R.id.IconsRecyclerView);
        iconsRecyclerView.setHasFixedSize(true);
        iconsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iconsArray=new ArrayList<EventsIconModel>();
        addIcons();
        iconsAdapter= new EventsIconAdapter(getContext(), iconsArray,this);
        iconsRecyclerView.setAdapter(iconsAdapter);

        return view;
    }

    private void addIcons() {
        iconsArray.add(new EventsIconModel(R.drawable.archery_icon, "Archery"));
        iconsArray.add(new EventsIconModel(R.drawable.badminton, "Badminton"));
        iconsArray.add(new EventsIconModel(R.drawable.basketball_icon, "Basketball"));
        iconsArray.add(new EventsIconModel(R.drawable.cricket, "Box Cricket"));
        iconsArray.add(new EventsIconModel(R.drawable.carrom, "Carrom"));
        iconsArray.add(new EventsIconModel(R.drawable.chess_image, "Chess"));
        iconsArray.add(new EventsIconModel(R.drawable.cricketmain, "Cricket"));
        iconsArray.add(new EventsIconModel(R.drawable.football, "Football"));
        iconsArray.add(new EventsIconModel(R.drawable.fencing_image, "Fencing"));
        iconsArray.add(new EventsIconModel(R.drawable.hockey, "Hockey"));
        iconsArray.add(new EventsIconModel(R.drawable.kabaddi, "Kabaddi"));
        iconsArray.add(new EventsIconModel(R.drawable.khokho, "Kho Kho"));
        iconsArray.add(new EventsIconModel(R.drawable.indoorrowing, "Rowing"));
        iconsArray.add(new EventsIconModel(R.drawable.tabletennis_ic, "Table Tennis"));
        iconsArray.add(new EventsIconModel(R.drawable.volleyball_icon, "Volleyball"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel=new ViewModelProvider(requireActivity()).get(EventsViewModel.class);
        viewModel.setSelectedEvent("ZEST 24");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void getEventName(String EventName) {
        viewModel.setSelectedEvent(EventName);
        CurrentEvent=EventName;
//        fetchUpcomingMatches(EventName);
    }

    private void fetchUpcomingMatches(String EventName) {
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();
        upcomingArray.clear();
        db.collection(EventName)
                .document("Timeline")
                .collection("Upcoming")
                .orderBy("MatchNo")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot value) {
                        for(DocumentSnapshot documentSnapshot:value.getDocuments()){
                            Object obj=documentSnapshot.getData();
                            String TeamALogo = ((Map<?, ?>) obj).get("TeamALogo").toString();
                            String TeamBLogo = ((Map<?, ?>) obj).get("TeamBLogo").toString();
                            String MatchNo = "Match No " + ((Map<?, ?>) obj).get("MatchNo").toString();
                            String Time = ((Map<?, ?>) obj).get("Time").toString();
                            String Date = ((Map<?, ?>) obj).get("Date").toString();
                            String TeamA = ((Map<?, ?>) obj).get("TeamA").toString();
                            String TeamB = ((Map<?, ?>) obj).get("TeamB").toString();
                            upcomingArray.add(new EventsUpcomingModel(TeamALogo, TeamBLogo, MatchNo, TeamA, TeamB, Time, Date));
                        }
                        upcomingAdapter.notifyDataSetChanged();
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}