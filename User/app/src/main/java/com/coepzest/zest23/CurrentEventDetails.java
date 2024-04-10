package com.coepzest.zest23;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.coepzest.zest23.R;
import com.google.android.material.tabs.TabLayout;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class CurrentEventDetails extends AppCompatActivity {

    private static final int Home = 0;
    private static final int Events = 1;
    private static final int Gallery = 2;
    //    private static final int Unbreakables = 3;
    private static final int POS_MAP = 3;
    private static final int ExploreMore = 5;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private CurrentEventDetailsAdapter adapter;
    String CurrentEvent;
    Toolbar toolbar;
    private SlidingRootNav slidingRootNav;

    String[] EventName = new String[]{"Archery", "Badminton", "Basketball", "Box Cricket", "Carrom", "Chess", "Cricket", "Fencing", "Football", "Handball", "Hockey", "Kabaddi", "Kho-Kho", "Lawn Tennis", "Rowing", "Swimming", "Table Tennis", "Taekwondo", "Volleyball"};
    EventsViewModel viewModel;

    String TAG = "Pratik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_event_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CurrentEvent = extras.getString("CurrentEvent");
        } else {
//            Log.d(TAG, CurrentEvent + "Failed to receive Data from events page to Tabbed activity");
//            CurrentEvent="Cricket";
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);


        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new CurrentEventDetailsAdapter(fragmentManager, getLifecycle(), CurrentEvent);
        viewPager2.setAdapter(adapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });




        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(CurrentEvent);





    }




}