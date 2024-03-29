package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.loginapp.adapter.FireStoreAdapter;
import com.example.loginapp.model.FireStoreModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FireStoreShowActivity extends AppCompatActivity {
    private RecyclerView fRecyclerView;
    private FirebaseFirestore db;
    private FireStoreAdapter adapter;
    private List<FireStoreModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestoreshow);

        fRecyclerView = findViewById(R.id.f_RecyclerView);
        fRecyclerView.setHasFixedSize(true);
        fRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        list = new ArrayList<>();
        adapter = new FireStoreAdapter(this, list);
        fRecyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(fRecyclerView);

        showData();
    }

    public void showData() {
        db.collection("Documents").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot: task.getResult()) {
                            FireStoreModel model = new FireStoreModel(snapshot.getString("id"),
                                    snapshot.getString("title"),
                                    snapshot.getString("description"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FireStoreShowActivity.this, "Oops ... Something went wrong !!", Toast.LENGTH_SHORT).show();
                    }
                }
                );
    }
}