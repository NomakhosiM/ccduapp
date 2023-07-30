package com.example.ccduapp;

import static com.example.ccduapp.R.id.nextPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.util.Patterns;

public class postSignUp extends AppCompatActivity {
    OkHttpClient client;
    String postURL = "https://lamp.ms.wits.ac.za/home/s2500339/app_clt_signup.php";
    private TextView responseTxt;
    private EditText setUsername;
    private EditText setPassword;
    private Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_sign_up);

        client = new OkHttpClient();
        responseTxt = (TextView) findViewById(R.id.responseTxt);
        setUsername = (EditText) findViewById(R.id.setUsername);
        setPassword = (EditText) findViewById(R.id.setPassword);
        nextPage = (Button) findViewById(R.id.nextPage);

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL = "https://lamp.ms.wits.ac.za/home/s2500339/app_clt_signup.php";
                String Username = (String) setUsername.getText().toString().trim();
                String Password = (String) setPassword.getText().toString();
//                openClientProblem();
                if(Username.isEmpty()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(postSignUp.this,"Invalid username",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RequestBody postCouncilSignUp = new FormBody.Builder()
                                    .add("uname", Username)
                                    .add("pw", Password)
                                    .build();
                            Request request = new Request.Builder()
                                    .url(URL)
                                    .post(postCouncilSignUp)
                                    .build();

                            OkHttpClient client = new OkHttpClient();
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    final String responseData = response.body().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (responseData.equals("-1")) {
                                                Toast.makeText(postSignUp.this, "User already Exists", Toast.LENGTH_SHORT).show();
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(postSignUp.this, responseData, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                Intent i = new Intent(postSignUp.this, clientProblem.class);
                                                startActivity(i);
                                            }
                                        }
                                    });
                                }
                            });


                        }
                    }).start();


                }
            }
        });
    }

}