package com.example.ccduapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class clientProblem extends AppCompatActivity {

    OkHttpClient client;
    String clientURL = "https://lamp.ms.wits.ac.za/home/s2500339/app_clt_addtpc.php";
    private Switch mentalH1;
    private Switch domesticV1;
    private Switch careerB1;
    private TextView respondText;

    private EditText reEnterUname;
    private Button clientToLogIn;



    String mental ="nan";
    String domestic = "nan";

    String career = "nan";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_problem);

        client = new OkHttpClient();
        respondText = (TextView) findViewById(R.id.respondText);
        mentalH1 = (Switch) findViewById(R.id.mentalH1);
        domesticV1 = (Switch) findViewById(R.id.domesticV1);
        careerB1 = (Switch) findViewById(R.id.careerB1);
        reEnterUname = (EditText) findViewById(R.id.reEnterUname);
        clientToLogIn = (Button) findViewById(R.id.clientToLogIn);
        mentalH1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mental = "1";
                }
                else {
                    mental = "nan";
                }
            }
        });
        domesticV1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    domestic = "2";
                }
                else {
                    domestic = "nan";
                }
            }
        });
        careerB1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    career = "3";
                }
                else {
                    career = "nan";
                }
            }
        });

        clientToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String URL = "https://lamp.ms.wits.ac.za/home/s2500339/app_clt_addtpcs.php";
                        String Uname = reEnterUname.getText().toString();

                        RequestBody postClientLogIn = new FormBody.Builder()
                                .add("uname", Uname)
                                .add("tpc1", mental)
                                .add("tpc2", domestic)
                                .add("tpc3", career)

                                .build();
                        Request request = new Request.Builder()
                                .url(URL)
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

                                if (response.isSuccessful()){
                                    if (responseData.equals("-1")){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(clientProblem.this, "Already assigned to one of the problems, try another", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                    else if (responseData.equals("1")){
                                     openMainActivity();
                                    }else{
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(clientProblem.this, responseData, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }
        private void openMainActivity() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

