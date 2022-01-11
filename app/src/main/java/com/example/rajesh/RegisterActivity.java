package com.example.rajesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText fullname;
    EditText email;
    EditText phone;
    EditText password;
    EditText confirmPassword;
    Button regButton;
    TextView loginQuestion;

    FirebaseDatabase database;
    DatabaseReference myrefRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
    }

    public void register(View view) {
        String name = fullname.getText().toString();
        String emailid = email.getText().toString();
        String mobile = phone.getText().toString();
        String pass = password.getText().toString();
        String confpass = confirmPassword.getText().toString();

        myrefRegister = database.getReference().child("users");
        if(!name.isEmpty() && !emailid.isEmpty() && !mobile.isEmpty() && !pass.isEmpty() && !confpass.isEmpty()){
            myrefRegister.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    if(datasnapshot.child(emailid.toLowerCase()).exists()){
                        Toast.makeText(RegisterActivity.this, "User Already Exists", Toast.LENGTH_LONG).show();
                        fullname.setText("");
                        password.setText("");
                        confirmPassword.setText("");
                        phone.setText("");
                        email.setText("");
                    }
                    else{
                        if(pass.equals(confpass)){
                            myrefRegister.child(emailid.toLowerCase()).child("username").setValue(fullname.getText().toString());
                            myrefRegister.child(emailid.toLowerCase()).child("emailid").setValue(email.getText().toString().toLowerCase());
                            myrefRegister.child(emailid.toLowerCase()).child("password").setValue(password.getText().toString());
                            myrefRegister.child(emailid.toLowerCase()).child("phonenumber").setValue(phone.getText().toString());
                            Toast.makeText(RegisterActivity.this, "Registration Successfull", Toast.LENGTH_LONG).show();
                            fullname.setText("");
                            password.setText("");
                            confirmPassword.setText("");
                            phone.setText("");
                            email.setText("");
                            Intent done = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(done);
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Passwords Not Matching", Toast.LENGTH_LONG).show();
                        }
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

    public void registerlogin(View view) {
        Intent i1 = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(i1);
    }

}