package com.example.signdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class homepage extends AppCompatActivity implements View.OnClickListener{

    GoogleSignInClient mGoogleSignInClient;
    String name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        TextView tvName = (TextView) findViewById(R.id.tvname);
        TextView tvEmail = (TextView) findViewById(R.id.tvemail);

        name = getIntent().getStringExtra("Name");
        email = getIntent().getStringExtra("Email");

        tvName.setText(name);
        tvEmail.setText(email);

        Button signout = findViewById(R.id.signout);
        signout.setOnClickListener(this);

        //YANG INI AMBIL DARI MAINACTIVITY.JAVA
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.btnMap).setOnClickListener((view)->{
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnAboutUs).setOnClickListener((v -> {
            startActivity(new Intent(this, AboutUs.class));
        }));
        findViewById(R.id.btnNews).setOnClickListener(v -> {
            startActivity(new Intent(this, NewsActivity.class));
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signout:
                signOut();
                break;
        }
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), email+" signed out",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
