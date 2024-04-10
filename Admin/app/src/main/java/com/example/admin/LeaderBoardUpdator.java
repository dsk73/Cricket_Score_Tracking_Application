package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class LeaderBoardUpdator extends AppCompatActivity {

    ListView listView;
    String [] countryname=new String[]{"Archery","Badminton","Basketball","Box Cricket","Carrom","Chess","Cricket","Fencing","Football","Handball","Hockey","Kabaddi","Kho Kho","Rowing","Table Tennis","Volleyball"};
    Spinner spinner;
    String TAG="Pratik";
    EditText Points,Position,Team;
    TextView EventName,Category;
    Button Boys,Girls,Upload;
    Boolean flag;

    ProgressDialog progressDialog;

    FirebaseStorage Storage=FirebaseStorage.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board_updator);

        EventName=findViewById(R.id.EventName);
        Points=findViewById(R.id.Points);
        Position=findViewById(R.id.Position);
        Team=findViewById(R.id.Team);
        Category=findViewById(R.id.Category);
        Boys=findViewById(R.id.Boys);
        Girls=findViewById(R.id.Girls);
        Upload=findViewById(R.id.Upload);
        spinner=findViewById(R.id.spinner);
        listView=findViewById(R.id.listviewitem);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading Data");


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,countryname);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventName.setText(countryname[position]);
                spinner.setSelection(position, true);
            }
        });

        ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countryname);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value=countryname[position].toString();
                listView.setSelector(R.drawable.ic_launcher_background);
                listView.smoothScrollToPosition(position);
                EventName.setText(countryname[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Boys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category.setText("Boys");
            }
        });
        Girls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category.setText("Girls");
            }
        });

//        Upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(check()) {
//                    Map<String, Object> data = new HashMap<>();
//                    data.put("Team", Team.getText().toString());
//                    data.put("Position", Integer.valueOf(Position.getText().toString()));
//                    data.put("Points", Integer.valueOf(Points.getText().toString()));
//
//                    progressDialog.show();
//                    db.collection(EventName.getText().toString())
//                            .document("LeaderBoard")
//                            .collection(Category.getText().toString())
//                            .document(Team.getText().toString())
//                            .set(data)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Log.d(TAG, Team.getText().toString());
//                                    Team.setText("");
//                                    Points.setText("");
//                                    Position.setText("");
//                                    Category.setText("");
//                                    if (progressDialog.isShowing()) {
//                                        progressDialog.dismiss();
//                                    }
//                                Toast.makeText(LeaderBoardUpdator.this, Team.getText().toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    if (progressDialog.isShowing()) {
//                                        progressDialog.dismiss();
//                                    }
////                                    Log.d(TAG, "Failed" + Team.getText().toString());
//                                Toast.makeText(LeaderBoardUpdator.this, "Failed" + Team.getText().toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                }
//                else{
//
//                }
//            }
//
//            private boolean check() {
//                if(Points.getText().toString().isEmpty() ||
//                Position.getText().toString().isEmpty() ||
//                Team.getText().toString().isEmpty() ||
//                Category.getText().toString().isEmpty()){
//                    Toast.makeText(LeaderBoardUpdator.this, "Incomplete Fields", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                return true;
//            }
//        });


        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value=Team.getText().toString();
//                on succes
                Team.setText("");
            }

        });



    }

    private Boolean alertbox() {
        flag = false;
        new AlertDialog.Builder(this)
                .setMessage(Team.getText().toString() + "\n" +
                            "Points: " + Points.getText().toString() + "\n" +
                            "Position" + Position.getText().toString())
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flag=true;
                        Log.d(TAG, "onClick: " + flag.toString());
                    }
                })
                .setNegativeButton("No",null)
                .show();
        Log.d(TAG, flag.toString());
        return flag;
    }
}