package com.coepzest.zest23;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CurrentEventDetailsAdapter extends FragmentStateAdapter {

    String CurrentEvent;
    public CurrentEventDetailsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String CurrentEvent) {
        super(fragmentManager, lifecycle);
        this.CurrentEvent=CurrentEvent;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle=new Bundle();
        bundle.putString("CurrentEvent",CurrentEvent);
        if(position==0){
            if(CurrentEvent.equals("Cricket") || CurrentEvent.equals("Box Cricket")){
                CricketLiveScore cricketLiveScore=new CricketLiveScore();
                cricketLiveScore.setArguments(bundle);
                return cricketLiveScore;
            }
            CurrentEventLiveScore currentEventLiveScore=new CurrentEventLiveScore();
            currentEventLiveScore.setArguments(bundle);
            return currentEventLiveScore;
        }

            CurrentEventPastMatch currentEventPastMatch=new CurrentEventPastMatch();
            currentEventPastMatch.setArguments(bundle);
            return currentEventPastMatch;

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
