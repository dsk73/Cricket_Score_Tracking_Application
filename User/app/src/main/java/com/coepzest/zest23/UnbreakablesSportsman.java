package com.coepzest.zest23;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.coepzest.zest23.R;

public class UnbreakablesSportsman extends AppCompatActivity {

    String Name;
    ImageView PlayerImage;
    TextView PlayerInfo;
    String TAG="Pratik";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbreakables_sportsman);

        Name=getIntent().getStringExtra("Name");
        PlayerImage=findViewById(R.id.PlayerImage);
        PlayerInfo=findViewById(R.id.PlayerInfo);

        if(Name.equals("Dinesh")){
            PlayerImage.setImageResource(R.drawable.dinesh_big);
            String info=getResources().getString(R.string.Dinesh);
            PlayerInfo.setText(info);
        }else if(Name.equals("Ali")){
            PlayerImage.setImageResource(R.drawable.ic_launcher_foreground);
            String info=getResources().getString(R.string.Ali);
            PlayerInfo.setText(info);
        }else if(Name.equals("Pullela")){
            PlayerImage.setImageResource(R.drawable.pullela_big);
            String info=getResources().getString(R.string.Pullela);
            PlayerInfo.setText(info);
        }else if(Name.equals("Bobby")){
            PlayerImage.setImageResource(R.drawable.bobby_big);
            String info=getResources().getString(R.string.Bobby);
            PlayerInfo.setText(info);
        }else if(Name.equals("Matthias")){
            PlayerImage.setImageResource(R.drawable.ic_launcher_foreground);
            String info=getResources().getString(R.string.Matthias);
            PlayerInfo.setText(info);
        }else if(Name.equals("Vignesh")){
            PlayerImage.setImageResource(R.drawable.ic_launcher_foreground);
            String info=getResources().getString(R.string.Vignesh);
            PlayerInfo.setText(info);
        }else if(Name.equals("Roger")){
            PlayerImage.setImageResource(R.drawable.ic_launcher_foreground);
            String info=getResources().getString(R.string.Roger);
            PlayerInfo.setText(info);
        }else{
            PlayerImage.setImageResource(R.drawable.aries_big);
            String info=getResources().getString(R.string.Aries);
            PlayerInfo.setText(info);
        }



    }
}