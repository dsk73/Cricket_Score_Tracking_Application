package com.coepzest.zest23;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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


public class Gallery extends Fragment {

    View view;
    ArrayList<GalleryModel> arrayList;
    GalleryAdapter adapter;
    RecyclerView recyclerView;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;

    String TAG="Pratik";

    public Gallery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_gallery, container, false);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(150000000)
                .build();
        db.setFirestoreSettings(settings);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");

        recyclerView = view.findViewById(R.id.GalleryRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        arrayList=new ArrayList<GalleryModel>();
        addPhoto();
        adapter= new GalleryAdapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void addPhoto() {
        progressDialog.show();
        db.collection("Gallery")
                .orderBy("No")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()){
                            if(documentSnapshot.exists()) {
                                Object obj = documentSnapshot.getData();
                                String Photo = ((Map<?, ?>) obj).get("Photo").toString();
                                arrayList.add(new GalleryModel(Photo));
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