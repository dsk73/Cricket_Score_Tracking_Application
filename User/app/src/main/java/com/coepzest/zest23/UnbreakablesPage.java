package com.coepzest.zest23;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.coepzest.zest23.R;


public class UnbreakablesPage extends Fragment {

    GridLayout mainGrid;
    View view;
    ImageView Dinesh,Ali,Erica,Pullela,Bobby,Matthias,Vignesh,Roger,Aries;

    public UnbreakablesPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_unbreakables_page, container, false);
        mainGrid=(GridLayout) view.findViewById(R.id.mainGrid);
        Dinesh=view.findViewById(R.id.Dinesh);
        Ali=view.findViewById(R.id.Ali);
//        Erica=view.findViewById(R.id.Erica);
        Pullela=view.findViewById(R.id.Pullela);
        Bobby=view.findViewById(R.id.Bobby);
        Matthias=view.findViewById(R.id.Matthias);
        Vignesh=view.findViewById(R.id.Vignesh);
        Roger=view.findViewById(R.id.Roger);
        Aries=view.findViewById(R.id.Aries);

        for(int i=0;i<mainGrid.getChildCount();i++){
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = null;
                    Intent intent = new Intent(getContext(), UnbreakablesSportsman.class);
                    ActivityOptionsCompat options = null;
                    switch(finalI){
                        case 0:
                            s="Dinesh";
                            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    getActivity(), Dinesh, ViewCompat.getTransitionName(Dinesh));
                            break;
                        case 1:
                            s="Ali";
                            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    getActivity(), Ali, ViewCompat.getTransitionName(Ali));
                            break;
                        case 2:
                            s="Pullela";
                            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    getActivity(), Pullela, ViewCompat.getTransitionName(Pullela));
                            break;
                        case 3:
                            s="Bobby";
                            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    getActivity(), Bobby, ViewCompat.getTransitionName(Bobby));
                            break;
                        case 4:
                            s="Matthias";
                            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    getActivity(), Matthias, ViewCompat.getTransitionName(Matthias));
                            break;
                        case 5:
                            s="Vignesh";
                            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    getActivity(), Vignesh, ViewCompat.getTransitionName(Vignesh));
                            break;
                        case 6:
                            s="Roger";
                            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    getActivity(), Roger, ViewCompat.getTransitionName(Roger));
                            break;
                        case 7:
                            s="Aries";
                            options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    getActivity(), Aries, ViewCompat.getTransitionName(Aries));
                            break;
                        default:
                            break;
                    }
                    intent.putExtra("Name",s);
                    startActivity(intent, options.toBundle());

                }
            });
        }



        return view;
    }
}