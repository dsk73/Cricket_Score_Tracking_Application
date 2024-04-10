package com.example.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class LiveScore extends AppCompatActivity {

    View fragmentview;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    String [] countryname=new String[]{"Archery","Badminton","Basketball","Box Cricket","Carrom","Chess","Cricket","Fencing","Football","Handball","Hockey","Indoor Rowing","kabaddi","Kho-Kho","Table Tennis","Volleyball"};
    SearchView searchView;
    Spinner spinner;
    Bundle bundle=new Bundle();
    String TAG="Pratik";
    EditText TeamB,TeamA,MatchNo,Innings,Category;
    TextView EventName;

    private final int GalleryTeamA=1000,GalleryTeamB=2000;
    Uri uriTeamA,uriTeamB;
    String TeamALogoFilename,TeamBLogoFilename;


    ImageView TeamALogo,TeamBLogo;
    Button openGalleryA,openGalleryB,Upload;


    FirebaseStorage Storage;
    FirebaseFirestore Firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_score);

        EventName=findViewById(R.id.EventName);
        Category=findViewById(R.id.Category);
        Innings=findViewById(R.id.Innings);
        MatchNo=findViewById(R.id.MatchNo);
        TeamA=findViewById(R.id.TeamA);
        TeamB=findViewById(R.id.TeamB);
        TeamBLogo=findViewById(R.id.TeamBLogo);
        TeamALogo=findViewById(R.id.TeamALogo);
        openGalleryA=findViewById(R.id.TeamASelect);
        openGalleryB=findViewById(R.id.TeamBSelect);
        Upload=findViewById(R.id.Upload);


        spinner=findViewById(R.id.spinner);
        listView=findViewById(R.id.listviewitem);


        Storage=FirebaseStorage.getInstance();
        Firestore=FirebaseFirestore.getInstance();


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,countryname);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //set Selector Used to highlight what is selected
//                listView.setSelector(android.R.color.holo_orange_dark);
                EventName.setText(countryname[position]);
                spinner.setSelection(position, true);
//                sendEventName(countryname[position]);
            }
        });

//        ArrayAdapter<String> arrayAdapter1 =new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,countryname);
        ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countryname);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value=countryname[position].toString();
//                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
                listView.setSelector(R.drawable.ic_launcher_background);
//                listView.setSelection(position);     //To select the itemfrom list
                listView.smoothScrollToPosition(position);
                EventName.setText(countryname[position]);

//                sendEventName(countryname[position]);
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
            public void onClick(View v) {
                final StorageReference reference=Storage.getReference("Logo")
                        .child(TeamALogoFilename);
                String description=TeamA.getText().toString();
                StorageMetadata metadata = new StorageMetadata.Builder()
                        .setCustomMetadata("ImageDescription", description)
                        .build();
                reference.putFile(uriTeamA,metadata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        DocumentReference documentReference = Firestore.collection(EventName.getText().toString())
                                                .document("Live")
                                                .collection("MatchDetails")
                                                .document(Category.getText().toString() + MatchNo.getText().toString());
//                                documentReference.update("ImageUrl", FieldValue.arrayUnion(uri));
//                                documentReference.update("ImageUrl", FieldValue.arrayUnion(description));
                                        documentReference.update("Category",Category .getText().toString());
                                        documentReference.update("Innings",Innings .getText().toString());
                                        documentReference.update("MatchNo",Integer.valueOf(MatchNo .getText().toString()));
                                        documentReference.update("TeamA", TeamA.getText().toString());
                                        documentReference.update("TeamB", TeamB.getText().toString());
                                        documentReference.update("TeamALogo", uri);
                                        Log.d(TAG, "Team A: " + uri.toString());
                                        TeamA.setText("");

                                        final StorageReference reference1=Storage.getReference("Logo")
                                                .child(TeamBLogoFilename);
                                        String descriptionB=TeamB.getText().toString();
                                        StorageMetadata metadataB = new StorageMetadata.Builder()
                                                .setCustomMetadata("ImageDescription", descriptionB)
                                                .build();
                                        reference1.putFile(uriTeamB, metadataB).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        DocumentReference documentReference = Firestore.collection(EventName.getText().toString())
                                                                .document("Live")
                                                                .collection("MatchDetails")
                                                                .document(Category.getText().toString() + MatchNo.getText().toString());
                                                        documentReference.update("TeamBLogo", uri);
                                                        Log.d(TAG, "Team B: " + uri.toString());
                                                        TeamB.setText("");

                                                        DocumentReference documentReference1=Firestore.collection(EventName.getText().toString())
                                                                .document("Live")
                                                                .collection("Score")
                                                                .document(Category.getText().toString() + MatchNo.getText().toString());
                                                        documentReference1.update("TeamA", "0");
                                                        documentReference1.update("TeamB", "0");

                                                        Log.d(TAG, "Success");
//                                                        Intent intent=new Intent(EventGenerator.this,CricketScoreUpdate.class);
//                                                        intent.putExtra("EventName", "Cricket");
//                                                        startActivity(intent);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Error writing document ", e);
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
                uriTeamA=data.getData();
                TeamALogo.setImageURI(data.getData());
                TeamALogoFilename=getFileName(uriTeamA);
//                Log.d(TAG, TeamALogoFilename);
            }
            else if(requestCode==GalleryTeamB){
                uriTeamB=data.getData();
                TeamBLogo.setImageURI(data.getData());
                TeamBLogoFilename=getFileName(uriTeamB);
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