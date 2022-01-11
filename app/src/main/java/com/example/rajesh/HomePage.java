package com.example.rajesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);
    }
    public void logout(View view){
        Intent i8=new Intent(HomePage.this,MainActivity.class);
        startActivity(i8);
    }

    public void complaintadd(View view) {
        Intent i3 = new Intent(HomePage.this,NewComplaint.class);
        startActivity(i3);
    }

    public void complaintstatus(View view) {
        Intent i4 = new Intent(HomePage.this,ComplaintStatusUser.class);
        startActivity(i4);
    }
}