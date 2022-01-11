package com.example.rajesh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    public static String globalusername = null;
    FirebaseDatabase database1;
    DatabaseReference myrefLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setTitle("Login");

        database1 = FirebaseDatabase.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

    }


    public void login(View view) {
        String uname = email.getText().toString();
        String pass = password.getText().toString();

        myrefLogin = database1.getReference().child("users");
        if(!uname.isEmpty() && !pass.isEmpty()){
            myrefLogin.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(uname.toLowerCase()).exists()){
                        String reqpassword = snapshot.child(uname.toLowerCase()).child("password").getValue().toString();
                        if(pass.equals(reqpassword)){
                            globalusername = uname.toLowerCase();
                            Toast.makeText(MainActivity.this, "LoggedIn Successfully", Toast.LENGTH_LONG).show();
                            email.setText("");
                            password.setText("");
                            Intent loggedInto = new Intent(MainActivity.this,HomePage.class);
                            startActivity(loggedInto);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                            email.setText("");
                            password.setText("");
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "User Not Found", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            Toast.makeText(this, "Enter Complete Details", Toast.LENGTH_LONG).show();
        }

    }

    public void signup(View view) {
        Intent i2 = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(i2);
    }

    public void adminlogin(View view) {
        Intent admin = new Intent(MainActivity.this,AdminLogin.class);
        startActivity(admin);
    }
}