package com.example.rajesh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class AdminVerifyComplaint extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    DatabaseReference mrefadmin;
    RecyclerView recyclerView;

    AdminVerifyAdapter adminVerifyAdapter;
    List<AdminComplaintModel> adminComplaintModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_verify_complaint);
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        getSupportActionBar().hide();
        // reference will be received from previous page : zones page
        mrefadmin = database.getReference("complaints");
        recyclerView = findViewById(R.id.adminverify);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adminComplaintModelList = new ArrayList<>();
        adminVerifyAdapter = new AdminVerifyAdapter(AdminVerifyComplaint.this,adminComplaintModelList);
        recyclerView.setAdapter(adminVerifyAdapter);

        mrefadmin.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AdminComplaintModel adminComplaintStatusModel = snapshot.getValue(AdminComplaintModel.class);
                adminComplaintModelList.add(adminComplaintStatusModel);
                adminVerifyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}