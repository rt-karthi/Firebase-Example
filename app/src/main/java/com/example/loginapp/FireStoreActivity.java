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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class FireStoreActivity extends AppCompatActivity {
    private EditText mTitle, mDescription;
    private Button f_save, f_show;
    private FirebaseFirestore db;
    private String uId, uTitle, uDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);

        mTitle = findViewById(R.id.et_title);
        mDescription = findViewById(R.id.et_description);
        f_save = findViewById(R.id.f_btn_save);
        f_show = findViewById(R.id.f_btn_show);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            f_save.setText("update");

            uId = bundle.getString("uId");
            uTitle = bundle.getString("uTitle");
            uDescription = bundle.getString("uDescription");

            mTitle.setText(uTitle);
            mDescription.setText(uDescription);
        } else {
            f_save.setText("Save");
        }

        f_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FireStoreActivity.this, FireStoreShowActivity.class));
            }
        });

        f_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null){
                    String id = uId;
                    updateToFireStore(id, title, description);
                } else {
                    String id = UUID.randomUUID().toString();

                    saveToFireStrore(id, title, description);
                }
            }
        });
    }

    private void updateToFireStore(String id, String title, String description) {
        db.collection("Documents").document(id).update("title", title,
                "description", description)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FireStoreActivity.this, "Data Updated !!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FireStoreActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FireStoreActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveToFireStrore(String id, String title, String description) {
        if (!title.isEmpty() && !description.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("title", title);
            map.put("desciption", description);

            db.collection("Documents").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(FireStoreActivity.this, "Data Saved !!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FireStoreActivity.this, "Failed !!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
        }
    }
}