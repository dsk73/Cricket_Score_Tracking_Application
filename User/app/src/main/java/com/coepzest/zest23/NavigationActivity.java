package com.coepzest.zest23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coepzest.zest23.R;

public class NavigationActivity extends AppCompatActivity {

    ViewPager slideViewPager;
    LinearLayout dotIndicator;
    Button backButton, nextButton, skipButton;
    TextView[] dots;
    ViewPageAdapter viewPageAdapter;





    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);
            if (position > 0) {
                backButton.setVisibility(View.VISIBLE);
            } else {
                backButton.setVisibility(View.INVISIBLE);
            }
            if (position == 2){
                nextButton.setText("Finish");
            } else {
                nextButton.setText("Next");
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if(restorePrefData()){
            Intent mainAcivity = new Intent(getApplicationContext() , MainActivity.class);
            startActivity(mainAcivity);
            finish();
        }

        setContentView(R.layout.activity_navigation);

        backButton = findViewById(R.id.backkButton);
        nextButton = findViewById(R.id.nexttButton);
        skipButton = findViewById(R.id.skipButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) > 0) {
                    slideViewPager.setCurrentItem(getItem(-1), true);
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) < 2)
                    slideViewPager.setCurrentItem(getItem(1), true);
                else {
                    Intent i = new Intent(NavigationActivity.this, MainActivity.class);
                    savePrefaData();
                    startActivity(i);

                    finish();
                }
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NavigationActivity.this, MainActivity.class);
                savePrefaData();
                startActivity(i);

                finish();

            }
        });
        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotIndicator = (LinearLayout) findViewById(R.id.dotIndicator);
        viewPageAdapter = new ViewPageAdapter(this);
        slideViewPager.setAdapter(viewPageAdapter);
        setDotIndicator(0);
        slideViewPager.addOnPageChangeListener(viewPagerListener);
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened",false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefaData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }


    public void setDotIndicator(int position){

        dots = new TextView[3];
        dotIndicator.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.grey, getApplicationContext().getTheme()));
            dotIndicator.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.lavender, getApplicationContext().getTheme()));
    }

    private int getItem(int i) {
        return slideViewPager.getCurrentItem() + i;
    }
}