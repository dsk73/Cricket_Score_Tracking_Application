package com.coepzest.zest23;
//

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coepzest.zest23.R;

//
public class ExploreMore extends Fragment {
    //
    TextView PrivacyPolicy;
    View view;

    ImageView insta ;
    ImageView twitter ;
    ImageView linkidin ;
    ImageView youtube ;
    ImageView facebook ;

    public ExploreMore() {
        // Required empty public constructor
    }

    //
//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_explore_more, container, false);


        TextView ourTeam = view.findViewById(R.id.ourteam);
        ourTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                startActivity(intent);
            }
        });

        TextView webSite = view.findViewById(R.id.website);
        webSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebSiteWebActivity.class);
                startActivity(intent);
            }
        });


        TextView sponsers = view.findViewById(R.id.sponsers);
        sponsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebActivitySponsers.class);
                startActivity(intent);
            }
        });

        TextView contactUs = view.findViewById(R.id.contactUs);
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebActivitySec.class);
                startActivity(intent);
            }
        });

        PrivacyPolicy = (TextView) view.findViewById(R.id.PrivacyPolicy);
        PrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), com.coepzest.zest23.PrivacyPolicy.class);
                startActivity(intent);
            }
        });


        insta = view.findViewById(R.id.insta);
        twitter = view.findViewById(R.id.twitter);
        linkidin = view.findViewById(R.id.linkidin);
        youtube = view.findViewById(R.id.youtube);
        facebook = view.findViewById(R.id.facebook);


        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.instagram.com/coepzest/");
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://twitter.com/ZestCoep");
            }
        });
        linkidin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.linkedin.com/company/zest-coep/");
            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com/@coepzest2271");
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.facebook.com/coepzest");
            }
        });


//
        return view;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}