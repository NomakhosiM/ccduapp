package com.example.ccduapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatList extends AppCompatActivity {
    String councilID;
    OkHttpClient okHttpClient;

    String clientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);
        RecyclerView client = findViewById(R.id.client);
        Intent intent = getIntent();
        councilID = intent.getStringExtra("councilID");


        ArrayList<ClientInfo> clientInfoArrayList = new ArrayList<>();

        String retrieveClientURL = "https://lamp.ms.wits.ac.za/home/s2500339/app_cllr_clts.php";
        RequestBody retrieveClientsBody = new FormBody.Builder()
                .add("cllr_no",councilID)
                        .build();

        Request request = new Request.Builder()
                .url(retrieveClientURL)
                .post(retrieveClientsBody)
                        .build();

        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONArray jsonArray;
                    try {
                        jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            clientID = jsonObject.getString("CLT_NO");
                            String username = jsonObject.getString("CLT_UNAME");
                            clientInfoArrayList.add(new ClientInfo(username,"",R.drawable.ic_launcher_foreground));

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                clientInfoArrayList.add(new ClientInfo("Emma","mental health",R.drawable.ic_launcher_foreground));
                                clientInfoArrayList.add(new ClientInfo("Milo","domestic violence",R.drawable.ic_launcher_foreground));
                                clientInfoArrayList.add(new ClientInfo("Smiley","academic pressure",R.drawable.ic_launcher_foreground));
                                clientInfoArrayList.add(new ClientInfo("Thabiso","mental health and domestic violence",R.drawable.ic_launcher_foreground));
                                ClientAdapter clientAdapter = new ClientAdapter(clientInfoArrayList);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatList.this, LinearLayoutManager.VERTICAL, false);
                                client.setLayoutManager(linearLayoutManager);
                                client.setAdapter(clientAdapter);
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });



    }

    public void onClick(@NonNull View view){
        goToChat();
    }
    public void goToChat(){
        Intent intent = new Intent(ChatList.this,ChatWindow.class);
        intent.putExtra("isCouncilLogIn",true);
        intent.putExtra("councilID",councilID);
        intent.putExtra("clientID", clientID);
        startActivity(intent);
    }
}
