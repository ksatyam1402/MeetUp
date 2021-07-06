package com.example.MeetUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;
    EditText emailBox, passwordBox, namebox;
    Button loginBtn, signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        emailBox = findViewById(R.id.emailBox);
        namebox = findViewById(R.id.namebox);
        passwordBox = findViewById(R.id.passwordBox);

        signupbtn = findViewById(R.id.createBtn);
        loginBtn = findViewById(R.id.loginbtn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailBox.getText().toString().trim().length() == 0 || passwordBox.getText().toString().trim().length() == 0 ||
                        namebox.getText().toString().trim().length() == 0) {
                    Toast.makeText(SignupActivity.this, "Field(s) Empty..!!", Toast.LENGTH_SHORT).show();
                } else {
                    String email, pass, name;
                    email = emailBox.getText().toString();
                    pass = passwordBox.getText().toString();
                    name = namebox.getText().toString();

                    User user = new User();
                    user.setEmail(email);
                    user.setPass(pass);
                    user.setName(name);

                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                database.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(SignupActivity.this, loginActivity.class));
                                        Toast.makeText(SignupActivity.this, "Account created !!", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                //                  Toast.makeText(SignupActivity.this, "Account is Created", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, loginActivity.class));

            }
        });
    }

}