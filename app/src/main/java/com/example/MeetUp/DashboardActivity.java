package com.example.MeetUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {

    EditText secretcodebox;
    Button joinBtn, shareBtn;
    Button logout, verifyBtn;
    FirebaseAuth auth;
    CardView verify;
    TextView verifytxt;

    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        secretcodebox = findViewById(R.id.codebox);
        logout= findViewById(R.id.logout);
        joinBtn = findViewById(R.id.joinBtn);
        shareBtn = findViewById(R.id.shareBtn);
        auth = FirebaseAuth.getInstance();
        verifyBtn = findViewById(R.id.verifyBtn);
        verify = findViewById(R.id.verify);
        verifytxt = findViewById(R.id.verifytxt);
        if(!auth.getCurrentUser().isEmailVerified()){
            verifyBtn.setVisibility(View.VISIBLE);
            verify.setVisibility(View.VISIBLE);
            verifytxt.setVisibility(View.VISIBLE);
            joinBtn.setEnabled(false);
            shareBtn.setEnabled(false);
            logout.setEnabled(false);
            secretcodebox.setEnabled(false);
        }
        URL serverURL;
        
        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions;
            defaultOptions = new JitsiMeetConferenceOptions.Builder().setServerURL(serverURL)
                    .setWelcomePageEnabled(false).build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder().setRoom(secretcodebox.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(DashboardActivity.this, options);

            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareBody = "Join my meeting using this secret code :- ";
                String code = secretcodebox.getText().toString();
                String fin = shareBody + code;
              Intent shareIntent = new Intent(Intent.ACTION_SEND);
              shareIntent.setType("text/plain");
              shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
              shareIntent.putExtra(Intent.EXTRA_TEXT,fin);

              startActivity(Intent.createChooser(shareIntent, "Share using :  "));
                
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardActivity.this , loginActivity.class));
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DashboardActivity.this, "Verification email sent..!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DashboardActivity.this, loginActivity.class));
                    }
                });
            }
        });
        
    }


}