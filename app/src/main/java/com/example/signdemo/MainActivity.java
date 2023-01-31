package com.example.signdemo;

//client id
//730520997077-36ndi6b762hf3tcs5n7geo2s7kdnhr1p.apps.googleusercontent.com

//client secret
//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


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account == null){ //NI UNTUK USER LOGIN
            //null: the user have not sign in yet
            Toast.makeText(this, "Please sign In with Gmail account", Toast.LENGTH_SHORT).show();
        }else{
            //user already sign in, so show the secret page
            //NI UNTUK BAGI DIA GERAK KE PAGE HOMEPAGE
            Intent intent = new Intent(this, homepage.class);
            intent.putExtra("Name",account.getDisplayName());
            intent.putExtra("Email",account.getEmail());

            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 10);
                break;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 10) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //BUKA ACTIVITY BARU, YANG RAHSIA TADI
            Toast.makeText(this, "already signed in",Toast.LENGTH_SHORT).show();

            //NI UNTUK BAGI DIA GERAK KE PAGE HOMEPAGE
            Intent intent = new Intent(this, homepage.class);
            intent.putExtra("Name",account.getDisplayName());
            intent.putExtra("Email",account.getEmail());

            startActivity(intent);


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ALAMAK", "signInResult:failed code=" + e.getStatusCode());

            //Takboleh sign in...
            Toast.makeText(this,"Alamak, tak boleh sign in laa...", Toast.LENGTH_SHORT).show();

            //updateUI(null);
        }
    }
}