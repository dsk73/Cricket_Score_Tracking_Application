package com.example.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class PastMatchUpdator extends AppCompatActivity {

    ListView listView;
    String [] countryname=new String[]{"Archery","Badminton","Basketball","Box Cricket","Carrom","Chess","Cricket","Fencing","Football","Handball","Hockey","Kabaddi","Kho Kho","Rowing","Table Tennis","Volleyball"};
    Spinner spinner;
    String TAG="Pratik";
    EditText Category,Innings,MatchNo,TeamA,TeamAScore,TeamB,TeamBScore;
    TextView EventName;
    Button openGalleryA,openGalleryB,Upload;
    ImageView TeamALogo,TeamBLogo;

    ProgressDialog progressDialog;

    FirebaseStorage Storage=FirebaseStorage.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    private final int GalleryTeamA=1000,GalleryTeamB=2000;
    Uri LocalUriTeamA, LocalUriTeamB;
    Uri FirebaseUriTeamA,FirebaseUriTeamB;
    String TeamALogoFilename,TeamBLogoFilename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_match_updator);

        EventName=findViewById(R.id.EventName);
        Category=findViewById(R.id.Category);
        Innings=findViewById(R.id.Innings);
        MatchNo=findViewById(R.id.MatchNo);
        TeamA=findViewById(R.id.TeamA);
        TeamAScore=findViewById(R.id.TeamAScore);
        TeamB=findViewById(R.id.TeamB);
        TeamBScore=findViewById(R.id.TeamBScore);
        TeamBLogo=findViewById(R.id.TeamBLogo);
        TeamALogo=findViewById(R.id.TeamALogo);
        openGalleryA=findViewById(R.id.TeamASelect);
        openGalleryB=findViewById(R.id.TeamBSelect);
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

        openGalleryA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery=new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GalleryTeamA);
            }
        });

        openGalleryB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery=new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GalleryTeamB);
            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                final StorageReference referenceTeamA=Storage.getReference("Logo")
                        .child(TeamALogoFilename);
                String description=TeamA.getText().toString();
                StorageMetadata metadataTeamA = new StorageMetadata.Builder()
                        .setCustomMetadata("ImageDescription", description)
                        .build();
                referenceTeamA.putFile(LocalUriTeamA, metadataTeamA)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                referenceTeamA.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        FirebaseUriTeamA=uri;



                                        final StorageReference referenceTeamB=Storage.getReference("Logo")
                                                .child(TeamBLogoFilename);
                                        String descriptionB=TeamB.getText().toString();
                                        StorageMetadata metadataTeamB = new StorageMetadata.Builder()
                                                .setCustomMetadata("ImageDescription", descriptionB)
                                                .build();
                                        referenceTeamB.putFile(LocalUriTeamB,metadataTeamB)
                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        referenceTeamB.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {
                                                                FirebaseUriTeamB=uri;


                                                                Map<String, Object> data = new HashMap<>();
                                                                data.put("Category",Category.getText().toString());
                                                                data.put("Innings",Innings.getText().toString());
                                                                data.put("MatchNo", Integer.valueOf(MatchNo.getText().toString()));
                                                                data.put("TeamA",TeamA.getText().toString());
                                                                data.put("TeamAScore",TeamAScore.getText().toString());
                                                                data.put("TeamB",TeamB.getText().toString());
                                                                data.put("TeamBScore",TeamBScore.getText().toString());
                                                                data.put("TeamALogo",FirebaseUriTeamA);
                                                                data.put("TeamBLogo",FirebaseUriTeamB);

                                                                db.collection(EventName.getText().toString())
                                                                        .document("Timeline")
                                                                        .collection("Past Match")
                                                                        .document(Category.getText().toString() +
                                                                                MatchNo.getText().toString())
                                                                        .set(data)
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void unused) {
                                                                                Category.setText("");
                                                                                Innings.setText("");
                                                                                MatchNo.setText("");
                                                                                TeamA.setText("");
                                                                                TeamAScore.setText("");
                                                                                TeamB.setText("");
                                                                                TeamBScore.setText("");

                                                                                if(progressDialog.isShowing()){
                                                                                    progressDialog.dismiss();
                                                                                }
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Log.d(TAG, "Falied Updating Past Match");
                                                                                if(progressDialog.isShowing()){
                                                                                    progressDialog.dismiss();
                                                                                }
                                                                            }
                                                                        });

                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.d(TAG, "Failed TeamB URI" + TeamB.getText().toString());
                                                                if(progressDialog.isShowing()){
                                                                    progressDialog.dismiss();
                                                                }
                                                            }
                                                        });
                                                    }

                                                }) .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d(TAG, "Failed TeamB Storage: " + TeamB.getText().toString());
                                                        if(progressDialog.isShowing()){
                                                            progressDialog.dismiss();
                                                        }
                                                    }
                                                });



                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "Failed TeamA URI :" + TeamA.getText().toString());
                                        if(progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"Failed Task: ");
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==GalleryTeamA){
                LocalUriTeamA =data.getData();
                TeamALogo.setImageURI(data.getData());
                TeamALogoFilename=getFileName(LocalUriTeamA);
//                Log.d(TAG, TeamALogoFilename);
            }
            else if(requestCode==GalleryTeamB){
                LocalUriTeamB =data.getData();
                TeamBLogo.setImageURI(data.getData());
                TeamBLogoFilename=getFileName(LocalUriTeamB);
//                Log.d(TAG, TeamBLogoFilename);
            }
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}