package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity{

    private DatabaseReference databaseReference;
    private static final ArrayList<DBHelper> arrayList  = new ArrayList<>();
    private RecyclerView list;
    @SuppressLint("StaticFieldLeak")
    public static Activity Fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList.clear();
        Fa =this;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        getUsers();
        Button btnCreate = findViewById(R.id.btn_CreateMain);
        list = findViewById(R.id.list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                new LinearLayoutManager(this).getOrientation());
        list.addItemDecoration(dividerItemDecoration);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new ListAdapter(arrayList,this ));
        btnCreate.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this,SecondActivity.class);
            String s;
            s="Create";
            i.putExtra("Button",s);
            startActivity(i);
            finish();
        });

    }
    public void getUsers(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            String userName, email, number;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    userName = Objects.requireNonNull(dataSnapshot.child("userName").getValue()).toString();
                    email = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                    number = Objects.requireNonNull(dataSnapshot.child("number").getValue()).toString();
                    arrayList.add(new DBHelper(userName,email,number));
                }
                list.setAdapter(new ListAdapter(arrayList,MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static void cleanList(){
        arrayList.clear();
    }
}