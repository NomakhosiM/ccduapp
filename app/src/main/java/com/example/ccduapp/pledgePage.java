package com.example.ccduapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import okhttp3.OkHttpClient;

public class pledgePage extends AppCompatActivity {

    private Button pledgeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge_page);
        pledgeBtn = (Button) findViewById(R.id.pledgeBtn);

        pledgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {openMainActivity();
                //must make this button store username and password into database



            }
        });
}

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}