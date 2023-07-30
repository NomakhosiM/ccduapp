package com.example.ccduapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button logIn;
    private Button signUpClient;
    private Button signUpCouncilor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        logIn = (Button) findViewById(R.id.logIn);
        signUpClient = (Button) findViewById(R.id.signUpClient);
        signUpCouncilor = (Button) findViewById(R.id.signUpCouncilor);

        signUpClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openPostSignUp();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPostLogIn();
            }
        });
        signUpCouncilor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openCouncilSignUp();
            }
        });
    }


    // this method is evoked upon clicking the LogIn button
    public void onClick(View view) {
        openPostLogIn();
    }
    private void openPostLogIn() {
        Intent intent = new Intent(this, postLogIn.class);
        startActivity(intent);
    }

    // this method is evoked upon clicking the signUp button
    private void openPostSignUp() {
        Intent intent = new Intent(this, postSignUp.class);
        startActivity(intent);
    }
    private void openCouncilSignUp(){
        Intent intent = new Intent(this, councilSignUP.class);
        startActivity(intent);
    }
}