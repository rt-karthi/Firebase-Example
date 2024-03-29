package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    private TextView authenticate, real_text, real_image, cloud_firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        authenticate = findViewById(R.id.authenticate);
        real_text = findViewById(R.id.real_text);
        real_image = findViewById(R.id.real_image);
        cloud_firestore = findViewById(R.id.cloud_firestore);

        authenticate.setOnClickListener( v -> {
            startActivity(new Intent(DashboardActivity.this, SignUpActivity.class));
        });

        real_text.setOnClickListener( v -> {
            startActivity(new Intent(DashboardActivity.this, RealtimeDataBaseActivity.class));
        });

        real_image.setOnClickListener( v -> {
            startActivity(new Intent(DashboardActivity.this, ImageActivity.class));
        });
        cloud_firestore.setOnClickListener( v -> {
            startActivity(new Intent(DashboardActivity.this, FireStoreActivity.class));
        });
    }
}