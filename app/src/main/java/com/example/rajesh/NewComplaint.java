package com.example.rajesh;

import static com.example.rajesh.MainActivity.globalusername;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;

public class NewComplaint extends AppCompatActivity {

    EditText e1;
    ImageView imageView;
    String typecomplaint;
    StorageReference mStorage;
    DatabaseReference mRefComplaint;
    DatabaseReference mRefUserDetails;
    Uri FilePathUri;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);
        e1 = (EditText)findViewById(R.id.description);
        imageView = (ImageView)findViewById(R.id.image_view1);
        Spinner mySpinner = findViewById(R.id.complaintType);
        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(NewComplaint.this);

        mStorage = FirebaseStorage.getInstance().getReference("complaints");

        mRefComplaint = FirebaseDatabase.getInstance().getReference("complaints");

        mRefUserDetails = FirebaseDatabase.getInstance().getReference("users");

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NewComplaint.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.type));
        // create array in Strings.xml and add it to here
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                typecomplaint = (String)parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void chooseimage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), Image_Request_Code);
    }

    public void registercomplaint(View view) {
        String des = e1.getText().toString();
        String comptype = typecomplaint;
        if(!des.isEmpty() && !comptype.isEmpty()){
            UploadImage();
        }
        else{
            Toast.makeText(this, "Enter Complete Details", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null){

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public void UploadImage() {

        if (FilePathUri != null) {
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = mStorage.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> downloaduri = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();
                                    progressDialog.dismiss();
                                    DatabaseReference newPost = mRefComplaint.push();
                                    String desc = e1.getText().toString();
                                    String type = typecomplaint;
                                    String ImageUploadId = mRefComplaint.push().getKey();
                                    mRefComplaint.child(ImageUploadId).child("type").setValue(type);
                                    mRefComplaint.child(ImageUploadId).child("complaintid").setValue(ImageUploadId);
                                    mRefComplaint.child(ImageUploadId).child("description").setValue(desc);
                                    mRefComplaint.child(ImageUploadId).child("username").setValue(globalusername);
                                    mRefComplaint.child(ImageUploadId).child("date").setValue(new Date().toString().substring(0,11));
                                    mRefComplaint.child(ImageUploadId).child("imageURL").setValue(task.getResult().toString());
                                    mRefComplaint.child(ImageUploadId).child("status").setValue("pending");
                                    // adding data to the user : compliant section to show the status complaint
                                    mRefUserDetails.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            mRefUserDetails.child(globalusername).child("complaintslist").child(ImageUploadId).child("status").setValue("pending");
                                            mRefUserDetails.child(globalusername).child("complaintslist").child(ImageUploadId).child("type").setValue(typecomplaint);
                                            mRefUserDetails.child(globalusername).child("complaintslist").child(ImageUploadId).child("description").setValue(desc);
                                            mRefUserDetails.child(globalusername).child("complaintslist").child(ImageUploadId).child("complaintid").setValue(ImageUploadId);
                                            mRefUserDetails.child(globalusername).child("complaintslist").child(ImageUploadId).child("imageURL").setValue(task.getResult().toString());
                                            mRefUserDetails.child(globalusername).child("complaintslist").child(ImageUploadId).child("date").setValue(new Date().toString().substring(0,11));

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    e1.setText("");
                                    Toast.makeText(getApplicationContext(), "Complaint Filed Successfully ", Toast.LENGTH_LONG).show();
                                }
                            });
//
                            Intent upload = new Intent(NewComplaint.this,HomePage.class);
                            startActivity(upload);
//
                        }
                    });
        }
        else {

            Toast.makeText(NewComplaint.this, "Please Select Image", Toast.LENGTH_LONG).show();

        }
    }
}