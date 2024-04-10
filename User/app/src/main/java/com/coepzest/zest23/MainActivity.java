package com.coepzest.zest23;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.coepzest.zest23.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements NavigationAdapter.OnItemSelectedListener {

    private static final int Home = 0;
    private static final int Events = 1;
    private static final int Gallery = 2;
    //    private static final int Unbreakables = 3;
    private static final int POS_MAP = 3;
    private static final int ExploreMore = 5;


    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    Toolbar toolbar;
    String[] EventName = new String[]{"Archery", "Badminton", "Basketball", "Box Cricket", "Carrom", "Chess", "Cricket", "Fencing", "Football", "Handball", "Hockey", "Kabaddi", "Kho-Kho", "Lawn Tennis", "Rowing", "Swimming", "Table Tennis", "Taekwondo", "Volleyball"};
    EventsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewModel = new ViewModelProvider(this).get(EventsViewModel.class);
        viewModel.getSelectedEvent().observe(this, item -> {
            toolbar.setTitle(item);
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        });


        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.navigation_left_drawer)
                .inject();


        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();


        NavigationAdapter adapter = new NavigationAdapter(Arrays.asList(
                createItemFor(Home).setChecked(true),
                createItemFor(Events),
                createItemFor(Gallery),
                createItemFor(POS_MAP),
                new NavigationSpaceItem(45),
                createItemFor(ExploreMore)));
        adapter.setListener(this);


        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(Home);


        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }

                    }
                });


    }



    @Override
    public void onItemSelected(int position) {
        slidingRootNav.closeMenu();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (position == Home) {
//            toolbar.setTitle("Home");
//            Title.setText("Home");
            HomePage homePage = new HomePage();
            transaction.replace(R.id.container, homePage).commit();
        } else if (position == Events) {
//            toolbar.setTitle("Events");
//            toolbar.setLogo(R.drawable.ic_launcher_foreground);
//            Title.setText("Go Zest!!!!!!!!!!!!!!!!!");
            EventsPage eventsPage = new EventsPage();
            transaction.replace(R.id.container, eventsPage).commit();
//              AllEvents eventsPage = new AllEvents();
//              transaction.replace(R.id.container, eventsPage).commit();

        } else if (position == POS_MAP) {
            MapPage mapPage = new MapPage();
            transaction.replace(R.id.container, mapPage).commit();
        } else if (position == Gallery) {
            Gallery gallery = new Gallery();
            transaction.replace(R.id.container, gallery).commit();
        } else if (position == ExploreMore) {
            ExploreMore exploreMore = new ExploreMore();
            transaction.replace(R.id.container, exploreMore).commit();
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private NavigationItem createItemFor(int position) {
        return new NavigationSimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.NavigationItems);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray typedArray = getResources().obtainTypedArray(R.array.NavigationIcons);
        Drawable[] icons = new Drawable[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            int arrayResourceId = typedArray.getResourceId(i, 0);
            if (arrayResourceId != 0) {
                icons[i] = ContextCompat.getDrawable(this, arrayResourceId);
            }
        }
        typedArray.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


}