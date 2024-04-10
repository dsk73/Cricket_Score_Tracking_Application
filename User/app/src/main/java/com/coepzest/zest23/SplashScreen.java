package com.coepzest.zest23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.coepzest.zest23.R;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        lottie=findViewById(R.id.lottieSplash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this,NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4800);

    }
}