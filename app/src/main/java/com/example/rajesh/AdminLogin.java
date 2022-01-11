package com.example.rajesh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_login);
        e1 = (EditText)findViewById(R.id.adminusername);
        e2 = (EditText)findViewById(R.id.adminpassword);
    }

    public void adminclicked(View view) {
        String uname = e1.getText().toString();
        String pass = e2.getText().toString();
        if(!uname.isEmpty() && !pass.isEmpty()){
            if(uname.equals("admin") && pass.equals("admin")){
                Intent correct  = new Intent(AdminLogin.this,AdminHomePage.class);
                startActivity(correct);
                e1.setText("");
                e2.setText("");
            }
            else{
                Toast.makeText(this, "Invalid Details", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Enter full Details", Toast.LENGTH_LONG).show();
        }
    }

    public void homepage(View view) {
        Intent home = new Intent(AdminLogin.this,MainActivity.class);
        startActivity(home);
    }
}