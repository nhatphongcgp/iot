package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
    private Intent i ;
    private EditText txtEmail,txtNumber,txtUsername;
    private DatabaseReference databaseReference;
    private String userName,email,number;

    @Override
    public void onBackPressed() {
        Intent j = new Intent(SecondActivity.this,MainActivity.class);
        startActivity(j);
        finish();
        super.onBackPressed();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button button = findViewById(R.id.btnSubmit);
        txtEmail = findViewById(R.id.editTxtEmail);
        txtNumber = findViewById(R.id.editTxtNumber);
        txtUsername = findViewById(R.id.editTxtUsername);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String user = "Users";
        databaseReference = firebaseDatabase.getReference(user);

        button.setText("Submit");
        i = getIntent();
        String s = i.getStringExtra("Button");
        switch (s) {
            case "Create":
                button.setOnClickListener(view -> {
                    userName = txtUsername.getText().toString();
                    email = txtEmail.getText().toString();
                    number = txtNumber.getText().toString();
                    databaseReference.child(userName).setValue(new DBHelper(userName, email, number));
                    Toast.makeText(SecondActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    i = new Intent(SecondActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                });
                break;
            case "Edit":
                String temp;
                HashMap<String, Object> hashMap = new HashMap<>();
                userName = i.getStringExtra("userName");
                temp = userName;
                email = i.getStringExtra("email");
                number = i.getStringExtra("number");
                txtUsername.setText(userName);
                txtEmail.setText(email);
                txtNumber.setText(number);
                button.setOnClickListener(v -> {
                    userName = txtUsername.getText().toString();
                    email = txtEmail.getText().toString();
                    number = txtNumber.getText().toString();
                    hashMap.put("userName", userName);
                    hashMap.put("email", email);
                    hashMap.put("number", number);
                    databaseReference.child(temp).updateChildren(hashMap).addOnCompleteListener(task -> {
                        i = new Intent(SecondActivity.this, MainActivity.class);
                        if (task.isSuccessful()) {
                            Toast.makeText(SecondActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SecondActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(i);
                        finish();
                    });
                });
                break;
            case "Read":
                userName = i.getStringExtra("userName");
                email = i.getStringExtra("email");
                number = i.getStringExtra("number");
                button.setText("OK");
                txtUsername.setText(userName);
                txtEmail.setText(email);
                txtNumber.setText(number);
                txtEmail.setEnabled(false);
                txtNumber.setEnabled(false);
                txtUsername.setEnabled(false);
                button.setOnClickListener(v -> {
                    i = new Intent(SecondActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                });
                break;

        }
    }

    @Override
    protected void onResume() {

        Log.println(Log.ASSERT,"ffd","fdf");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.println(Log.ASSERT,"ffd","fdffdfdfdfdfdf");
        super.onPause();
    }

}