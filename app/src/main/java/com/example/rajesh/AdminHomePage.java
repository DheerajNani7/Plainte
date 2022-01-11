package com.example.rajesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_home_page);
    }

    public void verifycomplaint(View view) {
        Intent open = new Intent(AdminHomePage.this,AdminVerifyComplaint.class);
        startActivity(open);
    }

    public void manageusers(View view) {
        Intent user = new Intent(AdminHomePage.this,AboutUser.class);
        startActivity(user);
    }
}