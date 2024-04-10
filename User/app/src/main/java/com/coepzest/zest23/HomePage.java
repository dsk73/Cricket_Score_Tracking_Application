package com.coepzest.zest23;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.coepzest.zest23.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class HomePage extends Fragment {

    View view;

    LinearLayout cycloLayout;

    LinearLayout maraLayout;


    public HomePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home_page, container, false);


        cycloLayout = view.findViewById(R.id.cyclo);
        cycloLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                gotoUrl("https://www.coepzest.org/Cyclothon/");
         }
});




        maraLayout = view.findViewById(R.id.mara);
        maraLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                gotoUrl("https://www.coepzest.org/Marathon/");
            }
        });



        return view;

    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
}




}