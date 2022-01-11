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

import java.util.ArrayList;
import java.util.List;

import static com.example.rajesh.MainActivity.globalusername;

public class ComplaintStatusUser extends AppCompatActivity {
    private RecyclerView usercomplaints;
    private List<UserComplaintModel> userReadNewsModelList;
    private UserComplaintAdapter userComplaintAdapter;

    FirebaseDatabase database;
    DatabaseReference getcomplaints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status_user);
        database = FirebaseDatabase.getInstance();
        getcomplaints = database.getReference().child("users").child(globalusername).child("complaintslist");
        usercomplaints = (RecyclerView) findViewById(R.id.usercomplaints);
        userReadNewsModelList = new ArrayList<>();
        getSupportActionBar().hide();
        userComplaintAdapter = new UserComplaintAdapter(userReadNewsModelList, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        usercomplaints.setLayoutManager(linearLayoutManager);
        usercomplaints.setAdapter(userComplaintAdapter);
        getcomplaints.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserComplaintModel userComplaintModel = snapshot.getValue(UserComplaintModel.class);
                userReadNewsModelList.add(userComplaintModel);
                userComplaintAdapter.notifyDataSetChanged();
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