package com.coepzest.zest23;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.coepzest.zest23.R;

public class PrivacyPolicy extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("Privacy Policy");
        }

    }
}