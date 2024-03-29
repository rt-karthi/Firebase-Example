package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.loginapp.adapter.ImageAdapter;
import com.example.loginapp.model.ImageModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowImageActivity extends AppCompatActivity {
    private RecyclerView iRecyclerview;
    private ArrayList<ImageModel> list;
    private ImageAdapter adapter;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);

        iRecyclerview = findViewById(R.id.iRecyclerView);
        iRecyclerview.setHasFixedSize(true);
        iRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new ImageAdapter(this, list);
        iRecyclerview.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ImageModel model = dataSnapshot.getValue(ImageModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}