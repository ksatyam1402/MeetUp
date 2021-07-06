package com.example.MeetUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class loginActivity extends AppCompatActivity {

    EditText emailBox, passwordBox;
    Button loginBtn, signupbtn;

    FirebaseAuth auth;
    ProgressDialog Dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Dialog = new ProgressDialog(this);
        Dialog.setMessage("Please wait !! ");


        auth = FirebaseAuth.getInstance();
        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);

        loginBtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.createBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailBox.getText().toString().trim().length() == 0 || passwordBox.getText().toString().trim().length() == 0) {
                    Toast.makeText(loginActivity.this, "Field(s) Empty..!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Dialog.show();
                    String email, password;
                    email = emailBox.getText().toString();
                    password = passwordBox.getText().toString();



                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Dialog.dismiss();

                                if (task.isSuccessful()) {
                                    //success
                                    // Toast.makeText(loginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(loginActivity.this, DashboardActivity.class));
                                } else {
                                    Toast.makeText(loginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                }
                }



        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this, SignupActivity.class));

            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}