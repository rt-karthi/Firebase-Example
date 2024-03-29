package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RealtimeDataBaseActivity extends AppCompatActivity {
    private EditText mName, mSurname, mEmail;
    private Button mSubmit, mRetrieve;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtimedatabase);

        mName = findViewById(R.id.et_Name);
        mSurname = findViewById(R.id.et_surname);
        mEmail = findViewById(R.id.et_Email);
        mSubmit = findViewById(R.id.btn_Submit);
        mRetrieve = findViewById(R.id.btn_Retrieve);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String surname = mSurname.getText().toString();
                String email = mEmail.getText().toString();

                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("name", name);
                userMap.put("surname", surname);
                userMap.put("email", email);

//                root.setValue(userMap);
//                root.child("01").setValue(name);

                root.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RealtimeDataBaseActivity.this, "Data Submitted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        mRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RealtimeDataBaseActivity.this, ShowActivity.class));
            }
        });
    }
}