package com.example.signdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView link=findViewById(R.id.textView3);
        link.setOnClickListener((view)->{
            Uri uri = Uri.parse("https://github.com/izzahazri/Hazard-News");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });
    }
}