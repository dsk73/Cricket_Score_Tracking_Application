package com.coepzest.zest23;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coepzest.zest23.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MapPage extends Fragment {

    View view;
    String TAG="Pratik";

    MapEventsAdapter adapter;
    ArrayList<MapEventsModel> arrayList;
    RecyclerView recyclerView;

    Button main_building,boatClub,COEPGround,Refreshment,TableTennis;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;
    GeoPoint geoPoint;

    private GoogleMap mMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLng coepGrd = new LatLng(18.528161758804586, 73.85266788568693);
            LatLng boatCLubGrd = new LatLng(18.53095384716651, 73.85630031454541);
            LatLng mainBuilding = new LatLng(18.52942970918503, 73.85651988785894);
            LatLng RSComplex = new LatLng(18.4814299, 73.8199556);
            MarkerOptions optionCoepGrd = new MarkerOptions().position(coepGrd).title("COEP Ground");
            MarkerOptions optionBoatClubGrd = new MarkerOptions().position(boatCLubGrd).title("Boat Club");
            MarkerOptions optionMainBuilding = new MarkerOptions().position(mainBuilding).title("Main Building");
            MarkerOptions optionRSComplex = new MarkerOptions().position(RSComplex).title("Table Tennis Court");
            mMap.addMarker(optionCoepGrd);
            mMap.addMarker(optionBoatClubGrd);
            mMap.addMarker(optionMainBuilding);
            mMap.addMarker(optionRSComplex);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mainBuilding));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mainBuilding, 17f));

            main_building.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fetchImages("Main Building");
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mainBuilding, 17f));
                }
            });

            boatClub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fetchImages("Boat Club");
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(boatCLubGrd, 17f));
                }
            });


            COEPGround.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fetchImages("COEP Ground");
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coepGrd, 17f));
                }
            });

            Refreshment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fetchImages("Refreshment");
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coepGrd, 17f));
                }
            });

            TableTennis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fetchImages("Refreshment");
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(RSComplex, 17f));
                }
            });

        }
    };

    public MapPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_map_page, container, false);

        main_building=(Button) view.findViewById(R.id.mainBuilding) ;
        boatClub=(Button) view.findViewById(R.id.boatClub);
        COEPGround=(Button) view.findViewById(R.id.COEPGround);
        Refreshment=(Button) view.findViewById(R.id.Refreshment);
        TableTennis=(Button) view.findViewById(R.id.TableTennis);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(150000000)
                .build();
        db.setFirestoreSettings(settings);

        recyclerView=view.findViewById(R.id.MapEventRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

        arrayList=new ArrayList<MapEventsModel>();
        fetchImages("Main Building");

        adapter=new MapEventsAdapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void fetchImages(String ground) {
        arrayList.clear();
        progressDialog.show();

        db.collection("ImageSlider")
                .whereEqualTo("Title", ground)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<String> group = (List<String>) document.get("ImageUrl");
                                for(int i=0;i<group.size();i+=2){
                                    String ImageUri=group.get(i);
                                    String desription=group.get(i+1);
                                    arrayList.add(new MapEventsModel(ImageUri, desription));
                                }
                                adapter.notifyDataSetChanged();
                            }

                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                });
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

}