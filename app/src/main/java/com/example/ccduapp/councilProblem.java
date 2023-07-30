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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class councilProblem extends AppCompatActivity {


    private Switch mentalH;
    private Switch domesticV;
    private Switch careerB;
    private TextView respondTexts;
    private EditText confirmUname;
    private Button councilToLogIn;
    String mental ="nan";
    String domestic = "nan";

    String career = "nan";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.council_problem);

        respondTexts = (TextView) findViewById(R.id.respondTexts);
        mentalH = (Switch) findViewById(R.id.mentalH);
        domesticV = (Switch) findViewById(R.id.domesticV);
        careerB = (Switch) findViewById(R.id.careerB);
        confirmUname = (EditText) findViewById(R.id.confirmUname);
        councilToLogIn = (Button) findViewById(R.id.councilToLogIn);



       mentalH.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        domesticV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        careerB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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




        councilToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String URL = "https://lamp.ms.wits.ac.za/home/s2500339/app_cllr_addtpcs.php";
                        String Uname = confirmUname.getText().toString();

                        RequestBody chooseTpc = new FormBody.Builder()

                                .add("tpc1", mental)
                                .add("tpc2", domestic)
                                .add("tpc3", career)
                                .add("email", Uname)
                                .build();

                        Request request = new Request.Builder()
                                .url(URL)
                                .post(chooseTpc)
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
                                    if (!responseData.equals("1")){

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(councilProblem.this, "Already assigned to one of the problems, try another", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                    else{
                                        openMainActivity();
                                    }
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(councilProblem.this, Integer.toString(response.code()), Toast.LENGTH_SHORT).show();
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
    private void openMainActivity() {
        Intent intent = new Intent(this, pledgePage.class);
        startActivity(intent);
    }
}

