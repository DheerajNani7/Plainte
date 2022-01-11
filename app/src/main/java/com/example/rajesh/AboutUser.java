package com.example.rajesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutUser extends AppCompatActivity {

    EditText e1;
    TextView t1,t2,t3;
    DatabaseReference getData;

    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about_user);
        e1 = (EditText)findViewById(R.id.usernamegetdata);
        t1 = (TextView)findViewById(R.id.unamedisplay);
        t3 = (TextView)findViewById(R.id.phonedisplay);
        database = FirebaseDatabase.getInstance();
    }

    public void get(View view) {
        getData = database.getReference().child("users");
        String uname = e1.getText().toString();
        if(!uname.isEmpty()){
            getData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(uname).exists()){
                        String fullname = snapshot.child(uname).child("username").getValue().toString();
                        String mobile = snapshot.child(uname).child("phonenumber").getValue().toString();
                        t1.setText(fullname);
                        t3.setText(mobile);
                        e1.setText("");
                    }
                    else{
                        Toast.makeText(AboutUser.this, "User Not Found", Toast.LENGTH_LONG).show();
                        t1.setText("");
                        t2.setText("");
                        t3.setText("");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            Toast.makeText(this, "Enter Username", Toast.LENGTH_LONG).show();
        }

    }
}