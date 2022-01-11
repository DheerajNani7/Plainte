package com.example.rajesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static com.example.rajesh.MainActivity.globalusername;

public class profile extends AppCompatActivity {
    TextView t1,t2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String name,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        t1 = (TextView)findViewById(R.id.namefromdb);
        t2 = (TextView)findViewById(R.id.mobilefromdb);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child("dhoni");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(globalusername).exists()){
                    name = snapshot.child(globalusername.toLowerCase()).child("username").getValue().toString();
                    phone = snapshot.child(globalusername.toLowerCase()).child("phonenumber").getValue().toString();
                    System.out.println("Name is :"+name);
                }
                t1.setText(name);
                t2.setText(phone);
                // run chey
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void backtomain(View view) {
        Intent i6=new Intent(profile.this, HomePage.class);
        startActivity(i6);
    }

    public void logoutbutton(View view) {
        Intent i5=new Intent(profile.this, MainActivity.class);
        startActivity(i5);
    }
}