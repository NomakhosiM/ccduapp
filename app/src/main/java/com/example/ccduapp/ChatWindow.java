package com.example.ccduapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatWindow extends AppCompatActivity {
    private RecyclerView Recycler;
    private ImageView send;
    EditText chatMessage;
    boolean isCouncilLogIn = false;
    boolean isClientLogIn = false;
    String councilID,clientID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Intent intent = getIntent();
        if(intent.hasExtra("isCouncilLogIn")){
            isCouncilLogIn = true;
            councilID = intent.getStringExtra("councilID");
            clientID = intent.getStringExtra("clientID");
        } else if (intent.hasExtra("isClientLogIn")) {
            isClientLogIn = true;
            clientID = intent.getStringExtra("clientID");
        }

        ArrayList<ChatClass2> dataList = new ArrayList<>();

        String retrieveUrl = "https://lamp.ms.wits.ac.za/home/s2500339/app_msg_show.php";

                RequestBody postCouncilSignUp = new FormBody.Builder()
                        .add("clt_no","1")
                        .add("cllr_no", "1")
                        .build();
                Request request = new Request.Builder()
                        .url(retrieveUrl)
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
                            if (response.isSuccessful()){
                                String length;
                                JSONArray jsonArray;
                                try {
                                    jsonArray = new JSONArray(response.body().string());
                                    length = Integer.toString(jsonArray.length());
                                    for (int i = 0; i < jsonArray.length();i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String messageText = jsonObject.getString("TEXT");
                                            String sender = jsonObject.getString("SENDER");
//
//
                                            if(sender.equals("0") && isClientLogIn){
                                                ChatClass2 newMessage = new ChatClass2(ChatAdapter2.sentMessage,messageText);
                                                dataList.add(newMessage);
                                            }
                                            else if(sender.equals("1") && isClientLogIn){
                                                ChatClass2 newMessage = new ChatClass2(ChatAdapter2.receivedMessage,messageText);
                                                dataList.add(newMessage);
                                            } else if (sender.equals("1") && isCouncilLogIn) {
                                                ChatClass2 newMessage = new ChatClass2(ChatAdapter2.sentMessage,messageText);
                                                dataList.add(newMessage);
                                            } else if (sender.equals("0")&& isCouncilLogIn) {
                                                ChatClass2 newMessage = new ChatClass2(ChatAdapter2.receivedMessage,messageText);
                                                dataList.add(newMessage);
                                            }

                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ChatAdapter2 adapter = new ChatAdapter2(ChatWindow.this, dataList);
                                            Recycler = findViewById(R.id.chatRView);
                                            Recycler.setLayoutManager(new LinearLayoutManager(ChatWindow.this));
                                            Recycler.setAdapter(adapter);
                                            send = findViewById(R.id.sendButton);
                                            chatMessage = findViewById(R.id.inputMessage);
                                            send.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String sendUrl = "https://lamp.ms.wits.ac.za/home/s2500339/app_msg_send.php";
                                                    String message = chatMessage.getText().toString();

                                                    RequestBody sendData = new FormBody.Builder()
                                                            .add("clt_no","1")
                                                            .add("cllr_no", "1")
                                                            .add("sender", "0")
                                                             .add("msg",message)
                                                            .build();
                                                    OkHttpClient client = new OkHttpClient();

                                                    Request request = new Request.Builder()
                                                            .url(sendUrl)
                                                                    .post(sendData)
                                                                            .build();

                                                    client.newCall(request).enqueue(new Callback() {
                                                        @Override
                                                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                        @Override
                                                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                                if(response.isSuccessful()){
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {

                                                                            ChatClass2 newMessage = new ChatClass2(ChatAdapter2.sentMessage,message);
                                                                            dataList.add(newMessage);
                                                                            adapter.notifyItemInserted(dataList.size()-1);
                                                                        }
                                                                    });

                                                                }
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });


                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ChatWindow.this, "This has an error", Toast.LENGTH_SHORT).show();
                                        ChatAdapter2 adapter = new ChatAdapter2(ChatWindow.this, dataList);
                                    }
                                });
                            }
                    }
                });


    }
}