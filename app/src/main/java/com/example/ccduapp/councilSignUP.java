package com.example.ccduapp;

import static android.util.Patterns.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.util.Patterns;

public class councilSignUP extends AppCompatActivity {

    OkHttpClient client;
    String getURL ="";
    String postURL="https://lamp.ms.wits.ac.za/home/s2500339/app_clt_signup.php";
    private Button goToPledge;
    private TextView responseText;
    private EditText enterEmail;
    private EditText createPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_council_sign_up);

        client = new OkHttpClient();

        goToPledge = (Button) findViewById(R.id.goToPledge);
        responseText = (TextView) findViewById(R.id.responseText);
        createPassword = (EditText) findViewById(R.id.createPassword);
        enterEmail = (EditText)  findViewById(R.id.enterEmail);

        goToPledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = "https://lamp.ms.wits.ac.za/home/s2500339/app_cllr_signup.php";
                String setEmail = (String) enterEmail.getText().toString();
                String setPassword = (String) createPassword.getText().toString();
                //if sign up id successful, then openPledgePage()
                // if not, edit textView
//                openPledgePage();
                //open pledge window
                // put information into database table
                if(setEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(setEmail).matches()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(councilSignUP.this,"Invalid email",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RequestBody postCouncilSignUp = new FormBody.Builder()
                                    .add("email", setEmail)
                                    .add("pw", setPassword)

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
                                                Toast.makeText(councilSignUP.this, "User already Exists", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Intent intent = new Intent(councilSignUP.this, councilProblem.class);
                                                startActivity(intent);
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

    private void openPledgePage(){
        Intent intent = new Intent(this, pledgePage.class);
        startActivity(intent);
    }

}