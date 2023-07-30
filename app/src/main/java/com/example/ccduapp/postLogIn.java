package com.example.ccduapp;

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

public class postLogIn extends AppCompatActivity {

    OkHttpClient client;
    String clientURL = "https://lamp.ms.wits.ac.za/home/s2500339/app_clt_login.php";
    String councilURL = "https://lamp.ms.wits.ac.za/home/s2500339/app_cllr_login.php";
    private Button clientLogIn;
    private Button councilLogIn;
    private EditText enterUsername;
    private EditText enterPW;
    private TextView responseText1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        responseText1 = (TextView) findViewById(R.id.responseText1);
        clientLogIn = (Button) findViewById(R.id.clientLogIn);
        councilLogIn = (Button) findViewById(R.id.councilLogin);
        enterPW = (EditText) findViewById(R.id.enterPW);
        enterUsername = (EditText) findViewById(R.id.enterUsername);

        clientLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clientURL = "https://lamp.ms.wits.ac.za/home/s2500339/app_clt_login.php";
                String entUsername = enterUsername.getText().toString();
                String entPW = enterPW.getText().toString();
                //openChatWindow();
                if (entUsername.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(postLogIn.this,"Invalid username",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RequestBody postClientLogIn = new FormBody.Builder()
                                    .add("uname", entUsername)
                                    .add("pw", entPW)

                                    .build();
                            Request request = new Request.Builder()
                                    .url(clientURL)
                                    .post(postClientLogIn)
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

                                    if (!responseData.equals("-1")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        String assign_url = "https://lamp.ms.wits.ac.za/home/s2500339/app_assign.php";
                                                        RequestBody signupbody = new FormBody.Builder()
                                                                .add("clt_no", responseData.toString())
                                                                .build();
                                                        Request request = new Request.Builder()
                                                                .url(assign_url)
                                                                .post(signupbody)
                                                                .build();

                                                        OkHttpClient client = new OkHttpClient();
                                                        client.newCall(request).enqueue(new Callback() {
                                                            @Override
                                                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                                                e.printStackTrace();
                                                            }

                                                            @Override
                                                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                                if (response.isSuccessful()){
                                                                    Intent intent = new Intent(postLogIn.this, ChatWindow.class);
                                                                    startActivity(intent);
                                                                }
                                                                else{
                                                                    Intent intent = new Intent(postLogIn.this, ChatWindow.class);
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(postLogIn.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        councilLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send email and password to see if it matches the council table then we open the chat window
                String councilURL = "https://lamp.ms.wits.ac.za/home/s2500339/app_cllr_login.php";
                String entUsername = enterUsername.getText().toString();
                String entPW = enterPW.getText().toString();
                //openChatWindow();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestBody postCouncilLogIn = new FormBody.Builder()
                                .add("email", entUsername)
                                .add("pw", entPW)
                                //  .add("topic",) how to add a problem if box is checked
                                .build();
                        Request request = new Request.Builder()
                                .url(councilURL)
                                .post(postCouncilLogIn)
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

                                if(!responseData.equals("-1")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            responseText1.setText(responseData);
                                            Intent intent = new Intent(postLogIn.this,ChatList.class);
                                            intent.putExtra("councilID",responseData);
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(postLogIn.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                        });
                    }
                }).start();
            }
        });
    }
}
