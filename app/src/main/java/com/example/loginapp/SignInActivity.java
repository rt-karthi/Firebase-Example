package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private TextInputEditText mEmail, mPass;
    private TextView mSignUp;
    private Button mSignIn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mEmail = findViewById(R.id.et_Email);
        mPass = findViewById(R.id.et_Pass);
        mSignIn = findViewById(R.id.btn_Login);
        mSignUp = findViewById(R.id.tv_SignUp);
        mAuth = FirebaseAuth.getInstance();

        mSignUp.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });

        mSignIn.setOnClickListener(v -> {
            loginUser();
        });
    }

    private void loginUser() {
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!pass.isEmpty()) {
               mAuth.signInWithEmailAndPassword(email, pass)
                       .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                           @Override
                           public void onSuccess(AuthResult authResult) {
                               Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                               finish();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(SignInActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                           }
                       });
            } else {
                mPass.setError("Empty Fields are not Allowed");
            }
        } else if (email.isEmpty()) {
            mEmail.setError("Empty Fields are Allowed");
        } else {
            mEmail.setError("Please enter correct Email");
        }
    }
}